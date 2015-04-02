package com.photography.utils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 根据类的成员变量自动组装XML
 * 成员变更必须有getXXX()和setXXX() (Boolean类型也是)
 * 集合类型必须是List
 * @author 赵志宏
 * @since 2012-12-6 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class XmlUtil {

    /** 日志对象 */
    protected Logger loger = Logger.getLogger(this.getClass());

    private Map colClass = null;

    /** 是否获取父类的成员变更 */
    private boolean isContainerFather = false;

    private StringBuffer sbXML = new StringBuffer(500);

    public XmlUtil() {
        sbXML.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
    }

    public XmlUtil(boolean isContainerFather) {
        sbXML.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
        this.isContainerFather = isContainerFather;
    }

    public XmlUtil(Map colClass) {
        this.colClass = colClass;
        sbXML.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
    }

    public Document createDocument(Object obj) throws Exception {
        createXML(obj, null);
        DocumentBuilderFactory objFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder objBilder = objFactory.newDocumentBuilder();
        Document objDocument = objBilder
                .parse(new ByteArrayInputStream(sbXML.toString().getBytes()));
        return objDocument;
    }

    public String getXMLString(Object obj) throws Exception {
        loger.info("webservice 接口：对象自动组装XML字符串开始。");
        createXML(obj, null);
        loger.info("webservice 接口：对象自动组装XML字符串成功结束。");
        return sbXML.toString();
    }

    public void reset() {
        sbXML = new StringBuffer(500);
    }

    /**
     * xml还原为对象
     * @param obj 存储数据对象
     * @param xmlDoc XML数据
     * @throws Exception
     * @return
     */
    public void parseXML(Object obj, String xmlDoc) throws Exception {
        loger.info("webservice 接口：XML自动组装对象开始。 对象：" + obj);
        //没有数据就直接返回
        if (StringUtil.isEmpty(xmlDoc)) {
            throw new Exception("XML数据为空!");
        }
        DocumentBuilderFactory objFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder objBilder = objFactory.newDocumentBuilder();
        Document objDocument = objBilder
                .parse(new ByteArrayInputStream(xmlDoc.getBytes()));
        NodeList objNodeList = objDocument.getChildNodes();
        for (int i = 0, j = objNodeList.getLength(); i < j; i++) {
            Node objNode = objNodeList.item(i);
            getNodeDetail(obj, objNode);
        }
        loger.info("webservice 接口：XML自动组装对象结束。");
    }

    /**
     * 根据类的成员变量自动组装XML
     * @param obj 需要生成xml的对象
     * @param propertyName 成员变量名,如果不是变量则为空
     * @param sbXML 保存xml字符串
     * @throws Exception
     */
    private void createXML(Object obj, String propertyName) throws Exception {
        if (null == obj) {
            return;
        }
        Class objClass = obj.getClass();
        String strClassName = objClass.getName();
        strClassName = strClassName.replace('.', '#');
        int iLength = strClassName.split("#").length;
        String strClassSimpleName = strClassName.split("#")[iLength - 1];
        sbXML.append("<").append(strClassSimpleName).append(">");
        if (null != propertyName) {
            sbXML.deleteCharAt(sbXML.length() - 1);
            sbXML.append(" property=\"").append(propertyName).append("\">");
        }
        List lstFields = new ArrayList();
        getClassProperties(lstFields, objClass);
        //遍历所有的成员变更
        for (int i = 0, j = lstFields.size(); i < j; i++) {
            Field objField = (Field)lstFields.get(i);
            if (objField.getModifiers() == 25) { //如果是静常量
                continue;
            }
            String strDateType = getDataType(objField.getType().getName());
            if (null != strDateType) { //如果是基本类型或strign
                objField.setAccessible(true);
                String strName = objField.getName();
                if ("serialVersionUID".equals(strName)) {
                    continue ;
                }
                sbXML.append("<").append(strName).append(" type=\"");
                sbXML.append(strDateType).append("\">");
                sbXML.append(invokeGetMethod(obj, strName));
                sbXML.append("</").append(strName).append(">");
            } else if (isList(objField.getType())) { //如果是list集合
                objField.setAccessible(true);
                parseList(objField.get(obj), objField.getName());
            } else { //自定义对象
                objField.setAccessible(true);
                createXML(objField.get(obj), objField.getName());
            }
        }
        sbXML.append("</").append(strClassSimpleName).append(">");
    }

    /**
     * 攻取类的所有属性
     * @param lstFields
     * @param objClass
     */
    @SuppressWarnings("unchecked")
	private void getClassProperties(List lstFields, Class objClass) {
    	//获取父类属性
    	Class objSuperClass = objClass.getSuperclass();
    	if (null != objSuperClass && isContainerFather) {
    		getClassProperties(lstFields, objSuperClass);
    	}
        //获取类自身的属性
        Field[] objFields = objClass.getDeclaredFields();
        for (int i = 0, j = objFields.length; i < j; i++) {
            lstFields.add(objFields[i]);
        }
    }

    /**
     * 获得该节点的各数据
     * @param obj 数据存储对象
     * @param objNode 节点
     * @throws Exception
     */
    private void getNodeDetail(Object obj, Node objNode) throws Exception {
        if (!objNode.hasChildNodes()) {
            return;
        }
        NodeList objNodeList = objNode.getChildNodes();
        for (int i = 0, j = objNodeList.getLength(); i < j; i++) {
            if (i == 108) {
                System.out.println(1);
            }
            Node objChildNode = objNodeList.item(i);
            Field objField = null;
            String strType = getAttribute(objChildNode, "type");
            String strProperty = getAttribute(objChildNode, "property");
            loger.info("webservice 接口：开始处理对象属性："+ strType);
            Class objClass = obj.getClass();
            try {
            	if (!isBaseDataType(strType)) { //如果是其它对象
                    strProperty = getAttribute(objChildNode, "property");
                } else { //如果是基对数据类型及String
                    strProperty = objChildNode.getNodeName();
                }
                objField = objClass.getDeclaredField(strProperty);
            } catch (Exception ex) {
                //对象没有该属性,跳过该值的设置
                continue ;
            }
            Object objNodeValue = null; //节点的值
            Class objParamType = null;
            if ("List".equals(objChildNode.getNodeName())) { //如果是集合list
                objNodeValue = getXMLList(objChildNode);
                objParamType = List.class;
            } else if (!isBaseDataType(strType)) { //如果是其它自定义对象
                String strNodeName = objChildNode.getNodeName();
                String strClassName = (String) colClass.get(strNodeName);
                objNodeValue = Class.forName(strClassName).newInstance();
                objParamType = objNodeValue.getClass();
                getNodeDetail(objNodeValue, objChildNode);
            } else { //如果是基对数据类型及String
                String strValue = "";
                if (objChildNode.getChildNodes().getLength() == 1) {
                    strValue = objChildNode.getChildNodes().item(0).getNodeValue();
                }
                if (!"null".equals(strValue)) {
                    try {
                        objParamType = objField.getType();
                        objNodeValue = getDataTypeValue(objParamType.toString().replaceAll("class ", ""), strValue);
                        //objParamType = getDataTypeClass(strType);
                    } catch (Exception ex) {
                        //对象没有该属性
                        continue ;
                    }

                } else {
                    continue;
                }
            }
            loger.info("webservice 接口：处理对象属性："+ strProperty + "，值为：" + objNodeValue);
            invokeSetMethod(obj, strProperty, objNodeValue, objParamType);
        }
    }

    /**
     * 遍历List节点下的所有元素
     * @param objNode 节点
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private List getXMLList(Node objNode) throws Exception {
        List litObject = new ArrayList();
        if (objNode.hasChildNodes()) {
            NodeList objChildNodes = objNode.getChildNodes();
            for (int i = 0, j = objChildNodes.getLength(); i < j; i++) {
                Node objChildNode = objChildNodes.item(i);
                String strNodeName = objChildNode.getNodeName();
                if (colClass.containsKey(strNodeName)) {
                    String strClassName = (String) colClass.get(strNodeName);
                    Object obj = Class.forName(strClassName).newInstance();
                    getNodeDetail(obj, objChildNode);
                    litObject.add(obj);
                }
            }
        }
        return litObject;
    }

    /**
     * 获取attribute
     * @param objNode 节点
     * @param name attribute名
     * @return
     */
    private String getAttribute(Node objNode, String name) {
        String strProperty = null;
        if (objNode.hasAttributes()) {
            NamedNodeMap objAttributes = objNode.getAttributes();
            if (null != objAttributes.getNamedItem(name)) {
                strProperty = objAttributes.getNamedItem(name).getNodeValue();
            }
        }
        return strProperty;
    }

    /**
     * 判断是否为List对象
     * @param objClass 比较对象
     * @return
     */
    private boolean isList(Class objClass) {
        boolean bResult = false;
        String strClassName = objClass.getName();
        if ("java.util.List".equals(strClassName) || "java.util.ArrayList".equals(strClassName)
                || "java.util.Collection".equals(strClassName)) {
            bResult = true;
        }
        return bResult;
    }

    /**
     * 解析List对象,组装成xml
     * @param obj List对象
     * @param propertyName 成员变量名
     * @param sbXML 保存xml字符串
     * @throws Exception
     */
    private void parseList(Object obj, String propertyName) throws Exception {
        List lstObject = (List) obj;
        if (null == lstObject) {
            lstObject = new ArrayList();
        }
        sbXML.append("<List property=\"").append(propertyName).append("\">");
        for (int i = 0, j = lstObject.size(); i < j; i++) {
            createXML(lstObject.get(i), null);
        }
        sbXML.append("</List>");
    }

    /**
     * 取得数据类型
     * @param type 类开
     * @return
     */
    private String getDataType(String type) {
        String objResult = null;
        if ("byte".equals(type)) {
            objResult = type;
        } else if ("java.lang.Byte".equals(type)) {
            objResult = type;
        } else if ("short".equals(type)) {
            objResult = type;
        } else if ("java.lang.Short".equals(type)) {
            objResult = type;
        } else if ("int".equals(type)) {
            objResult = type;
        } else if ("java.lang.Integer".equals(type)) {
            objResult = type;
        } else if ("long".equals(type)) {
            objResult = type;
        } else if ("java.lang.Long".equals(type)) {
            objResult = type;
        } else if ("float".equals(type)) {
            objResult = type;
        } else if ("java.lang.Float".equals(type)) {
            objResult = type;
        } else if ("double".equals(type)) {
            objResult = type;
        } else if ("java.lang.Double".equals(type)) {
            objResult = type;
        } else if ("java.math.BigDecimal".equals(type)) {
            objResult = type;
        } else if ("boolean".equals(type)) {
            objResult = type;
        } else if ("java.lang.Boolean".equals(type)) {
            objResult = type;
        } else if ("char".equals(type)) {
            objResult = type;
        } else if ("java.lang.Character".equals(type)) {
            objResult = type;
        } else if ("string".equals(type) || "java.lang.String".equals(type)) {
            objResult = "string";
        } else if ("java.util.Date".equals(type)) {
            objResult = type;
        } else if ("java.sql.Date".equals(type)) {
            objResult = type;
        } else if ("java.sql.Timestamp".equals(type)) {
            objResult = type;
        }
        return objResult;
    }

    /**
     * 获得相应基本类型的数据值
     * @param type 类型
     * @param value 值
     * @return
     */
    private Object getDataTypeValue(String type, String value) throws Exception {
        Object objValue = null;
        try {
            if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
                objValue = new Byte(Byte.parseByte(value));
            } else if ("short".equals(type) || "java.lang.Short".equals(type)) {
                objValue = new Short(Short.parseShort(value));
            } else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
                objValue = new Integer(Integer.parseInt(value));
            } else if ("long".equals(type) || "java.lang.Long".equals(type)) {
                objValue = new Long(Long.parseLong(value));
            } else if ("float".equals(type) || "java.lang.Float".equals(type)) {
                objValue = new Float(Float.parseFloat(value));
            } else if ("double".equals(type) || "java.lang.Double".equals(type)) {
                objValue = new Double(Double.parseDouble(value));
            } else if ("java.math.BigDecimal".equals(type)) {
                objValue = new BigDecimal(value);
            } else if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
                objValue = Boolean.valueOf(Boolean.getBoolean(value));
            } else if ("char".equals(type) || "java.lang.Character".equals(type)) {
                objValue = new Character(value.charAt(0));
            } else if ("string".equals(type) || "java.lang.String".equals(type)) {
                objValue = new String(value);
            } else if ("java.util.Date".equals(type)) {
                SimpleDateFormat objFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                objValue = objFormat.parse(this.toDateString(value));
                //objValue = java.util.Date.parse(value);
            } else if ("java.sql.Date".equals(type)) {
                SimpleDateFormat objFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                java.util.Date objUtilDate = objFormat.parse(this.toDateString(value));
                objValue = new java.sql.Date(objUtilDate.getTime());
            } else if ("java.sql.Timestamp".equals(type)) {
                SimpleDateFormat objFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                java.util.Date objUtilDate = objFormat.parse(this.toDateString(value));
                objValue = new java.sql.Timestamp(objUtilDate.getTime());
            }
        } catch (Exception ex) {
            loger.error("webservice 接口：xml 转换 数据异常：getDataTypeValue()", ex);
            throw new Exception("xml 转换 数据异常：getDataTypeValue() ", ex);
        }

        return objValue;
    }

    /**
     * 将字符串转换为必要的格式
     * @param value
     * @return
     */
    private String toDateString(String value) {
        value = value.replaceAll("/", "-");
        if (value.length() == 10) {
            value += " 00:00:00";
        }
        return value;
    }

    private boolean isBaseDataType(String type) {
        boolean bIsBaseType = false;
        if (null == type) {
            return bIsBaseType;
        }
        if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
            bIsBaseType = true;
        } else if ("short".equals(type) || "java.lang.Short".equals(type)) {
            bIsBaseType = true;
        } else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
            bIsBaseType = true;
        } else if ("long".equals(type) || "java.lang.Long".equals(type)) {
            bIsBaseType = true;
        } else if ("float".equals(type) || "java.lang.Float".equals(type)) {
            bIsBaseType = true;
        } else if ("double".equals(type) || "java.lang.Double".equals(type) || "java.math.BigDecimal".equals(type)) {
            bIsBaseType = true;
        } else if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
            bIsBaseType = true;
        } else if ("char".equals(type) || "java.lang.Character".equals(type)) {
            bIsBaseType = true;
        } else if ("string".equals(type) || "java.lang.String".equals(type)) {
            bIsBaseType = true;
        } else if ("java.util.Date".equals(type)) {
            bIsBaseType = true;
        } else if ("java.sql.Date".equals(type)) {
            bIsBaseType = true;
        } else if ("java.sql.Timestamp".equals(type)) {
            bIsBaseType = true;
        }
        return bIsBaseType;
    }

    /**
     * 调用getXXX()方法
     * @param obj 被调用的对象
     * @param property 被调用的成员变量名
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Object invokeGetMethod(Object obj, String property) throws Exception {
    	 Class objClass = obj.getClass();
         String strMethodName = "get" + property.substring(0, 1).toUpperCase()
                 + property.substring(1, property.length());
         Method objMethod = objClass.getMethod(strMethodName, null);
         Object objValue = objMethod.invoke(obj, null);
         if (objValue instanceof String) {
             String strValue = (String) objValue;
             if (strValue.contains("&") && !strValue.contains("&amp;")) {
                 strValue = strValue.replaceAll("&", "&amp;");
             }
             if (strValue.contains("<")) {
                 strValue = strValue.replaceAll("<", "&lt;");
             }
             if (strValue.contains(">")) {
                 strValue = strValue.replaceAll(">", "&gt;");
             }
             if (strValue.contains("'")) {
                 strValue = strValue.replaceAll("'", "&apos;");
             }
             if (strValue.contains("\"")) {
                 strValue = strValue.replaceAll("\"", "&quot;");
             }
             objValue = (Object) strValue;
         }
         return objValue;
    }

    /**
     * 调用setXXX()方法
     * @param obj 被调用的对象
     * @param property 被调用的成员变量名
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Object invokeSetMethod(Object obj, String property, Object value, Class paramType)
            throws Exception {
    	  Object objValue = value;
          Class objClass = obj.getClass();
          String strMethodName = "set" + property.substring(0, 1).toUpperCase()
                  + property.substring(1, property.length());
          Object[] objValues = { objValue };
          Method objMethod = null;
          try {
              Class[] objParamTypes = { paramType };
              objMethod = objClass.getMethod(strMethodName, objParamTypes);
          } catch (NoSuchMethodException ex) {
              Class[] objParamTypes = { Object.class };
              objMethod = objClass.getMethod(strMethodName, objParamTypes);
          }

          return objMethod.invoke(obj, objValues);
    }
}
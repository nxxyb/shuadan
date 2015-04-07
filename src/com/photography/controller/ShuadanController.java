package com.photography.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photography.dao.exp.Condition;
import com.photography.dao.query.Sort;
import com.photography.exception.ServiceException;
import com.photography.mapping.ShuadanUser;
import com.photography.service.IShuadanLogService;
import com.photography.service.IShuadanUserService;
import com.photography.utils.Constants;
import com.photography.utils.DateUtil;

/**
 * 请求用户
 */
@Controller
@RequestMapping("/user")
public class ShuadanController extends BaseController{
	
	private final static Logger log = Logger.getLogger(ShuadanController.class);
	
	@Autowired
	private IShuadanUserService shuadanUserService;
	
	@Autowired
	private IShuadanLogService shuadanLogService;

	public void setShuadanUserService(IShuadanUserService shuadanUserService) {
		this.shuadanUserService = shuadanUserService;
	}

	public void setShuadanLogService(IShuadanLogService shuadanLogService) {
		this.shuadanLogService = shuadanLogService;
	}

	/**
	 * 取得一个乘客
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getChengke",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String getChengke() throws UnsupportedEncodingException{
		String result = null;
		List<ShuadanUser> users = (List<ShuadanUser>) shuadanUserService.loadPojoByExpression(Condition.eq("fd_status", Constants.FD_TYPE_WEIFA), new Sort("id","asc"));
		if(users == null || users.isEmpty()){
			result = Constants.NO;
		}else{
			ShuadanUser user = users.get(0);
			result = user.toString();
			log.error("请求获得乘客用户信息：" + result);
		}
        return result;  
    }
	
	/**
	 * 取得一个车主
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getChezhu",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String getChezhu() throws UnsupportedEncodingException, ServiceException{
		String result = null;
		List<ShuadanUser> users = (List<ShuadanUser>) shuadanUserService.loadPojoByExpression(Condition.eq("fd_status", Constants.FD_TYPE_YIFA), new Sort("id","asc"));
		if(users == null || users.isEmpty()){
			log.error("目前还没有符合条件的车主");
			result = Constants.NO;
		}else{
			ShuadanUser user = users.get(0);
			result = user.toString();
			log.error("请求获得车主用户信息：" + result);
			
			//将该条信息状态置为  车主正在抢单 
			user.setFd_status(Constants.FD_TYPE_QD);
			shuadanUserService.savePojo(user, null);
			log.error("("+ user.getChezhu_name() + "," + user.getChezhu_mobile() +  ")" + " 车主开始抢单：" + DateUtil.date2String(new Date()));
		}
        return result;  
    }
	
	/**
	 * 根据id取得车主和乘客
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/getInfo",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String getInfo(String id) throws UnsupportedEncodingException{
		String result = null;
		ShuadanUser user = (ShuadanUser) shuadanUserService.loadPojo(id);
		if(user == null){
			log.error("未找到用户ID：" + id);
			result = Constants.NO;
			return result;
		}else{
			result = user.toString();
			log.error("根据ID(" + id + ") 获得车主用户信息：" + result);
			return result;
        }
		
    }
	
	/**
	 * 乘客发单
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKeFD",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public String chengKeFD(String id) throws UnsupportedEncodingException, ServiceException{
		return changeStatus(id,Constants.FD_TYPE_YIFA); 
    }
	
	/**
	 * 车主抢单完成
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/cheZhuQD",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String cheZhuQD(String id) throws UnsupportedEncodingException, ServiceException{
		return changeStatus(id,Constants.FD_TYPE_QDWC); 
    }
	
	/**
	 * 乘客确认搭乘
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEQR",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String chengKEQR(String id) throws UnsupportedEncodingException, ServiceException{
		return changeStatus(id,Constants.FD_TYPE_QRDC); 
    }
	
	/**
	 * 乘客评价完成
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEPJ",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String chengKEPJ(String id) throws UnsupportedEncodingException, ServiceException{
		return changeStatus(id,Constants.FD_TYPE_PJWC); 
    }
	
	/**
	 * 取得已抢单成功且未付款的乘客
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getQdcgWfk",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getQdcgWfk() throws UnsupportedEncodingException, ServiceException{
		String result = null;
		List<ShuadanUser> users = (List<ShuadanUser>) shuadanUserService.loadPojoByExpression(
				Condition.and(Condition.eq("fd_status", Constants.FD_TYPE_QDWC), 
				Condition.or(Condition.eq("fk_status", null), Condition.eq("fk_status", Constants.NO))
			) , new Sort("id","asc"));
		if(users == null || users.isEmpty()){
			result = Constants.NO;
		}else{
			ShuadanUser user = users.get(0);
			result = user.toString();
			log.error("请求获得抢单完成且未付款乘客用户信息：" + result);
		}
        return result;  
    }
	
	/**
	 * 乘客付款
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEFK",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String chengKEFK(String id) throws UnsupportedEncodingException, ServiceException{
		String result = Constants.YES;
		ShuadanUser user = (ShuadanUser) shuadanUserService.loadPojo(id);
		if(user == null){
			log.error("未找到用户ID：" + id);
			result = Constants.NO;
		}else{
			user.setFk_status(Constants.YES);
			String time = DateUtil.date2String(new Date());
			log.error("("+ user.getChengke_name() + "," + user.getChengke_mobile() +  ")" + " 乘客付款完成：" + time);
			shuadanUserService.savePojo(user, null);
		}
		return result;
    }
	
	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @author 徐雁斌
	 * @throws ServiceException 
	 */
	private synchronized String changeStatus(String id,String status) throws ServiceException{
		String result = Constants.YES;
		ShuadanUser user = (ShuadanUser) shuadanUserService.loadPojo(id);
		if(user == null){
			log.error("未找到用户ID：" + id);
			result = Constants.NO;
			return result;
			
		}else if(Constants.FD_TYPE_YIFA.equals(status)){  //乘客发单
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date());
			user.setChengke_fd_time(time);
			log.error("("+ user.getChengke_name() + "," + user.getChengke_mobile() +  ")" + " 乘客发单：" + time);
			
		}else if(Constants.FD_TYPE_QDWC.equals(status)){  //车主抢单完成
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date());
			user.setChezhu_qd_time(time);
			log.error("("+ user.getChezhu_name() + "," + user.getChezhu_mobile() +  ")" + " 抢单完成：" + time);
			
		}else if(Constants.FD_TYPE_QRDC.equals(status)){  //乘客确认搭乘
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date());
			log.error("("+ user.getChengke_name() + "," + user.getChengke_mobile() +  ")" + " 确认搭乘：" + time);
			
		}else if(Constants.FD_TYPE_PJWC.equals(status)){  //乘客评价完成
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date());
			log.error("("+ user.getChengke_name() + "," + user.getChengke_mobile() +  ")" + " 评价完成：" + time);
		}
		
		shuadanUserService.savePojo(user, null);
		
		return result;  
	}
	
	
}

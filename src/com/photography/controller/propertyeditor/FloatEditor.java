package com.photography.controller.propertyeditor;

import java.beans.PropertyEditorSupport;


/**
 * 
 * @author 徐雁斌
 * @since 2015-2-11
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
public class FloatEditor extends PropertyEditorSupport {

	@Override    
    public void setAsText(String text) throws IllegalArgumentException {    
        if (text == null || text.equals("")) {    
            text = "0";    
        }    
        setValue(Float.parseFloat(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
}

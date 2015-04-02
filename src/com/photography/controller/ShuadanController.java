package com.photography.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photography.dao.exp.Condition;
import com.photography.mapping.ShuadanUser;
import com.photography.service.IShuadanLogService;
import com.photography.service.IShuadanUserService;
import com.photography.utils.Constants;

/**
 * 请求用户
 * @author 徐雁斌
 * @since 2015-2-3
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
	 * @author 徐雁斌
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getChengke")  
    @ResponseBody
    public String getChengke(){
		String result = null;
		List<ShuadanUser> users = (List<ShuadanUser>) shuadanUserService.loadPojoByExpression(Condition.eq("chengke_fd", Constants.FD_TYPE_WEIFA), null);
		if(users == null || users.isEmpty()){
			result = Constants.NO;
		}else{
			ShuadanUser user = users.get(0);
			result = user.toString();
			log.error("请求获得用户信息：" + result);
		}
        return result;  
    }
	
	
}

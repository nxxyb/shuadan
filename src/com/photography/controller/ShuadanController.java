package com.photography.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photography.dao.exp.Condition;
import com.photography.dao.exp.Expression;
import com.photography.dao.query.Sort;
import com.photography.exception.ServiceException;
import com.photography.mapping.ShuadanChengke;
import com.photography.mapping.ShuadanChezhu;
import com.photography.mapping.ShuadanRenwu;
import com.photography.service.IShuadanService;
import com.photography.utils.Constants;
import com.photography.utils.DateUtil;
import com.photography.utils.MathUtil;

/**
 * 请求用户
 */
@Controller
@RequestMapping("/user")
public class ShuadanController extends BaseController{
	
	private final static Logger log = Logger.getLogger(ShuadanController.class);
	
	@Autowired
	private IShuadanService shuadanService;
	
	public void setShuadanService(IShuadanService shuadanService) {
		this.shuadanService = shuadanService;
	}
	
//	/**
//	 * 根据deviceId查询乘客任务
//	 * http://127.0.0.1:8088/shuadan/user/findChengkeRenwu?mobile=18622535271
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/findChengkeRenwu",produces="application/json;charset=UTF-8")  
//    @ResponseBody
//	public synchronized String findChengkeRenwu(String mobile){
//		String result = Constants.NO;
//		List<ShuadanRenwu> renwus = (List<ShuadanRenwu>) shuadanService.loadPojoByExpression(ShuadanRenwu.class, 
//				Condition.and(Condition.eq("chengke_mobile", mobile), Condition.eq("chengke_status", Constants.TYPE_SQ)), null);
//		if(renwus != null && !renwus.isEmpty()){
//			result = renwus.get(0).toString();
//			log.error("乘客请求任务信息：" + result);
//		}else{
//			log.error("无乘客任务信息，请申请乘客");
//		}
//		return result;
//	}

	/**
	 * 申请一个乘客
	 * http://127.0.0.1:8088/shuadan/user/getChengke
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getChengke",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String getChengke() throws ServiceException{
		String result = Constants.NO;
		List<ShuadanChengke> chengkes = (List<ShuadanChengke>) shuadanService.queryByHql("from shuadan_chengke c where c.chengke_mobile not in (select r.chengke_mobile from shuadan_renwu r where r.chengke_mobile is not null) order by c.group_type asc", null);
		if(chengkes != null && !chengkes.isEmpty()){
			//随机取一个
			ShuadanChengke chengke = chengkes.get(MathUtil.getRanmon(0, chengkes.size()-1));
			
			//新增任务
			ShuadanRenwu renwu = new ShuadanRenwu();
			renwu.setChengke_mobile(chengke.getChengke_mobile());
			renwu.setChengke_password(chengke.getChengke_password());
			renwu.setChengke_deviceid(chengke.getChengke_deviceid());
			renwu.setChengke_status(Constants.TYPE_SQ);
			renwu.setGroup_type(chengke.getGroup_type());
			renwu.setFd_status(Constants.FD_TYPE_WEIFA);
			renwu.setFk_status(Constants.NO);
			renwu.setChengke_zfb_num(chengke.getChengke_zfb_num());
			renwu.setChengke_zfb_password(chengke.getChengke_zfb_password());
			renwu.setChengke_sb_bstp(chengke.getChengke_sb_bstp());
			renwu.setChengke_xb_bstp(chengke.getChengke_xb_bstp());
			
			shuadanService.savePojo(renwu);
			
			result = renwu.toString();
			log.error("申请乘客任务信息：" + result);
		}
        return result;  
    }
	
//	/**
//	 * 根据deviceId查询车主任务
//	 * http://127.0.0.1:8088/shuadan/user/findChezhuRenwu?deviceId=908050227136524
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/findChezhuRenwu",produces="application/json;charset=UTF-8")  
//    @ResponseBody
//	public synchronized String findChezhuRenwu(String deviceId){
//		String result = Constants.NO;
//		List<ShuadanRenwu> renwus = (List<ShuadanRenwu>) shuadanService.loadPojoByExpression(ShuadanRenwu.class, 
//				Condition.and(Condition.eq("chezhu_deviceid", deviceId), Condition.eq("chezhu_status", Constants.TYPE_SQ)), null);
//		if(renwus != null && !renwus.isEmpty()){
//			result = renwus.get(0).toString();
//			log.error("车主请求任务信息：" + result);
//		}else{
//			log.error("无车主任务信息，请申请车主");
//		}
//		return result;
//	}
	
	/**
	 * 申请一个车主
	 * http://127.0.0.1:8088/shuadan/user/getChezhu
	 * @return
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getChezhu",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String getChezhu() throws ServiceException{
		String result = Constants.NO;
		Expression exp1 = Condition.or(Condition.eq("chengke_status", Constants.TYPE_SQ), Condition.eq("chengke_status", Constants.TYPE_JX));
		Expression exp2 = Condition.eq("chezhu_status", null);
		List<ShuadanRenwu> renwus = (List<ShuadanRenwu>) shuadanService.loadPojoByExpression(ShuadanRenwu.class, 
				Condition.and(exp1, exp2), null);
		if(renwus == null || renwus.isEmpty()){
			log.error("当前没有任务，无法分配车主，请稍候再试！");
			return result;
		}else{
			ShuadanRenwu  renwu = renwus.get(0);
			List<String> params = new ArrayList<String>();
			params.add(renwu.getGroup_type());
			List<ShuadanChezhu> chezhus = (List<ShuadanChezhu>) shuadanService.queryByHql("from shuadan_chezhu c where c.group_type=? and c.chezhu_mobile not in (select r.chezhu_mobile from shuadan_renwu r where r.chezhu_mobile is not null) order by c.group_type asc", params);
			if(chezhus != null && !chezhus.isEmpty()){
				//随机取一个
				ShuadanChezhu chezhu = chezhus.get(MathUtil.getRanmon(0, chezhus.size()-1));
				
				//更新任务
				renwu.setChezhu_mobile(chezhu.getChezhu_mobile());
				renwu.setChezhu_password(chezhu.getChezhu_password());
				renwu.setChezhu_deviceid(chezhu.getChezhu_deviceid());
				renwu.setChezhu_status(Constants.TYPE_SQ);
				shuadanService.savePojo(renwu);
				
				result = renwu.toString();
				log.error("申请车主任务信息：" + result);
			}
	        return result;  
		}
    }
	
	/**
	 * 乘客任务就绪
	 * http://127.0.0.1:8088/shuadan/user/chengkeJX?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengkeJX",produces="application/json;charset=UTF-8")  
    @ResponseBody
	public synchronized String chengkeJX(String renwuId) throws ServiceException{
		String result = Constants.NO;
		ShuadanRenwu renwu = (ShuadanRenwu) shuadanService.loadPojo(ShuadanRenwu.class, renwuId);
		if(renwu != null){
			renwu.setChengke_status(Constants.TYPE_JX);
			shuadanService.savePojo(renwu);
			result = renwu.toString();
			log.error("乘客任务就绪,ID：" + renwuId);
		}
		return result;
	}
	
	/**
	 * 车主任务就绪
	 * http://127.0.0.1:8088/shuadan/user/chezhuJX?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chezhuJX",produces="application/json;charset=UTF-8")  
    @ResponseBody
	public synchronized String chezhuJX(String renwuId) throws ServiceException{
		String result = Constants.NO;
		ShuadanRenwu renwu = (ShuadanRenwu) shuadanService.loadPojo(ShuadanRenwu.class, renwuId);
		if(renwu != null){
			renwu.setChezhu_status(Constants.TYPE_JX);
			shuadanService.savePojo(renwu);
			result = renwu.toString();
			log.error("车主任务就绪,ID：" + renwuId);
		}
		return result;
	}
	
	
	
	/**
	 * 根据id取得任务信息
	 * http://127.0.0.1:8088/shuadan/user/getInfo?renwuId=2
	 * @return
	 */
	@RequestMapping(value="/getInfo",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String getInfo(String renwuId) throws UnsupportedEncodingException{
		String result = Constants.NO;
		ShuadanRenwu renwu = (ShuadanRenwu) shuadanService.loadPojo(ShuadanRenwu.class,renwuId);
		if(renwu == null){
			log.error("未找到任务ID：" + renwuId);
		}else{
			result = renwu.toString();
			log.error("根据ID(" + renwuId + ") 获得任务信息：" + result);
        }
		return result;
		
    }
	
	/**
	 * 乘客发单
	 * http://127.0.0.1:8088/shuadan/user/chengKeFD?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKeFD",produces="application/json;charset=UTF-8")  
    @ResponseBody
    public synchronized String chengKeFD(String renwuId) throws ServiceException{
		return changeStatus(renwuId,Constants.FD_TYPE_YIFA); 
    }
	
	/**
	 * 车主抢单完成
	 * http://127.0.0.1:8088/shuadan/user/cheZhuQD?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/cheZhuQD",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String cheZhuQD(String renwuId) throws ServiceException{
		return changeStatus(renwuId,Constants.FD_TYPE_QDWC); 
    }
	
	/**
	 * 乘客确认搭乘
	 * http://127.0.0.1:8088/shuadan/user/chengKEQR?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEQR",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String chengKEQR(String renwuId) throws ServiceException{
		return changeStatus(renwuId,Constants.FD_TYPE_QRDC); 
    }
	
	/**
	 * 乘客评价完成
	 * http://127.0.0.1:8088/shuadan/user/chengKEPJ?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEPJ",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String chengKEPJ(String renwuId) throws ServiceException{
		return changeStatus(renwuId,Constants.FD_TYPE_PJWC); 
    }
	
	/**
	 * 取得已抢单成功且未付款的任务
	 * http://127.0.0.1:8088/shuadan/user/getQdcgWfk
	 * @return
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getQdcgWfk",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String getQdcgWfk() throws ServiceException{
		String result = Constants.NO;
		List<Expression> exps1 = new ArrayList<Expression>();
		exps1.add(Condition.eq("fd_status", Constants.FD_TYPE_QDWC));
		exps1.add(Condition.eq("fd_status", Constants.FD_TYPE_QRDC));
		exps1.add(Condition.eq("fd_status", Constants.FD_TYPE_PJWC));
		
		List<Expression> exps2 = new ArrayList<Expression>();
		exps2.add(Condition.eq("fk_status", null));
		exps2.add(Condition.eq("fk_status", Constants.NO));
		
		List<ShuadanRenwu> users = (List<ShuadanRenwu>) shuadanService.loadPojoByExpression(ShuadanRenwu.class,
				Condition.and(Condition.or(exps1),Condition.or(exps2)), new Sort("id","asc"));
		if(users == null || users.isEmpty()){
			result = Constants.NO;
			log.error("无抢单完成且未付款任务信息");
		}else{
			ShuadanRenwu user = users.get(0);
			result = user.toString();
			log.error("请求获得抢单完成且未付款任务信息：" + result);
		}
        return result;  
    }
	
	/**
	 * 乘客付款
	 * http://127.0.0.1:8088/shuadan/user/chengKEFK?renwuId=2
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/chengKEFK",produces="application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String chengKEFK(String renwuId) throws ServiceException{
		String result = Constants.YES;
		ShuadanRenwu user = (ShuadanRenwu) shuadanService.loadPojo(ShuadanRenwu.class,renwuId);
		if(user == null){
			log.error("未找到任务ID：" + renwuId);
			result = Constants.NO;
		}else{
			user.setFk_status(Constants.YES);
			String time = DateUtil.date2String(new Date(),null);
			log.error("("+  user.getChengke_mobile() +  ")" + " 乘客付款完成：" + time);
			shuadanService.savePojo(user);
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
		ShuadanRenwu user = (ShuadanRenwu) shuadanService.loadPojo(ShuadanRenwu.class,id);
		if(user == null){
			log.error("未找到任务ID：" + id);
			result = Constants.NO;
			return result;
			
		}else if(Constants.FD_TYPE_YIFA.equals(status)){  //乘客发单
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date(),null);
			user.setChengke_fd_time(time);
			log.error("(" + user.getChengke_mobile() +  ")" + " 乘客发单：" + time);
			
		}else if(Constants.FD_TYPE_QDWC.equals(status)){  //车主抢单完成
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date(),null);
			user.setChezhu_qd_time(time);
			log.error("("+ user.getChezhu_mobile() +  ")" + " 抢单完成：" + time);
			
		}else if(Constants.FD_TYPE_QRDC.equals(status)){  //乘客确认搭乘
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date(),null);
			log.error("("+ user.getChengke_mobile() +  ")" + " 确认搭乘：" + time);
			
		}else if(Constants.FD_TYPE_PJWC.equals(status)){  //乘客评价完成
			user.setFd_status(status);
			String time = DateUtil.date2String(new Date(),null);
			log.error("("+ user.getChengke_mobile() +  ")" + " 评价完成：" + time);
		}
		
		shuadanService.savePojo(user);
		
		return result;  
	}
	
	
}

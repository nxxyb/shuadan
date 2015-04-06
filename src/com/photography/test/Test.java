package com.photography.test;

import com.photography.utils.Constants;

/**
 * 
 * @author 徐雁斌
 * @since 2015-4-3
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
public class Test {
	
	public void runChengke(){
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				while(true){
					//取得乘客
					String chengkeInfo = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/getChengke", null);
					System.out.println("乘客信息：" + chengkeInfo);
					if(!"0".equals(chengkeInfo)){
						String[] chengkeArr = chengkeInfo.split(",");
						String id = chengkeArr[0];
						System.out.println("乘客id：" + id);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//乘客发单
						String result = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/chengKeFD", "id="+id);
						System.out.println("发单结果：" + result);
						
						while(true){
							chengkeInfo = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/getInfo", "id="+id);
							chengkeArr = chengkeInfo.split(",");
							String status = chengkeArr[13];
							String fkStatus = chengkeArr[14];
							System.out.println("拼单状态：" + status + " 付款状态：" + fkStatus);
							if("3".equals(status) && "1".equals(fkStatus)){
								break;
							}else{
								System.out.println("没有乘客付款完成，延迟等待");
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						
						//确认搭乘
						result = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/chengKEQR", "id="+id);
						System.out.println("搭乘结果：" + result);
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//乘客评价完成
						result = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/chengKEPJ", "id="+id);
						System.out.println("乘客评价：" + result);
						
						
					}else{
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		t1.start();
	}
	
	public void runChezhu(){
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				while(true){
					//取得车主
					String chengkeInfo = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/getChezhu", null);
					System.out.println("车主信息：" + chengkeInfo);
					if(!"0".equals(chengkeInfo)){
						String[] chengkeArr = chengkeInfo.split(",");
						String id = chengkeArr[0];
						System.out.println("车主id：" + id);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//车主抢单
						String result = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/cheZhuQD", "id="+id);
						System.out.println("车主抢单结果：" + result);
						
						//乘客付款
						result = HttpRequest.sendGet("http://127.0.0.1:8080/shuadan/user/chengKEFK", "id="+id);
						System.out.println("乘客付款结果：" + result);
						
						break;
					}else{
						System.out.println("没有乘客发单延时等待");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				
			}
			}
		});
		
		
		t1.start();
	}
	
	public static void main(String[] args) {
		Test1 t = new Test1();
		t.runChengke();
		t.runChezhu();
	}

}

package com.photography.test;



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
					String renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/getChengke", null);
					System.out.println("乘客线程-乘客任务信息：" + renwuInfo);
					if(!"0".equals(renwuInfo)){
						String[] renwuArr = renwuInfo.split(",");
						String id = renwuArr[0];
						System.out.println("乘客线程-乘客任务id：" + id);
						
						sleep(5000);
						
						//乘客就绪
						renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chengkeJX", "renwuId=" + id);
						System.out.println("乘客线程-乘客任务就绪状态：" + renwuInfo);
						
						//循环等待车主
						while(true){
							renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/getInfo", "renwuId=" + id);
							System.out.println("乘客线程-任务状态：" + renwuInfo);
							renwuArr = renwuInfo.split(",");
							if(!"0".equals(renwuInfo) && "1".equals(renwuArr[4])){
								System.out.println("乘客线程-车主已就绪，准备发单");
								break;
							}else{
								System.out.println("乘客线程-车主还未就绪，循环等待");
								sleep(5000);
							}
						}
						
						//乘客发单
						String result = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chengKeFD", "renwuId="+id);
						System.out.println("乘客线程-发单结果：" + result);
						
						sleep(5000);
						
						//等待车主抢单
						while(true){
							renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/getInfo", "renwuId="+id);
							renwuArr = renwuInfo.split(",");
							String qdStatus = renwuArr[11];
							System.out.println("乘客线程-车主抢单状态：" + qdStatus);
							if("2".equals(qdStatus)){
								break;
							}else{
								System.out.println("乘客线程-车主还未抢单，延迟等待");
								sleep(5000);
							}
						}
						
						//乘客付款
						result = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chengKEFK", "renwuId="+id);
						System.out.println("乘客线程-付款结果：" + result);
						sleep(5000);
						
						
						//确认搭乘
						result = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chengKEQR", "renwuId="+id);
						System.out.println("乘客线程-搭乘结果：" + result);
						
						sleep(5000);
						
						//乘客评价完成
						result = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chengKEPJ", "renwuId="+id);
						System.out.println("乘客线程-乘客评价：" + result);
						sleep(5000);
						
					}else{
						System.out.println("无乘客，循环等待");
						sleep(5000);
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
					String renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/getChezhu", null);
					System.out.println("车主线程-车主任务信息：" + renwuInfo);
					if(!"0".equals(renwuInfo)){
						String[] renwuArr = renwuInfo.split(",");
						String id = renwuArr[0];
						System.out.println("车主线程-车主任务id：" + id);
						sleep(5000);
						
						//车主就绪
						renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/chezhuJX", "renwuId=" + id);
						System.out.println("车主线程-车主任务就绪状态：" + renwuInfo);
						
						//循环等待乘客发单
						while(true){
							renwuInfo = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/getInfo", "renwuId=" + id);
							System.out.println("车主线程-任务状态：" + renwuInfo);
							renwuArr = renwuInfo.split(",");
							if(!"0".equals(renwuInfo) && "1".equals(renwuArr[11])){
								System.out.println("车主线程-乘客已发单，准备抢单");
								break;
							}else{
								System.out.println("车主线程-乘客还未发单，循环等待");
								sleep(5000);
							}
						}
						sleep(5000);
						
						//车主抢单
						String result = HttpRequest.sendGet("http://127.0.0.1:8088/shuadan/user/cheZhuQD", "renwuId="+id);
						System.out.println("车主线程-车主抢单结果：" + result);
						sleep(5000);
						
					}else{
						System.out.println("车主线程-没有任务延时等待");
						sleep(5000);
					}
				
			}
			}
		});
		
		
		t1.start();
	}
	
	public static void sleep(long m){
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		t.runChengke();
//		t.runChezhu();
	}

}

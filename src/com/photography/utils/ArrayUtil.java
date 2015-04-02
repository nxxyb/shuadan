package com.photography.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * 数组工具类
 * 
 * @author 赵志宏
 * @since 2013-5-21
 * 
 * @copyright 2013 天大求实电力新技术股份有限公司 版权所有
 *
 */
public class ArrayUtil {
	
	/**
	 * 将List<Map>结构转换成二维数组
	 * @param mapList 数据
	 * @param keyOrder 二维数组列顺序
	 * @return
	 */
	public static Object[][] toArray(List<Map<String, Object>> mapList, String[] keyOrder){
		Object[][] result = new Object[mapList.size()][keyOrder.length];
		
		for (int i=0; i<mapList.size(); i++) {
			Map<String, ?> rs = mapList.get(i);
			
			for (int j=0; j<keyOrder.length; j++) {
				result[i][j] = rs.get(keyOrder[j]);
			}
		}
		
		return result;
	}
	
	/**
	 * 按照spliteBy列的取值不同，将数组切割为多个子数组
	 * @param ob
	 * @param splitBy
	 * @return
	 */
	public static List<Object[][]> splitArray(Object[][] ob, int splitBy) {
		List<Object[][]> result = new ArrayList<Object[][]>();
		
		sort(ob, splitBy);
		
		List<Integer> splitLines = new ArrayList<Integer>();
		
		for (int i=1; i<ob.length; i++) {
			if (!ob[i][splitBy].equals(ob[i-1][splitBy])) {
				//上一行值不同时，把行号作为切割线
				splitLines.add(i);
			}
		}
		
		if (splitLines.size() == 0) {
			//不需要切割
			result.add(ob);
			return result;
		}
		
		for (int i=0; i<=splitLines.size(); i++) {
			if (i==0) {
				Object[][] sub = (Object[][])ArrayUtils.subarray(ob, 0, splitLines.get(i));
				result.add(sub);
			} else if (i==splitLines.size()) {
				Object[][] sub = (Object[][])ArrayUtils.subarray(ob, splitLines.get(i-1), ob.length);
				result.add(sub);
			} else {
				Object[][] sub = (Object[][])ArrayUtils.subarray(ob, splitLines.get(i-1), splitLines.get(i));
				result.add(sub);
			}
		}
		
		return result;
	}
	
	
	/**
	 * 对二维数组进行排序
	 * @param ob 二维数组
	 * @param orderBy 按照二维数据的哪几列进行排序(排序列必须实现Comparable接口)
	 */
	public static void sort(Object[][] ob, final int[] orderBy) {
		Arrays.sort(ob, new Comparator<Object>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public int compare(Object o1, Object o2) {
				Object[] one = (Object[]) o1;
				Object[] two = (Object[]) o2;
				
				for (int i = 0; i < orderBy.length; i++) {
					int k = orderBy[i];
					
					if (one[k] == null) {
						return -1;
					} else if (two[k] == null) {
						return 1;
					}
					
					if (one[k] instanceof Comparable) {
						Comparable element1 = (Comparable)one[k];
						Comparable element2 = (Comparable)two[k];
						
						if(0 == element1.compareTo(element2)){
							// 如果按一条件比较结果相等，就使用第二个条件进行比较。
							continue;
						} else {
							return element1.compareTo(element2);
						}
					} 
				}
				
				return 0;
			}
		});
	}
	
	
	/**
	 * 对二维数组进行排序
	 * @param ob 二维数组
	 * @param orderBy 按照第几列进行排序(排序列必须实现Comparable接口)
	 */
	public static void sort(Object[][] ob, final int orderBy) {
		sort(ob, new int[] {orderBy});
	}
}

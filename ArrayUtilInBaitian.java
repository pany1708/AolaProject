package com.altratek.altraserver.extensions.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Stream;

public class ArrayUtilInBaitian {
	
	public static int sum(int[] arr){
		if(arr == null){
			return 0;
		}
		int sum = 0;
		for(int e : arr){
			sum += e;
		}
		return sum;
	}
	
	public static int sum(List<Integer> list){
		if(list == null || list.isEmpty()){
			return 0;
		}
		int sum = 0;
		for(Integer e : list){
			sum += e;
		}
		return sum;
	}
	
	public static boolean contains(int[] arr, int elem){
		if(arr == null){
			return false;
		}
		for(int e : arr){
			if(e == elem){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取value在arr中的位置index, 使得
	 * <LI>value>=arr[index] && value&lt;arr[index+1];
	 * <LI>若value&lt;arr[0]，则index=-1
	 * <LI>若value&gt;arr的最后一个元素，则index=arr.length-1
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int getRangeIndex(int[] arr, int value){
		if(arr == null){
			return -1;
		}
		int index = -1;
		while(index < arr.length - 1){
			if(value >= arr[index + 1]){
				index++;
			} else {
				break;
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		System.out.println(ArrayUtilInBaitian.getRangeIndex(new int[]{0, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 100000}, 10000000));
	}
	
	/**
	 * 获取value在arr中的位置index, 使得
	 * <LI>value>=arr[index] && value&lt;arr[index+1];
	 * <LI>若value&lt;arr[0]，则index=-1
	 * <LI>若value&gt;arr的最后一个元素，则index=arr.length-1
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int getRangeIndex(long[] arr, long value){
		if(arr == null){
			return -1;
		}
		int index = -1;
		while(index < arr.length - 1){
			if(value >= arr[index + 1]){
				index++;
			} else {
				break;
			}
		}
		return index;
	}
	
	public static int[][] parse(String config, String separator, String subSeparator){
		if(StringUtil.isNullOrEmpty(config)){
			return null;
		}
		String[] configs = config.split(separator);
		int[][] result = new int[configs.length][];
		for(int i=0; i<configs.length; i++){
			if(StringUtil.isNullOrEmpty(configs[i])){
				result[i] = null;
				continue;
			}
			String[] subConfigs = configs[i].split(subSeparator); 
			result[i] = new int[subConfigs.length];
			for(int j=0; j<subConfigs.length; j++){
				result[i][j] = Integer.valueOf(subConfigs[j]);
			}
		}
		return result;
	}

	public static String toString(Object obj) {
		if(obj == null){
			return "null";
		}
		if(obj.getClass().isArray()){
			StringBuilder sb = new StringBuilder("[");
			for(int i=0; i<Array.getLength(obj); i++){
				sb.append(toString(Array.get(obj, i))).append(", ");
			}
			if(sb.length() > 1){
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append(']');
			return sb.toString();
		} else {
			return obj.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] ToConcreteObject(Object[] array, Class<T> type) {
		if(array == null) {
			return null;
		}
		int length = array.length;
		if(length <= 0) {
			return (T[])Array.newInstance(type, 0);
		}
		T[] dest = (T[])Array.newInstance(type, length);
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}
	
	public static void shuffle(int[] original) {
		if(original == null || original.length <= 0) {
			return;
		}
		for(int i=0; i<original.length; i++) {
			int rndPos = RandomUtil.nextInt(original.length);
			int tmp = original[i];
			original[i] = original[rndPos];
			original[rndPos] = tmp;
		}
	}
	
	public static int[] toIntArray(Object[] objArr) {
		if(objArr == null) {
			return null;
		}
		return Stream.of(objArr).mapToInt(o -> ((Number)o).intValue()).toArray();
	}
}

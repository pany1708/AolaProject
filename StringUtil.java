package com.altratek.altraserver.extensions.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringUtil {
	public static String join(AbstractCollection<String> s, String delimiter) {
		if (s.isEmpty())
			return "";
		Iterator<String> iter = s.iterator();
		StringBuffer buffer = new StringBuffer(iter.next());
		while (iter.hasNext())
			buffer.append(delimiter).append(iter.next());
		return buffer.toString();
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static String intArrayToString(int[] array) {
		return intArrayToString(array, ",");
	}

	public static String intArrayToString(int[] array, String separator) {
		if (array == null || array.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int element : array) {
			sb.append(element).append(separator);
		}
		return sb.delete(sb.length() - separator.length(), sb.length()).toString();
	}

	public static String integerArrayToString(Integer[] array, String separator) {
		if (array == null || array.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int element : array) {
			sb.append(element).append(separator);
		}
		return sb.delete(sb.length() - separator.length(), sb.length()).toString();
	}

	/**
	 * 配合intArrayToString以免NumberFormatException
	 */
	public static int[] stringToIntArray(String s, String separator) {
		int[] a = new int[0];
		if (s != null && s.length() > 0) {
			String[] ss = s.split(separator);
			a = new int[ss.length];
			for (int i = 0; i < ss.length; i++) {
				a[i] = Integer.parseInt(ss[i]);
			}
		}
		return a;
	}

	public static Integer[] stringToIntegerArray(String s, String separator) {
		Integer[] a = new Integer[0];
		if (s != null && s.length() > 0) {
			String[] ss = s.split(separator);
			a = new Integer[ss.length];
			for (int i = 0; i < ss.length; i++) {
				a[i] = Integer.parseInt(ss[i]);
			}
		}
		return a;
	}

	public static ArrayList<Integer> stringToArrayList(String s, String separator) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		int[] a = stringToIntArray(s, separator);
		for (int i : a) {
			l.add(i);
		}
		return l;
	}

	public static String arrayListToString(List<Integer> array, String separator) {
		if (array == null || array.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int element : array) {
			sb.append(element).append(separator);
		}
		return sb.delete(sb.length() - separator.length(), sb.length()).toString();
	}
}

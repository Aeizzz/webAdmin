package com.tepusoft.test;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
	private static Configuration configuration =null;
	private static Map<String, Template> allTemplates =null;
	public static void main(String[] args) {
//		sort();
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("title","213");
			File file =createExcel(map,"doc","test.ftl");
			InputStream inputStream = new FileInputStream(file);
			OutputStream outputStream = new FileOutputStream(new File("E://test.doc"));
			int len = 0 ;
			byte[] buff = new byte[4*1024*1024];
			while((len = inputStream.read(buff,0,buff.length)) != -1){
				outputStream.write(buff,0,len);
				outputStream.flush();
			}
			outputStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void sort() {
		int[] arr = new int[]{2,3,5,1,2,4,5,5,4};
		for(int j = 0 ; j < arr.length ;j++){
			int maxIndex = 0;
			int max = arr[0];
			for(int i = 0 ; i < arr.length - j ;i++){
				if(arr[i]>=max){
					max = arr[i];
					maxIndex = i;
				}
			}
			swap(arr,arr.length-1-j,maxIndex);
		}
		System.out.println(Arrays.toString(arr));
	}

	private static void swap(int[] arr, int j, int i) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static File createExcel(Map<?, ?> dataMap, String type, String valueName){
		try {
			configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
			configuration.setDirectoryForTemplateLoading(new File("E://"));
			allTemplates = new HashMap<String, Template>();
			allTemplates.put(type, configuration.getTemplate(valueName));
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		String name = "temp" + (int) (Math.random() * 100000) + ".doc";
		File file = new File(name);
		Template template = allTemplates.get(type);
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			template.process(dataMap, w);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return file;
	}

}

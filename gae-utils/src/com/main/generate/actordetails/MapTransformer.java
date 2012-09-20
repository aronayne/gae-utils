package com.main.generate.actordetails;

import java.util.HashMap;
import java.util.Map;

public class MapTransformer {

	private Map<String , String> m1;
	
	public MapTransformer(Map<String , String> m1){
		this.m1 = m1;
	}
	
	public void transformMap(){
		 Map<String, String> inverseMap = new HashMap<String, String>();
	        for (Map.Entry<String, String> entry : m1.entrySet()) {
	            for (String value : entry.getValue().split(",")) {
	                if (inverseMap.containsKey(value))
	                    inverseMap.put(value, inverseMap.get(value) + "," + entry.getKey());
	                else
	                    inverseMap.put(value, entry.getKey());
	            }
	        }

	        for (Map.Entry<String, String> entry : inverseMap.entrySet()) {
	            String key = entry.getKey();
	            String value = entry.getValue();
	            System.out.println(key+" "+value);
	        }
	}

}
package com.strategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Adrian
 *
 */

public class CreateCsvFile {
	
	private Set<String> filmNames;
	private Map<String, String> filmYearMap;
	private Map<String, String> filmActorMap;
	private Map<String, String> ratingMap;
	private Map<String, String> detailsMap = new HashMap<String , String>();
	private String fileName;
	
	public CreateCsvFile(Set<String> filmNames ,  String fileName){
		this.filmNames = filmNames;
		this.fileName = fileName;
	}
	
	public void generateFile(){

		BufferedWriter out1 = null;
		

		
		for (String filmName : filmNames) {
			
			
			/*
			 * 
			Displaying of actors may be in a seperate service...
			
			
			String filmYear = filmYearMap.get(filmName);

			String actorName = "";
			
			if(filmActorMap != null){
			for (Map.Entry<String, String> entry : filmActorMap.entrySet())
			{
			    String[] a = entry.getValue().split( "," );
			    Set<String> movieNames = new HashSet<String>( Arrays.asList( a ) );
			    for(String movieDetail : movieNames){
			    	if(movieDetail.toLowerCase().trim().contains(filmName.toLowerCase().trim()) && movieDetail.contains(filmYear)){
			    		actorName += entry.getKey().trim();
			 		}
			    }
			}
			actorName = "";
			}
			
			if(filmActorMap != null){
				System.out.println(filmName.trim() + "," + filmYearMap.get(filmName).trim() +","+actorName.trim());
				out1.write(filmName.trim() + "," + filmYearMap.get(filmName).trim() +","+actorName.trim());
				out1.newLine();
			}*/
			
			
			if(ratingMap != null){
				if(ratingMap.containsKey(filmName.trim())){
					String year = filmYearMap.get(filmName).trim();
					String updatedName = filmName.trim().replace(" ("+year+")", "").toUpperCase();
					detailsMap.put(updatedName , year +","+ratingMap.get(filmName.trim()).trim());
/*					System.out.println(filmName.trim() + "," + filmYearMap.get(filmName).trim() +","+ratingMap.get(filmName.trim()).trim());
					out1.write(filmName.trim() + "," + filmYearMap.get(filmName).trim() +","+ratingMap.get(filmName.trim()).trim());
					out1.newLine();*/
				}
			}
			
/*			else {
				System.out.println(filmName.trim() + "," + filmYearMap.get(filmName).trim());
				out1.write(filmName.trim() + "," + filmYearMap.get(filmName).trim());
			}*/
			

			
		}
		
		   Map<String,String> sortedMap = mySort(detailsMap);

		    System.out.println("Output");

		  
			try {
				out1 = new BufferedWriter(new FileWriter(this.fileName));
			    for (Map.Entry entry : sortedMap.entrySet()) {			    	
			    	out1.write(entry.getKey() + "," + entry.getValue());
					out1.newLine();
			        System.out.println("Key: "+entry.getKey()+" value: "+entry.getValue());
			    }
				
		out1.close();
		
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				out1.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static Map<String , String> mySort(Map<String , String> inputMap) {
	    // to list
	    List<String> list = new LinkedList(inputMap.entrySet());

	    Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	            String v1 = (String)((Map.Entry) (o1)).getValue();
	            // split by ,
	            String[] v1Split = v1.split(",");
	            // get last value
	            String v1Value = v1Split[0];

	            // compact syntax
	            String v2Value = (((String)((Map.Entry) (o2)).getValue()).split(","))[0];

	            // TRICK: -1 for reverse
	            return v1Value.compareTo(v2Value) * -1;
	            }
	            });
	 // TRICK: to LinkedMap
	    Map sortedMap = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }
	    return sortedMap;
	}   
	
	public void setFilmActorMap(Map<String, String> filmActorMap) {
		this.filmActorMap = filmActorMap;
	}
	
	public void setRatingMap(Map<String, String> ratingMap) {
		this.ratingMap = ratingMap;
	}
	
	public void setFilmYearMap(Map<String, String> filmYearMap) {
		this.filmYearMap = filmYearMap;
	}
	
}

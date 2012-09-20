package com.strategy.impl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategy.ParseStrategy;
import com.strategy.Parser;



public class CreateMovieActorMapStrategyImpl extends Parser implements ParseStrategy {
	
	private Map<String , String> details = new HashMap<String , String>();
	private int counter;
	
	public CreateMovieActorMapStrategyImpl(String fileNameToRead) {
		super(fileNameToRead);
	}

	private void populateDetailsMap(){
		
		try {

		BufferedReader br = new BufferedReader(new FileReader(super.getFileNameToRead()));
		String line = br.readLine();
		while (line != null)
		{
			//TODO use a regular expression to parse the film name and year
			try {
				 if(line.trim().isEmpty()) {
			 line = br.readLine();	
			 }
				 else {
					 
				 String actorName = "";
					 for (int i = 0; i < line.length(); i++){
						    char c = line.charAt(i); 
						    actorName += c;
						    if(c == '\t'){
						    	i = line.length();
						    }

						} 
				 String filmName = line.substring(actorName.length() , line.length());
				 line = br.readLine();	
				 while(!line.trim().isEmpty()) {
					 line = br.readLine();	
					 filmName += ","+line.trim();
				 }
				 actorName = actorName.replaceAll(",", ";").trim();
				 actorName += ",";
				 details.put(actorName, filmName);	
				 System.out.println("Adding : "+ ++counter+","+actorName +"," +filmName);
				 actorName = "";
			 }
			
			}
			catch(java.lang.StringIndexOutOfBoundsException obe){
				obe.printStackTrace();
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, String> execute() {
		this.populateDetailsMap();
		return this.details;
	}
}

package com.strategy.impl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.strategy.ParseStrategy;
import com.strategy.Parser;



public class CreateMovieRatingMapStrategyImpl extends Parser implements ParseStrategy {
	
	private Map<String , String> details = new HashMap<String , String>();
	
	public CreateMovieRatingMapStrategyImpl(String fileNameToRead) {
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
				
			String[] parts = line.split("\\s+");
			List<String> l = Arrays.asList(parts);
			if(l.size() > 2){
				StringBuilder strB = new StringBuilder();
				
				int counter = 0;
				for(String s : l){
					if(counter > 3){
						strB.append(s+" ");
					}
					++counter;
				}
				
				 String filmName = strB.toString().trim();
				 String filmDetail = l.get(2) +" , "+l.get(3);
				details.put(filmName.trim(), filmDetail.trim());
			}
		 
			 line = br.readLine();	
			}
			catch(Exception obe){
				obe.printStackTrace();
				line = br.readLine();	
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

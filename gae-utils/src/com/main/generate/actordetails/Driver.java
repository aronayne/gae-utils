package com.main.generate.actordetails;

import java.util.Map;

public class Driver {
	
	public static void main(String args[]){
		
		CreateMovieActorMap c = new CreateMovieActorMap("d:\\filmfiles\\actors.list" , 100000);
		Map<String , String> m = c.populateDetailsMap();
		MapTransformer m1 = new MapTransformer(m);
		m1.transformMap();
	}

}

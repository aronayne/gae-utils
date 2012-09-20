package com.main.generate.gendetails;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.exception.CustomException;
import com.filmservice.credential.CredentialDetail;
import com.filmservice.credential.TestCredentialImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.strategy.Context;
import com.strategy.CreateCsvFile;
import com.strategy.CreateFiles;
import com.strategy.impl.CreateMovieActorMapStrategyImpl;
import com.strategy.impl.CreateMovieRatingMapStrategyImpl;
import com.strategy.impl.CreateMovieYearMapStrategyImpl;
import com.upload.UploadEntity;


public class Driver {

	private static RemoteApiInstaller installer;
	/**
	 * Using a file to store the values in case generation process takes a significant amount of time, using seperate file
	 * allows process to be broken into separate tasks
	 */
	private static final String MOVIE_DETAILS_CSV = "d:/tmp/MovieCSV.csv";
	private static final int NUMBER_OF_RECORDS_TO_UPLOAD = 500;
	
	static {
		CredentialDetail c = new TestCredentialImpl();

		RemoteApiOptions options = new RemoteApiOptions().server(c.getServer(), c.getPort()).credentials(c.getUserName(),c.getPassword());
		installer = new RemoteApiInstaller();
		try {
			installer.install(options);
		} catch (IOException e) {
			System.out.println("Check that GAE server web application is running");
			e.printStackTrace();
				System.exit(0);
				

		}
	}
	
	public static void main(String args[]) {

		Context context;

		context = new Context(new CreateMovieYearMapStrategyImpl("d:\\filmfiles\\movies.list"));
		Map<String, String> filmYearMap = context.executeStrategy();

		Set<String> filmNames = filmYearMap.keySet();
		System.out.println("Total films : " + filmNames.size());
		
		/**
		 * Constructor will be amended as new strategys are added
		 */
/*		context = new Context(new CreateMovieActorMapStrategy("d:\\filmfiles\\actors-test.list"));
		Map<String, String> filmActorMap = context.executeStrategy();*/
		
		context = new Context(new CreateMovieRatingMapStrategyImpl("d:\\filmfiles\\ratings.list"));
		Map<String, String> filmRatingMap = context.executeStrategy();
		
		createCsvFile(filmNames , filmRatingMap , filmYearMap);
		
		List<Entity> entityList = getEntityListFromCsv(NUMBER_OF_RECORDS_TO_UPLOAD);	
		UploadEntity.upload(entityList);
		
		installer.uninstall();
	}
	
	private static void createCsvFile(Set<String> filmNames,
			Map<String, String> filmRatingMap, Map<String, String> filmYearMap) {
		
		CreateCsvFile createFiles = new CreateCsvFile(filmNames , MOVIE_DETAILS_CSV);
		createFiles.setRatingMap(filmRatingMap);
		createFiles.setFilmYearMap(filmYearMap);
		createFiles.generateFile();
		
	}
	
	private static List<Entity> getEntityListFromCsv(int amount){
		List<Entity> entityList = new ArrayList<Entity>();
		
		try {

		int counter = 0;
		BufferedReader br = new BufferedReader(new FileReader(MOVIE_DETAILS_CSV));
		String line = br.readLine();
		
		while (line != null && ++counter <= amount)
		{
			//TODO use a regular expression to parse the film name and year
			try {
				String[] parts = line.split(",");
				List<String> l = Arrays.asList(parts);
				Entity movieEntity = new Entity("Movie");
				movieEntity.setProperty("name", l.get(0));
				movieEntity.setProperty("year" , l.get(1));
				movieEntity.setProperty("numberVoters", l.get(2));
				movieEntity.setProperty("rating", l.get(3));
				entityList.add(movieEntity);

			}
			catch(java.lang.StringIndexOutOfBoundsException obe){
				obe.printStackTrace();
			}
			
			 line = br.readLine();	
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return entityList;
	}
	
}

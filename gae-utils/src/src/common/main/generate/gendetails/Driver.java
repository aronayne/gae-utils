package src.common.main.generate.gendetails;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.filmservice.credential.CredentialDetail;
import com.filmservice.credential.TestCredentialImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.upload.UploadEntity;

import src.common.strategy.Context;
import src.common.strategy.CreateCsvFile;
import src.common.strategy.CreateFiles;
import src.common.strategy.impl.CreateMovieActorMapStrategyImpl;
import src.common.strategy.impl.CreateMovieRatingMapStrategyImpl;
import src.common.strategy.impl.CreateMovieYearMapStrategyImpl;

public class Driver {

	private static RemoteApiInstaller installer;
	/**
	 * Using a file to store the values in case generation process takes a significant amount of time, using seperate file
	 * allows process to be broken into separate tasks
	 */
	private static final String MOVIE_DETAILS_CSV = "d:/tmp/MovieCSV.csv";
	
	static {
		CredentialDetail c = new TestCredentialImpl();

		RemoteApiOptions options = new RemoteApiOptions().server(c.getServer(), c.getPort()).credentials(c.getUserName(),c.getPassword());
		installer = new RemoteApiInstaller();
		try {
			installer.install(options);
		} catch (IOException e) {
			e.printStackTrace();
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
		
		List<Entity> entityList = getEntityList();	
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
	
	private static List<Entity> getEntityList(){
		List<Entity> entityList = new ArrayList<Entity>();
		
		try {

		BufferedReader br = new BufferedReader(new FileReader(MOVIE_DETAILS_CSV));
		String line = br.readLine();
		
		while (line != null)
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

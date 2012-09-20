package com.query;

import com.filmservice.credential.CredentialDetail;
import com.filmservice.credential.TestCredentialImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class DataStoreQuery {

	private static final String KIND = "Movie";
	
	public static void main(String args[]) {
		// Get the Datastore Service

		try {

			CredentialDetail c = new TestCredentialImpl();

			RemoteApiOptions options = new RemoteApiOptions().server(
					c.getServer(), c.getPort()).credentials(c.getUserName(),
					c.getPassword());
			RemoteApiInstaller installer = new RemoteApiInstaller();
			installer.install(options);

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Filter filter = new FilterPredicate("year",FilterOperator.EQUAL, "2004");
			Query q = new Query(KIND).setFilter(filter);
			PreparedQuery pq = datastore.prepare(q);
			for (Entity result : pq.asIterable()) {
				System.out.println((String) result.getProperty("name"));
			}

			System.out.println("Finished!");
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

/*
 * Filter heightMinFilter = new FilterPredicate("height",
 * FilterOperator.GREATER_THAN_OR_EQUAL, minHeight);
 * 
 * Filter heightMaxFilter = new FilterPredicate("height",
 * FilterOperator.LESS_THAN_OR_EQUAL, maxHeight);
 * 
 * //Use CompositeFilter to combine multiple filters Filter heightRangeFilter =
 * CompositeFilterOperator.and(heightMinFilter, heightMaxFilter);
 * 
 * 
 * // Use class Query to assemble a query Query q = new
 * Query("Person").setFilter(heightRangeFilter);
 * 
 * // Use PreparedQuery interface to retrieve results PreparedQuery pq =
 * datastore.prepare(q);
 * 
 * 
 * for (Entity result : pq.asIterable()) { String firstName = (String)
 * result.getProperty("firstName"); String lastName = (String)
 * result.getProperty("lastName"); Long height = (Long)
 * result.getProperty("height");
 * 
 * System.out.println(firstName + " " + lastName + ", " + height +
 * " inches tall"); }
 */


package com.upload;

import com.filmservice.credential.CredentialDetail;
import com.filmservice.credential.TestCredentialImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import java.io.IOException;
import java.util.List;

public class UploadEntity {

	private static DatastoreService ds;

	static {

		try {
			ds = DatastoreServiceFactory.getDatastoreService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void upload(List<Entity> entityList) {
		try {
			int counter = 0;
			for(Entity e : entityList){
				System.out.println("Uploading details for : "+e.getProperty("name")+" , "+ ++counter+" of "+entityList.size());
				ds.put(e);
			}
		} 
		catch(Exception e){
			e.printStackTrace();
		}

	}

}

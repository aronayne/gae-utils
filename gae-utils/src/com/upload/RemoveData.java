package com.upload;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import java.io.IOException;

public class RemoveData {
    public static void main(String[] args) throws IOException {
        String username = "adrian.ronayne@gmail.com";
        String password = "computer1290";
        RemoteApiOptions options = new RemoteApiOptions().server("film-service.appspot.com", 443).credentials(username, password);
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        try {

            
            for(int counter = 0; counter <= 5000; ++counter){
            Entity movie = new Entity("movie");

            System.out.println("Deleting "+movie.getKey());
            ds.delete(movie.getKey());

            }
        } finally {
            installer.uninstall();
        }
    }
}

package com.edison.resources.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mrugen on 5/7/15.
 */
@Component
public class DataService {
    @Autowired
    MongoClient client;
    public void storeCoordinates(Double lat, Double lon, int vacancy) {
        final DBCollection parking = client.getDB("edison").getCollection("parking");
        final ArrayList<Double> coordinates = new ArrayList<Double>();
        coordinates.add(lon); coordinates.add(lat);

        final BasicDBObject filter = new BasicDBObject().append("coordinates",coordinates);
        final BasicDBObject update = new BasicDBObject().append("coordinates",coordinates).append("type","Point").append("vac",vacancy);
        if(parking.findOne(filter)!=null){
            parking.update(filter,update);
        }else{
            parking.insert(update);
        }
    }
}

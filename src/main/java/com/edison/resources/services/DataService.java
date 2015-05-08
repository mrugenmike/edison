package com.edison.resources.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<DBObject> findNearest(Double lat, Double lon) {
        final DBCollection parking = client.getDB("edison").getCollection("parking");
        final BasicDBObject query = new BasicDBObject("type", "Point");
        final BasicDBObject geometry = new BasicDBObject("$geometry", query.append("coordinates", Arrays.asList(lon, lat)));
        final BasicDBObject near = new BasicDBObject("$near", new BasicDBObject(geometry));
        final BasicDBObject coordinates = new BasicDBObject("coordinates", near);
        final List<DBObject> nearbySpots = parking.find(coordinates).toArray();
        if(nearbySpots!=null && !nearbySpots.isEmpty()){
            return nearbySpots.stream().map(spot->{spot.removeField("_id");return spot;}).collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }
    }
}

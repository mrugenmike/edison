package com.edison.resources;

import com.edison.resources.services.DataService;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class DataResource {

    @Autowired
    DataService dataService;

    @RequestMapping("/dashboard")
    String getDashBoard(){
        return "index";
    }

    @RequestMapping("/api/v1/data")
    ResponseEntity<String> storeCoordinates(@RequestParam("lat")double lat,@RequestParam("lon") double lon,@RequestParam("vac") int vacancy){
        dataService.storeCoordinates(lat,lon,vacancy);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/api/v1/nearby/{lon}/{lat}")
   @ResponseBody List<DBObject> findNearest(@PathVariable("lon") Double lon, @PathVariable("lat") Double lat){
        final List<DBObject> nearestSpots = dataService.findNearest(lat, lon);
        if (nearestSpots.isEmpty()){
            throw new NoContentException(String.format("No nearby parking spots found lat:%s lon:%s",lat,lon));
        }
        return nearestSpots;
    }
}

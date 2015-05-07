package com.edison.resources;

import com.edison.resources.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataResource {

    @Autowired
    DataService dataService;

    @RequestMapping("/dashboard")
    String getDashBoard(){
        return "Dashboard";
    }

    @RequestMapping("/api/v1/data")
    ResponseEntity<String> storeCoordinates(@RequestParam("lat")double lat,@RequestParam("lon") double lon,@RequestParam("vac") int vacancy){
        dataService.storeCoordinates(lat,lon,vacancy);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}

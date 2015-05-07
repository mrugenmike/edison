package com.edison.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataResource {

    @RequestMapping("/dashboard")
    String getDashBoard(){
        return "Dashboard";
    }
}

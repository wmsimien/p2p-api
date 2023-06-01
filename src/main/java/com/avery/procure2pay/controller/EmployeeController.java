package com.avery.procure2pay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class EmployeeController {

    /**
     * Test Method
     * @return
     */
    @GetMapping(path="/hello/")
    public String hello() {
        return "Hello, Family";
    }
}

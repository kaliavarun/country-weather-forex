package com.rest.countrydata.controler;

import com.rest.countrydata.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
public class CountryDataController {

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ReportDTO> getCountryReports() {
        // Exception handling not implemented for simplicity. Will implement ContollerAdvice and Exception handler in next version.
       return reportService.generateReport();
    }
}

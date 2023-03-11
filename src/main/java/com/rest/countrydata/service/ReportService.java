package com.rest.countrydata.service;

import com.rest.countrydata.controler.ReportDTO;

import java.util.List;

/**
 * services created specifically for this application
 * in order to handle retrieve data, persist in database and generate report in
 * single transaction.
 */
public interface ReportService {
    public List<ReportDTO> generateReport();
}

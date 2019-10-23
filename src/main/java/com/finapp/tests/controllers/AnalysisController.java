package com.finapp.tests.controllers;

import com.finapp.tests.services.dbdao.TransactionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Class name: AnalysisController
 * Creater: wgicheru
 * Date:10/3/2019
 */
@RestController("/trans")
public class AnalysisController {
    @Autowired
    TransactionsDao transactionsDao;

    @GetMapping("/analysis/{measure}/{period}")
    public void analyzePeriod(@PathVariable("measure") String measure,@PathVariable("period") String period,
                              @RequestParam("startdate")Date start, @RequestParam("enddate") Date end){
        //measure should be either sum or average

        //period should be a value in TransactionPeriods

    }
}

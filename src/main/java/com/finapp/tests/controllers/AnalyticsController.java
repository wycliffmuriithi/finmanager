package com.finapp.tests.controllers;

import com.finapp.tests.services.dbdao.TransactionsDao;
import com.finapp.tests.wrappers.enums.TransactionPeriods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Class name: AnalyticsController
 * Creater: wgicheru
 * Date:10/29/2019
 */
@RestController
@RequestMapping("/trans")
public class AnalyticsController {
    @Autowired
    TransactionsDao transactionsDao;

    @GetMapping("/analysis/{period}")
    public void analyzePeriod(@PathVariable("period") String period,
                              @RequestParam("startdate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date start,
                              @RequestParam("enddate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date end){


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            //period should be a value in TransactionPeriods
            if (TransactionPeriods.contains(period)) {
                Calendar startcal = Calendar.getInstance();
                startcal.setTime(start);

                Calendar endcal = Calendar.getInstance();
                endcal.setTime(end);
                transactionsDao.userTransactionsperPeriod(TransactionPeriods.valueOf(period), startcal, endcal, auth.getName());
            }

        //failed, wrong input
    }
}

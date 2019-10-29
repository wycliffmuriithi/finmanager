package com.finapp.tests.controllers;

import com.finapp.tests.services.dbdao.TransactionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class name: AnalyticsController
 * Creater: wgicheru
 * Date:10/29/2019
 */
@RestController
@RequestMapping("/analyse")
public class AnalyticsController {
    @Autowired
    TransactionsDao transactionsDao;


}

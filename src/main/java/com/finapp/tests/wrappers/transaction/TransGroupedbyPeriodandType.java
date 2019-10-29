package com.finapp.tests.wrappers.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class name: TransGroupedbyPeriodandType
 * Creater: wgicheru
 * Date:10/29/2019
 */
@Data @AllArgsConstructor
public class TransGroupedbyPeriodandType {
    TransDetails transDetails;
    double total;
    int freq;

    @Data @AllArgsConstructor
    public class TransDetails{
        int period;
        String transtype;
    }
}

package com.finapp.tests.wrappers.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Class name: TransGroupedbyPeriodandType
 * Creater: wgicheru
 * Date:10/29/2019
 */
@Data @AllArgsConstructor @ToString
public class TransGroupedbyPeriodandType {
    TransDetails transDetails;
    double total;
    int freq;

    @Data @AllArgsConstructor @ToString
    public class TransDetails{
        int period;
        String transtypeid;
    }
}

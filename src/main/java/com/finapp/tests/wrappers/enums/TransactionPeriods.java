package com.finapp.tests.wrappers.enums;

/**
 * Class name: TransactionPeriods
 * Creater: wgicheru
 * Date:10/29/2019
 */
public enum TransactionPeriods {
    daily, weekly, monthly, yearly;

    public static boolean contains(String s)
    {
        for(TransactionPeriods choice:values())
            if (choice.name().equals(s))
                return true;
        return false;
    }
}

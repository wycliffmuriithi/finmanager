package com.finapp.tests.database.entities;

import com.finapp.tests.wrappers.enums.TransStatus;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Class name: MpesaTransactions
 * Creater: wgicheru
 * Date:9/1/2019
 */
@Data
@RequiredArgsConstructor
public class MpesaTransactions extends BaseEntity {
    @NonNull
    private double amount;
    @NonNull
    private double balance;
    @NonNull
    private String details;
    @NonNull
    private Date time;
    @NonNull
    private String transid;
    @NonNull
    private String user;
    @NonNull
    private String processed;
    private String transtypeid;

}

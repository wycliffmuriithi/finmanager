package com.finapp.tests.database.entities;

import com.finapp.tests.wrappers.enums.TransStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

/**
 * Class name: MpesaTransactions
 * Creater: wgicheru
 * Date:9/1/2019
 */
@Data
@RequiredArgsConstructor @Entity @Table(name="tblmpesatransactions") @NoArgsConstructor
public class MpesaTransactions extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    public String id;
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
    private long transtypeid;

}

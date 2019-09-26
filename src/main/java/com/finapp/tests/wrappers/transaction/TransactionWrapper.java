package com.finapp.tests.wrappers.transaction;

import com.finapp.tests.services.mpesa.statements.StatmntTrans;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Class name: TransactionWrapper
 * Creater: wgicheru
 * Date:7/19/2019
 */
@Data @AllArgsConstructor
public class TransactionWrapper {
    private boolean success;
    private String description;
    private List<StatmntTrans> transactions;
}

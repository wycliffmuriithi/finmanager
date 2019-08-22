package com.finapp.tests.database.entities;

import lombok.Data;
import org.bson.types.Binary;

/**
 * Class name: MpesaStatements
 * Creater: wgicheru
 * Date:8/7/2019
 */
@Data
public class MpesaStatements extends BaseEntity{
    private Binary statementfile;
    private String filename;
    private String user;
}

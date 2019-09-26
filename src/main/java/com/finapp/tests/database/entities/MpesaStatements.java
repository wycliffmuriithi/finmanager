package com.finapp.tests.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
//import org.bson.types.Binary;

import javax.persistence.*;

/**
 * Class name: MpesaStatements
 * Creater: wgicheru
 * Date:8/7/2019
 */
@Data  @Entity @Table(name="tblmpesastatements") @NoArgsConstructor
public class MpesaStatements extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    public String id;
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] statementfile;
    private String filename;
    private String user;
}

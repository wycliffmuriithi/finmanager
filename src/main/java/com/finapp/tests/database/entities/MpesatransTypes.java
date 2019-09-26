package com.finapp.tests.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Class name: MpesatransTypes
 * Creater: wgicheru
 * Date:9/17/2019
 */
@Data
@RequiredArgsConstructor  @Entity @Table(name="tblmpesatranstypes") @NoArgsConstructor
public class MpesatransTypes extends BaseEntity  {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    public String id;
    @NonNull
    String transclass;
    @NonNull
    String transtype;
}

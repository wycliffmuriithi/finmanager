package com.finapp.tests.database.entities;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 * in mongodb, the timestamp of insertion can be retrieved using
 *    ObjectId("567a68517507b377a0a20903").getTimestamp()
 * this returns an ISO date
 *    ISODate("2015-12-23T09:24:33Z")
 * Class name: BaseEntity
 * Creater: wgicheru
 * Date:7/25/2019
 */
@Data @MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    public Date createdon;
}

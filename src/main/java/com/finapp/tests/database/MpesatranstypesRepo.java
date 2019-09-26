package com.finapp.tests.database;

import com.finapp.tests.database.entities.MpesatransTypes;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * interface name: MpesatranstypesRepo
 * Creater: wgicheru
 * Date:9/17/2019
 */
public interface MpesatranstypesRepo extends JpaRepository<MpesatransTypes,Long> {
    long countByTransclassAndTranstype(String trans_class,String trans_type);
    MpesatransTypes findByTransclassAndTranstype(String trans_class, String trans_type);
}

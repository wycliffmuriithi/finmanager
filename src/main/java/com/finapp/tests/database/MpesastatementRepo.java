package com.finapp.tests.database;

import com.finapp.tests.database.entities.MpesaStatements;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * interface name: MpesastatementRepo
 * Creater: wgicheru
 * Date:8/7/2019
 */
public interface MpesastatementRepo extends MongoRepository<MpesaStatements,String> {
    List<MpesaStatements> findByUser(String user);
    List<MpesaStatements> findByUserAndFilename(String user,String filename);
}

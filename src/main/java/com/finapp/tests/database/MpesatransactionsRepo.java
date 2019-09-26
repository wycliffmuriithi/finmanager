package com.finapp.tests.database;

import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.wrappers.enums.TransStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * interface name: MpesatransactionsRepo
 * Creater: wgicheru
 * Date:9/1/2019
 */
public interface MpesatransactionsRepo  extends MongoRepository<MpesaTransactions,String> {
    boolean existsByTransid(String transid);
    int countByUser(String user);

    long countByProcessed(String transStatus);
//    long countByProcessedAndTranstypeidNotNull(TransStatus transStatus);

//    Page findAllByTranstypeidIsNull(Pageable pageable);
    Page<MpesaTransactions> findByProcessed(Pageable pageable);
    Page<MpesaTransactions> findByProcessed(Pageable pageable,String processed);
}

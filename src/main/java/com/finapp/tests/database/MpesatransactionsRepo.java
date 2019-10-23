package com.finapp.tests.database;

import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.wrappers.enums.TransStatus;
import com.finapp.tests.wrappers.transaction.TransactionperPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * interface name: MpesatransactionsRepo
 * Creater: wgicheru
 * Date:9/1/2019
 */
public interface MpesatransactionsRepo  extends JpaRepository<MpesaTransactions,Long> {

    long countByProcessed(String transStatus);
    Page<MpesaTransactions> findByProcessed(Pageable pageable,String processed);

    /*
    allow a set of group by transaction for users
    ---daily (must be within a month)
        get the sum/average amount spent a day per transaction
        only works within a single month
    ---weekly(must be within a year)
        get the sum/average amount spent a week per transaction
        only works within a single year
    ---monthly(within a year)
        same as weekly
    ---yearly
        this should have some really nice insights
     */

    @Query(nativeQuery = true,value = "SELECT avg(amount) AS amount, DAY(time) AS period,transtypeid FROM " +
            "tblmpesatransactions WHERE " +
            "`user`=?1 AND  time BETWEEN ?2 AND ?3 GROUP BY period,transtypeid")
    List<TransactionperPeriod> dailyAvgPerUserandTranstype(String user, Date start, Date end);

    @Query(nativeQuery = true,value = "SELECT SUM(amount) AS amount, DAY(time) AS period,transtypeid FROM " +
            "tblmpesatransactions WHERE " +
            "`user`=?1 AND time BETWEEN ?2 AND ?3 GROUP BY period,transtypeid")
    List<TransactionperPeriod>  dailyTotalPerUserandTranstype(String user, Date start, Date end);
}

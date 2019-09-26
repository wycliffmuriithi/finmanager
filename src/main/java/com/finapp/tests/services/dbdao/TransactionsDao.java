package com.finapp.tests.services.dbdao;

import com.finapp.tests.database.MpesatransactionsRepo;
import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.wrappers.enums.TransStatus;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Class name: TransactionsDao
 * Creater: wgicheru
 * Date:9/1/2019
 */
@Service
@EnableScheduling
public class TransactionsDao {
    private static final Logger LOGGER = Logger.getLogger(TransactionsDao.class);
    @Autowired
    MpesatransactionsRepo mpesatransactionsRepo;

    /**
     * perform crud operations...
     * the main purpose behind this class is to maintain
     * integrity in transaction data
     */

    /**
     * this method should only present transactions that have been processed by the transactiontypeFactory
     * it should flag the transaction so as not to process it afterwards
     *
     * @param pagesize
     * @param pagenumber
     * @return
     */
    public Optional<List<MpesaTransactions>> mpesa_transtotalsPager(int pagesize, int pagenumber) {
        long recordcount = mpesatransactionsRepo.countByProcessed(TransStatus.mom.name());
        long totalpages = Math.round(recordcount / pagesize);

        if (pagenumber < totalpages && recordcount > 0) {
            LOGGER.info("raw page size "+pagesize+" record size "+recordcount);
//            pagesize = pagenumber == totalpages - 1 ? (int) recordcount % pagesize : pagesize;
            LOGGER.info("retrieving mpesa transactions page " + pagenumber + " size " + pagesize);
            Page<MpesaTransactions> mpesatransactionPage = mpesatransactionsRepo.findByProcessed(PageRequest.of(pagenumber, pagesize), TransStatus.mom.name());

            return Optional.of(mpesatransactionPage.getContent());
        } else {
            LOGGER.info("reached end of data..reset counters");
            return Optional.empty();
        }
    }

    public Optional<List<MpesaTransactions>> mpesa_transtypePager(int pagesize, int pagenumber) {
        long recordcount = mpesatransactionsRepo.countByProcessed(TransStatus.virgin.name());
        long totalpages = Math.round(recordcount / pagesize);

        if (pagenumber < totalpages && recordcount > 0) {
            LOGGER.info("2_raw page size "+pagesize+" record size "+recordcount);
//            pagesize = pagenumber == totalpages - 1 ? (int) recordcount % pagesize : pagesize;
            LOGGER.info("2_retrieving mpesa transactions page " + pagenumber + " size " + pagesize);
            Page<MpesaTransactions> mpesatransactionPage = mpesatransactionsRepo.findByProcessed(PageRequest.of(pagenumber, pagesize), TransStatus.virgin.name());

            return Optional.of(mpesatransactionPage.getContent());
        } else {
            LOGGER.info("reached end of data..reset counters");
            return Optional.empty();
        }
    }


    public void saveTrans(List<MpesaTransactions> mpesaTransactionslist) {
        mpesatransactionsRepo.saveAll(mpesaTransactionslist);
    }


}

package com.finapp.tests.services.dbdao.workers;

import com.finapp.tests.database.MpesatranstypesRepo;
import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.database.entities.MpesatransTypes;
import com.finapp.tests.services.dbdao.TransactionsDao;
import com.finapp.tests.wrappers.enums.TransStatus;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Class name: TransactiontypeFactory
 * Creater: wgicheru
 * Date:9/20/2019
 */
@Service
public class TransactiontypeFactory {
    private static final Logger LOGGER = Logger.getLogger(TransactiontypeFactory.class);
    @Autowired
    TransactionsDao transactionsDao;
    @Autowired
    MpesatranstypesRepo mpesatranstypesRepo;

    int pagesize=50,pagenumber=0;

    /**
     * at the very basic transaction can  be FT or Charge
     * this will be identified as class...
     * a class will have different transaction types
     */
    @Scheduled(fixedDelay = 1000 * 10)
    void populateTransactiontypes() {
        LOGGER.info("factory routine sweep at "+new Date().toString());
        Optional<List<MpesaTransactions>> mpesaTransactionsContainer = transactionsDao.mpesa_transtypePager(pagesize,pagenumber);
        if (mpesaTransactionsContainer.isPresent()) {
            List<MpesaTransactions> mpesaTransactionsList = mpesaTransactionsContainer.get();
            mpesaTransactionsList.stream().forEach((transaction) -> {
                //check the trans and classify it
                String trans_class = transaction.getDetails().contains("Charge") ? "CHARGE" : "FT";
                //Update trans types..pick the first two names from details
                String temp[] = transaction.getDetails().split(" ");
                String trans_type = temp[0].concat(" ").concat(temp[1]);
//                transaction.setTransclass(trans_class);
//                transaction.setTranstype(trans_type);
                long exists = mpesatranstypesRepo.countByTransclassAndTranstype(trans_class,trans_type);
                if(exists==0){
                    //save new type-class combination
                    MpesatransTypes mpesatransTypes = new MpesatransTypes(trans_class,trans_type);
                    mpesatranstypesRepo.save(mpesatransTypes);
                }

                transaction.setTranstypeid(
                        mpesatranstypesRepo
                                .findByTransclassAndTranstype(trans_class,trans_type)
                                .get_id());
                transaction.setProcessed(TransStatus.mom.name());
            });

            transactionsDao.saveTrans(mpesaTransactionsList);
            pagenumber++;
        }else{
            //reset counter
            pagenumber=0;
        }
    }
}

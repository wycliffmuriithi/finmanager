package com.finapp.tests.services.dbdao.workers;

import com.finapp.tests.database.TotalsbytranstypeRepo;
import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.database.entities.TotalsbyTranstype;
import com.finapp.tests.services.dbdao.TransactionsDao;
import com.finapp.tests.wrappers.enums.TransStatus;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Class name: TranstypetotalsFactory
 * Creater: wgicheru
 * Date:9/20/2019
 */
@Service
public class TranstypetotalsFactory {
    private static final Logger LOGGER = Logger.getLogger(TranstypetotalsFactory.class);
    @Autowired
    TotalsbytranstypeRepo totalsbytranstypeRepo;
    @Autowired
    TransactionsDao transactionsDao;

    int pagesize = 50, pagenumber = 0;

    /**
     * take a group of transactions, determine which user they belong to
     * group it to type...
     * then process it by incrementing the proper counters
     */
    @Scheduled(fixedRate = 1000 * 6)
    void calculateTotalsbytranstypeperUser() {
        Optional<List<MpesaTransactions>> mpesaTransactionsContainer = transactionsDao.mpesa_transtotalsPager(pagesize, pagenumber);
        if (mpesaTransactionsContainer.isPresent()) {
            List<MpesaTransactions> mpesaTransactionsList = mpesaTransactionsContainer.get();

            mpesaTransactionsList.stream().forEach((mpesaTransactions -> {
                //get the user
                String user = mpesaTransactions.getUser();
                //get type
                String transtypeid = mpesaTransactions.getTranstypeid();

                List<TotalsbyTranstype> totalsbyTranstypeList = totalsbytranstypeRepo.findByUserAndTranstypeid(user, transtypeid);
                TotalsbyTranstype totalsbyTranstype;
                if (totalsbyTranstypeList.isEmpty()) {
                    totalsbyTranstype = new TotalsbyTranstype(user, 0, transtypeid, 0);
                } else {
                    totalsbyTranstype = totalsbyTranstypeList.get(0);
                }

                totalsbyTranstype.addAmount(mpesaTransactions.getAmount());
                totalsbytranstypeRepo.save(totalsbyTranstype);
                mpesaTransactions.setProcessed(TransStatus.grandma.name());
            }));
            transactionsDao.saveTrans(mpesaTransactionsList);
            pagenumber++;
        } else {
            pagenumber = 0;
        }
    }
}

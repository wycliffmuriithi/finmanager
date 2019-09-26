package com.finapp.tests.services.mpesa.transactions;

import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.services.dbdao.StatementsDao;
import com.finapp.tests.services.dbdao.TransactionsDao;
import com.finapp.tests.services.mpesa.statements.ReadMpesastatement;
import com.finapp.tests.wrappers.enums.TransStatus;
import com.finapp.tests.wrappers.transaction.TransactionWrapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class name: TransactionFactory
 * Creater: wgicheru
 * Date:9/2/2019
 */
@Service
public class TransactionFactory {
    private static final Logger LOGGER = Logger.getLogger(TransactionFactory.class);
    @Autowired
    TransactionsDao transactionsDao;
    @Autowired
    StatementsDao statementsDao;
    @Autowired
    ReadMpesastatement readMpesastatement;


    /**
     * get the file from statements, take it through read statements then save it to transactions
     *
     * @param user
     * @param password
     */
    public void readMpesatransfromFile(String user, String password) {
        List<Resource> files = statementsDao.retrieveFilesbyuser(user);

        for (Resource fileresource : files) {
            try {
                //read the statement..this method should be async
                TransactionWrapper transactionWrapper = readMpesastatement.simplePDFReader(fileresource.getInputStream(), password);
                if (transactionWrapper.isSuccess()) {
                    List<MpesaTransactions> mpesaTransactionsList = transactionWrapper.getTransactions().stream().map(statmntTrans ->
                            new MpesaTransactions(statmntTrans.getAmount(), statmntTrans.getBalance(),
                                    statmntTrans.getDetails(), statmntTrans.getTime(), statmntTrans.getTransid(), user, TransStatus.virgin.name())
                    ).collect(Collectors.toList());
                    //save to database...should be async too
                    transactionsDao.saveTrans(mpesaTransactionsList);
                } else {
                    //get the error
                    String error = transactionWrapper.getDescription();
                    LOGGER.error(error);
                }
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}

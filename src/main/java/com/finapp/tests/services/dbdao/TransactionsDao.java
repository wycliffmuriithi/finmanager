package com.finapp.tests.services.dbdao;

import com.finapp.tests.database.MpesatransactionsRepo;
import com.finapp.tests.database.entities.MpesaTransactions;
import com.finapp.tests.wrappers.enums.TransStatus;
import com.finapp.tests.wrappers.enums.TransactionPeriods;
import com.finapp.tests.wrappers.transaction.TransGroupedbyPeriodandType;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
    @Autowired
    MongoTemplate mongoTemplate;

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
            LOGGER.info("raw page size " + pagesize + " record size " + recordcount);
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
            LOGGER.info("2_raw page size " + pagesize + " record size " + recordcount);
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


    /**
     * db.mpesaTransactions.aggregate([
     * {"$project" : {
     * monthperformed: {$month:"$time"},transtypeid:"$transtypeid",amount:"$amount"
     * }},
     * {"$group" : {
     * _id:{monthperformed:"$monthperformed",transtype:"$transtypeid"},
     * total: {$sum:"$amount"},freq:{$sum:1}
     * }},
     * {$sort:{"_id.monthperformed":-1}}
     * ])
     */
    private List<TransGroupedbyPeriodandType> groupbyPeriodandTranstype(String period, Date startdate, Date enddate,String user) {
        String periodoperation = period.concat("(time)");

        MatchOperation dateFilter = match(Criteria.where("time").lt(enddate).gt(startdate).and("user").is(user));

        ProjectionOperation getPeriod = project("id", "transtypeid", "amount")
                .andExpression(periodoperation).as("period");

        GroupOperation groupOperation = group("period", "transtypeid")
                    .avg("amount").as("average")
                    .sum("amount").as("total")
                    .count().as("freq");


        SortOperation sortbyPeriod = sort(Sort.Direction.DESC, "_id.period");

        ProjectionOperation maptoModel = project("total", "freq")
                .andExpression("_id").as("transDetails");

        Aggregation aggregation = newAggregation(dateFilter, getPeriod, groupOperation, sortbyPeriod, maptoModel);

        AggregationResults<TransGroupedbyPeriodandType> groupbyResults =
                mongoTemplate.aggregate(aggregation, "mpesaTransactions", TransGroupedbyPeriodandType.class);
        return groupbyResults.getMappedResults();
    }


    public void userTransactionsperPeriod(TransactionPeriods periodtype, Calendar startdate, Calendar enddate, String user) {
//        List<TransactionperPeriod> transactionperPeriodList= new ArrayList<>();
        switch (periodtype) {
            case daily:
                //the start date and end date should be in same month
                if (startdate.get(Calendar.MONTH) == enddate.get(Calendar.MONTH)) {
                    List<TransGroupedbyPeriodandType> results =
                            groupbyPeriodandTranstype("dayOfMonth", startdate.getTime(), enddate.getTime(),user);
                    for (TransGroupedbyPeriodandType data : results) {
                        LOGGER.info(data.toString());
                    }
                }
            case weekly:
                //the start date and end date should be in same year
                if (startdate.get(Calendar.YEAR) == enddate.get(Calendar.YEAR)){
                    List<TransGroupedbyPeriodandType> results =
                            groupbyPeriodandTranstype("week", startdate.getTime(), enddate.getTime(),user);
                    for (TransGroupedbyPeriodandType data : results) {
                        LOGGER.info(data.toString());
                    }
                }
            case monthly:
                //the start date and end date should be in same year
                if (startdate.get(Calendar.YEAR) == enddate.get(Calendar.YEAR)) {
                    List<TransGroupedbyPeriodandType> results =
                            groupbyPeriodandTranstype("month", startdate.getTime(), enddate.getTime(),user);
                    for (TransGroupedbyPeriodandType data : results) {
                        LOGGER.info(data.toString());
                    }
                }
            case yearly:
                List<TransGroupedbyPeriodandType> results =
                        groupbyPeriodandTranstype("year", startdate.getTime(), enddate.getTime(),user);
                for (TransGroupedbyPeriodandType data : results) {
                    LOGGER.info(data.toString());
                }
                break;
            default:
                break;
        }
//        return transactionperPeriodList;
    }

}

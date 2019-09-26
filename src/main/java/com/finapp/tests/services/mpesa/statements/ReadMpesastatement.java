package com.finapp.tests.services.mpesa.statements;


import com.finapp.tests.wrappers.transaction.TransactionWrapper;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class name: ReadMpesastatement
 * Creater: wgicheru
 * Date:7/16/2019
 * <p>
 * as of 16th July 2019, an MPESA Statement document is a password protected PDF
 * the PDF has several pages depending on the number of transactions
 * the first page has two tables
 * 1. summary
 * 2. detailed statement
 * <p>
 * this solution seeks to go through the detailed statement and transform that
 * data into consumable format that can be analyzed
 */
@Service
public class ReadMpesastatement {
    PdfReader pdfReader;
    private static final Logger LOGGER = Logger.getLogger(ReadMpesastatement.class);

//    public static void main(String[] args) {
//        ReadMpesastatement readMpesastatement = new ReadMpesastatement();
//        try {
//            readMpesastatement.pdfReader = new PdfReader("C:\\Users\\wgicheru\\Downloads\\DEACONS.pdf", "3802188".getBytes());
//            LOGGER.info("number of pages " + readMpesastatement.pdfReader.getNumberOfPages());
//        }catch (BadPasswordException ex){
//            LOGGER.info("incorrect password");
//        }catch (Exception e){
//            LOGGER.error(e.getMessage(),e);
//        }
//    }



    /**
     * this method reads a specific file in a particular location
     */
    @Async
    public TransactionWrapper simplePDFReader(InputStream statementstream, String password) {
        List<StatmntTrans> statements = new ArrayList<>();
        try {
            pdfReader = new PdfReader(statementstream, password.getBytes());
            int pages = pdfReader.getNumberOfPages();
            int counter = 1;
            while (counter <= pages) {
                String rawpage = PdfTextExtractor.getTextFromPage(pdfReader, counter);
                String[] raw_transactions = rawpage.split("\n");
                int arrayindex = 0;

                for (String transaction : raw_transactions) {
                    if (transaction.matches("^\\b[A-Z0-9]{10}\\b\\s[0-9].*")) {
                        StatmntTrans statmntTrans = new StatmntTrans();
                        int nextindex = arrayindex + 1 == raw_transactions.length ? arrayindex : arrayindex + 1;
//                        LOGGER.info(transaction);
                        statmntTrans.readStatement(transaction, raw_transactions[nextindex]);
                        statements.add(statmntTrans);
                    }
                    arrayindex++;
                }
                counter++;
            }
            return new TransactionWrapper(true,"statement processed ",statements);
        }catch (BadPasswordException ex){
            LOGGER.info("incorrect password");
            return new TransactionWrapper(false,"incorrect password",new ArrayList<>());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new TransactionWrapper(false,ex.getMessage(),new ArrayList<>());
        }
    }

}

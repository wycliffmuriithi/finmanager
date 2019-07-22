package com.finapp.tests.mpesa.statements;


import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.jboss.logging.Logger;

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
public class ReadMpesastatement {
    PdfReader pdfReader;
    private static final Logger LOGGER = Logger.getLogger(ReadMpesastatement.class);

    public static void main(String[] args) {
        new ReadMpesastatement().simplePDFReader();
    }

    /**
     * this method reads a specific file in a particular location
     */
    public void simplePDFReader() {
        String filepath = "C:\\Users\\wgicheru\\Downloads\\MPESA_Statement_20180716_to_20190716_254715702887.pdf";
        try {
            pdfReader = new PdfReader(filepath, "30802188".getBytes());
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
//
                        statmntTrans.readStatement(transaction, raw_transactions[nextindex]);
                        LOGGER.info(statmntTrans);
                    }
                    arrayindex++;
                }
                counter++;
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}

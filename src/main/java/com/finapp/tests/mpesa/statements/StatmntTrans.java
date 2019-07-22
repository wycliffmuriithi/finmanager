package com.finapp.tests.mpesa.statements;

import lombok.Data;
import org.jboss.logging.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class name: StatmntTrans
 * Creater: wgicheru
 * Date:7/19/2019
 */
@Data
public class StatmntTrans {
    private String transid;
    private Date time;
    private double amount;
    private double balance;
    private String details;
    private DateFormat df;
    private final static Logger LOGGER = Logger.getLogger(StatmntTrans.class);

    public void readStatement(String rawstatement, String nexttrans) {
//        LOGGER.info(rawstatement+" "+nexttrans);
        //transaction at this part is valid, get individual parts
        String[] transelements = rawstatement.split(" ");
        details = "";
        transid = transelements[0];
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            amount = Double.valueOf(transelements[transelements.length - 2]
                    .replaceAll("[^\\d]", ""));
            balance = Double.valueOf(transelements[transelements.length - 1]
                    .replaceAll("[^\\d]", ""));
            int index = 3;

            while (index < transelements.length - 2) {
                details = details.concat(transelements[index]).concat(" ").replaceAll("Completed","");
                index++;
            }
            //if the next item in array matches, then show this as subpart
            if (!nexttrans.matches("^[A-Z0-9]{10}\\b.*")) {
                details = details.concat(nexttrans);
            }

            String temptime = transelements[1] + " " + transelements[2];//2019-07-07 21:52:39
            time = df.parse(temptime);
        } catch (Exception e) {

        }
    }

}

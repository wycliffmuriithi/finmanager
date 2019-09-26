package com.finapp.tests.database.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Class name: TotalsbyTranstype
 * Creater: wgicheru
 * Date:9/20/2019
 */
@Data @RequiredArgsConstructor
public class TotalsbyTranstype extends BaseEntity{
    @NonNull
    String user;
    @NonNull
    double total;
    @NonNull
    String transtypeid;
    @NonNull
    int repetitioncount;

    public void addAmount(double amount){
        total += amount;
        repetitioncount++;
    }
}

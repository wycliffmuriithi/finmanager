package com.finapp.tests.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Class name: TotalsbyTranstype
 * Creater: wgicheru
 * Date:9/20/2019
 */
@Data @RequiredArgsConstructor @Entity @Table(name="tbltotalsbytranstype") @NoArgsConstructor
public class TotalsbyTranstype extends BaseEntity{

    @NonNull
    String user;
    @NonNull
    double total;
    @NonNull
    long transtypeid;
    @NonNull
    int repetitioncount;

    public void addAmount(double amount){
        total += amount;
        repetitioncount++;
    }
}

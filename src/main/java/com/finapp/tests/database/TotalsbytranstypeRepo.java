package com.finapp.tests.database;

import com.finapp.tests.database.entities.TotalsbyTranstype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Class name: TotalsbytranstypeRepo
 * Creater: wgicheru
 * Date:9/20/2019
 */
public interface TotalsbytranstypeRepo extends JpaRepository<TotalsbyTranstype,Long> {
    List<TotalsbyTranstype> findByUserAndTranstypeid(String user, long transtypeid);
}

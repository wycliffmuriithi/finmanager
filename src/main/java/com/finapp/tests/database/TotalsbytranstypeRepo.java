package com.finapp.tests.database;

import com.finapp.tests.database.entities.TotalsbyTranstype;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Class name: TotalsbytranstypeRepo
 * Creater: wgicheru
 * Date:9/20/2019
 */
public interface TotalsbytranstypeRepo extends MongoRepository<TotalsbyTranstype,String> {
    List<TotalsbyTranstype> findByUserAndTranstypeid(String user, String transtypeid);
}

package com.finapp.tests.database.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Class name: MpesatransTypes
 * Creater: wgicheru
 * Date:9/17/2019
 */
@Data
@RequiredArgsConstructor
public class MpesatransTypes extends BaseEntity  {
    @NonNull
    String transclass;
    @NonNull
    String transtype;
}

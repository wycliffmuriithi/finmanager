package com.finapp.tests.wrappers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Class name: ResponseObject
 * Creater: wgicheru
 * Date:7/25/2019
 */
@Data
@AllArgsConstructor
public class ResponseObject {
    String status;
    String description;
    List data;
}

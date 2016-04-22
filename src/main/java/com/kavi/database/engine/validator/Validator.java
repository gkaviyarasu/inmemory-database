package com.kavi.database.engine.validator;

import com.kavi.database.engine.metadata.Table;

import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public interface Validator {
    Map<String, Comparable> validate(Map<String, String> row, Table table) throws Exception;
}

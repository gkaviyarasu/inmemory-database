package com.kavi.database.importer.parser;


import java.io.IOException;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public interface DataParser {
    Map<String, String> readRow() throws IOException;

    String getTableName() throws IOException;
}

package com.kavi.database.engine.datastore;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public interface DataStore {
    /**
     * Should return row number
     *
     * @return
     */
    int insert(Map<String, Comparable> row);

    /**
     * Reads the row for the given index
     *
     * @param rowNumber
     * @return
     */
    Map<String, Comparable> readRow(int rowNumber);

    /**
     * Return row iterator
     */
    Iterator<Map<String, Comparable>> getRowIterator();
}

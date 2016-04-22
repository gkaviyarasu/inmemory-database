package com.kavi.database.engine.datastore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class InMemoryDataStore implements DataStore {

    private List<Map<String, Comparable>> rows = new ArrayList<>();

    @Override
    public int insert(Map<String, Comparable> row) {
        rows.add(row);
        return rows.size() - 1;
    }

    @Override
    public Map<String, Comparable> readRow(int rowNumber) {
        return rows.get(rowNumber);
    }

    @Override
    public Iterator<Map<String, Comparable>> getRowIterator() {
        return rows.iterator();
    }
}

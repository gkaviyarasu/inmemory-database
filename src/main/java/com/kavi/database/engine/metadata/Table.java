package com.kavi.database.engine.metadata;

import com.kavi.database.engine.datastore.DataStore;
import com.kavi.database.engine.indexer.Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Table {

    private String name;
    private Map<String, Column> columns = new HashMap<>();
    private Map<String, Tree> indexes = new HashMap<>();
    private Schema schema;
    private DataStore dataStore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public Map<String, Tree> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<String, Tree> indexes) {
        this.indexes = indexes;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        schema.addTable(this);
        this.schema = schema;
    }

    public void addColumn(Column column) {
        this.columns.put(column.getName(), column);
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }
}

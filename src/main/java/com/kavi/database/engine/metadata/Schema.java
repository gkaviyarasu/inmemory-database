package com.kavi.database.engine.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Schema {
    private String name;
    private Map<String, Table> tables = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void addTable(Table table) {
        tables.put(table.getName(), table);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
}

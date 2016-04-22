package com.kavi.database.engine.metadata;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Column {
    private String name;
    private Class type;
    private int size;
    private boolean nullable;
    private Table table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        table.addColumn(this);
        this.table = table;
    }
}

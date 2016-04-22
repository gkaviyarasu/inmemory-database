package com.kavi.database.engine.validator;

import com.kavi.database.engine.metadata.Column;
import com.kavi.database.engine.metadata.Table;
import com.kavi.database.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class ValidatorImpl implements Validator {
    @Override
    public Map<String, Comparable> validate(Map<String, String> row, Table table) throws Exception {
        Map<String, Comparable> tableRow = new HashMap<>();
        for (Map.Entry<String, Column> tableColumn : table.getColumns().entrySet()) {
            Column column = tableColumn.getValue();
            String value = row.get(column.getName());
            if (!column.isNullable() && (value == null || value.trim().isEmpty())) {
                throw new Exception("Invalid data");
            }
            //TODO - need to perform the size check.
            tableRow.put(column.getName(), Util.convertToType(column.getType(), value));

        }
        return tableRow;
    }
}

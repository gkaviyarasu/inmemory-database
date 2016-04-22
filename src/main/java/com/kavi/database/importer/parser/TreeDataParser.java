package com.kavi.database.importer.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class TreeDataParser implements DataParser {

    private final BufferedReader reader;
    private final String tableName;
    private String lastLine = null;

    public TreeDataParser(final Reader reader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.tableName = readLine();
        if (this.tableName == null || this.tableName.charAt(0) == ' ') {
            throw new IOException("Unable to Read table name");
        }
    }

    @Override
    public Map<String, String> readRow() throws IOException {

        Map<String, String> row = new HashMap<>();
        String key = lastLine == null ? readLine() : lastLine;
        lastLine = null;
        while (true) {
            if (key == null || key.trim().isEmpty()) {
                break;
            }
            if (key.startsWith(" ") && !key.startsWith("  ")) {
                key = key.trim();
                if (row.containsKey(key)) {
                    lastLine = " " + key;
                    break;
                } else {
                    String value = readLine();
                    if (value != null && value.startsWith("  ")) {
                        value = value.trim();
                    } else {
                        value = null;
                    }
                    row.put(key, value);
                }
            } else {
                throw new IOException("Invalid file format");
            }
            key = readLine();
        }
        return row;
    }

    @Override
    public String getTableName() throws IOException {
        return this.tableName;
    }

    private String readLine() throws IOException {
        return reader.readLine();
    }
}

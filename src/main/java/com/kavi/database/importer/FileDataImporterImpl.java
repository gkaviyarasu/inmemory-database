package com.kavi.database.importer;

import com.kavi.database.engine.indexer.Tree;
import com.kavi.database.engine.metadata.Column;
import com.kavi.database.engine.metadata.Schema;
import com.kavi.database.engine.metadata.Table;
import com.kavi.database.engine.validator.Validator;
import com.kavi.database.importer.parser.DataParser;
import com.kavi.database.importer.parser.DataParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class FileDataImporterImpl implements DataImporter {

    private final Reader reader;
    private final Schema schema;
    private final DataParser dataParser;
    private final Validator validator;

    public FileDataImporterImpl(File file, String type, Schema schema, Validator validator) throws IOException {
        this.validator = validator;
        this.reader = new FileReader(file);
        this.schema = schema;
        DataParserFactory.DataParserBuilder builder = DataParserFactory.getParser(type);
        if (builder == null) {
            throw new IOException("Couldn't find suitable parser for the type " + type);
        }
        dataParser = builder.setReader(reader).build();
    }


    @Override
    public void importData() throws Exception {
        Table table = schema.getTable(dataParser.getTableName());
        if (table == null) {
            throw new IOException("Unknown Entity");
        }
        while (true) {
            Map<String, String> row = dataParser.readRow();
            if (row.isEmpty()) {
                break;
            }
            Map<String, Comparable> tableRow = validator.validate(row, table);
            int rowIndex = table.getDataStore().insert(tableRow);

            for (Map.Entry<String, Tree> dbIndexes : table.getIndexes().entrySet()) {
                Column column = table.getColumns().get(dbIndexes.getKey());
                Comparable indexedValue = tableRow.get(dbIndexes.getKey());
                dbIndexes.getValue().insert(indexedValue, rowIndex);
            }
        }
    }
}

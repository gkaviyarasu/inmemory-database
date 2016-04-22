package com.kavi.database.importer.parser;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class DataParserFactory {
    public static DataParserBuilder getParser(String type) {
        switch (type) {
            case "tree":
                return new DataParserBuilder() {

                    private Reader reader;

                    @Override
                    public DataParserBuilder setReader(Reader reader) {
                        this.reader = reader;
                        return this;
                    }

                    @Override
                    public DataParser build() throws IOException {
                        return new TreeDataParser(reader);
                    }
                };
        }
        return null;
    }

    public interface DataParserBuilder {
        DataParserBuilder setReader(Reader reader);

        DataParser build() throws IOException;
    }
}

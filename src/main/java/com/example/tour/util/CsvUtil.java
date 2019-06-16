package com.example.tour.util;

import com.example.tour.domain.tour.TourInformation;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CsvUtil {
    private CsvUtil() {
        throw new IllegalStateException("CsvUtil is utility class");
    }

    public static List<TourInformation> loadObjectList(File file) throws IOException {
        CsvSchema schema = CsvSchema.builder()
                                    .addColumn("no", CsvSchema.ColumnType.NUMBER)
                                    .addColumn("name")
                                    .addColumn("theme")
                                    .addColumn("serviceRegionName")
                                    .addColumn("intro")
                                    .addColumn("description")
                                    .build()
                                    .withSkipFirstDataRow(true);

        CsvMapper mapper = new CsvMapper();
        MappingIterator<TourInformation> iterator = mapper.readerFor(TourInformation.class)
                                                          .with(schema)
                                                          .readValues(file);
        return iterator.readAll();
    }
}

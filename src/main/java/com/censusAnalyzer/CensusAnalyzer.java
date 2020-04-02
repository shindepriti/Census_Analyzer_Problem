package com.censusAnalyzer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyzer {

    public int loadCensusData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCsvPojo>  csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCsvPojo.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCsvPojo> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCsvPojo> censusCsvIterator = csvToBean.iterator();
            int numberOfEntries = 0;
            while (censusCsvIterator.hasNext()) {
                numberOfEntries++;
                IndiaCensusCsvPojo  censusData = censusCsvIterator.next();
            }
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.getMessage());
        }
    }
}

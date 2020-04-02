package com.censusAnalyzer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyzer {

    public int loadCensusData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCsvPojo>  csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCsvPojo.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCsvPojo> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCsvPojo> censusCsvIterator = csvToBean.iterator();
            Iterable<IndiaCensusCsvPojo> csvIterable=() -> censusCsvIterator;
            int numberOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numberOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaStateCodePojo> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCodePojo.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCodePojo> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCodePojo> censusCsvIterator = csvToBean.iterator();
            Iterable<IndiaStateCodePojo> csvIterable = () -> censusCsvIterator;
            int numberOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numberOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }
}

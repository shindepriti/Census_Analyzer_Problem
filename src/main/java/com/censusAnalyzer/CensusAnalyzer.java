package com.censusAnalyzer.Service;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.censusAnalyzer.IndiaCensusCsvPojo;
import com.censusAnalyzer.IndiaStateCodePojo;
import com.censusAnalyzer.OpenCsvBuilder;

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
            Iterator<IndiaCensusCsvPojo> censusCsvIterator = new OpenCsvBuilder().getCsvFileIterator(reader,IndiaCensusCsvPojo.class);
            return getCount(censusCsvIterator);
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaStateCodePojo> censusCsvIterator = new OpenCsvBuilder().getCsvFileIterator(reader,IndiaStateCodePojo.class);
            return getCount(censusCsvIterator);
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numberOfEnteries;
    }


}

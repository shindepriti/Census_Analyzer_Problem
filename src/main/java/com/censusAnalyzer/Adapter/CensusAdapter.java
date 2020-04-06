package com.censusAnalyzer.Adapter;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DAO.CensusDao;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaStateCodeCsv;
import com.censusAnalyzer.DTO.UsCensusCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.censusAnalyzer.Service.CensusAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    Map<String, CensusDao> censusMap;

    public abstract Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CensusAnalyzerException;

    public <E> Map<String, CensusDao> loadCensusData(Class<E> censusCsvClass, String... csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
            IcsvBuilder icsvBuilder = CsvBuilderFactory.CsvBuilder();
            Iterator<E> censusCsvIterator = icsvBuilder.getCsvFileIterator(reader, censusCsvClass);
            Iterable<E> csvIterable = () -> censusCsvIterator;
            if (censusCsvClass.getName().equals("com.censusAnalyzer.DTO.IndiaCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCsv.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new CensusDao(censusCsv)));
            } else if (censusCsvClass.getName().equals("com.censusAnalyzer.DTO.UsCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(UsCensusCsv.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new CensusDao(censusCsv)));
            }
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        } catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM, e.getMessage());
        }
    }
}
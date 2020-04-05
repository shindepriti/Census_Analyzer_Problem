package com.censusAnalyzer.Adapter;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DAO.CensusDao;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaStateCodeCsv;
import com.censusAnalyzer.DTO.UsCensusCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAdapter {

    Map<String, CensusDao> censusMap;
    List<CensusDao> censusDaoList;

    public <E> Map<String, CensusDao> loadCensusData(Class<E> censusCsvClass,String... csvFilePath ) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
            IcsvBuilder icsvBuilder = CsvBuilderFactory.CsvBuilder();
            Iterator<E> censusCsvIterator = icsvBuilder.getCsvFileIterator(reader, censusCsvClass);
            Iterable<E> csvIterable = () -> censusCsvIterator;
            if (censusCsvClass.getName().equals("com.censusAnalyzer.DTO.IndiaCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCsv.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new CensusDao(censusCsv)));
            }
            else if (censusCsvClass.getName().equals("com.censusAnalyzer.DTO.UsCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(UsCensusCsv.class::cast)
                        .forEach(censusCsv -> censusMap.put(censusCsv.state, new CensusDao(censusCsv)));
            }
            if (csvFilePath.length==1)
                return censusMap;
            this.loadStateCodeData(censusMap,csvFilePath[1]);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }


    public int loadStateCodeData( Map<String, CensusDao> censusMap,String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            IcsvBuilder csvBuilder = CsvBuilderFactory.CsvBuilder();
            Iterator<IndiaStateCodeCsv> stateCodeCSVIterator = csvBuilder.getCsvFileIterator(reader, IndiaStateCodeCsv.class);
            Iterable<IndiaStateCodeCsv> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        } catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM, e.getMessage());
        }
    }

}

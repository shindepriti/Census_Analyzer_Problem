package com.censusAnalyzer.Service;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DAO.CensusDao;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaStateCodeCsv;
import com.censusAnalyzer.DTO.UsCensusCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyzer {
    Map<String, CensusDao> censusMap;
    List<CensusDao> censusDaoList;

    public CensusAnalyzer()
    {
        censusMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws  CensusAnalyzerException {
       return this.loadCensusData(csvFilePath,IndiaCensusCsv.class);
    }

    private <E> int loadCensusData(String csvFilePath,Class<E> censusCsvClass) throws  CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
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
            censusDaoList = censusMap.values().stream().collect(Collectors.toList());
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.getMessage());
        }catch (RuntimeException e) {
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.getMessage());
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyzerException {
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

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException {
        if(censusDaoList.size()==0 || censusDaoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Data");
        Comparator<CensusDao> censusCsvComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCsvComparator);
        String sortedCensusJson = new Gson().toJson(censusDaoList);
        return sortedCensusJson;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(censusDaoList.size()==0 || censusDaoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<CensusDao> censusDaoComparator = Comparator.comparing(census -> census.population);
        this.sort(censusDaoComparator);
        String sortedCensusJson = new Gson().toJson(censusDaoList);
        return sortedCensusJson;
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(censusDaoList.size()==0 || censusDaoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<CensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.totalDensity);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(censusDaoList);
        return sortedCensusJson;
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(censusDaoList.size()==0 || censusDaoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<CensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.totalArea);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(censusDaoList);
        return sortedCensusJson;
    }

    public String getUsStateSortedCensusData(String csvFilePath) throws CensusAnalyzerException {
        if(censusDaoList.size()==0 || censusDaoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<CensusDao> censusDaoComparator = Comparator.comparing(census -> census.state);
        this.sort(censusDaoComparator);
        String sortedCensusJson = new Gson().toJson(censusDaoList);
        return sortedCensusJson;
    }

    private void sort(Comparator<CensusDao> indiaCensusCsvComparator) {
        for (int i = 0; i < censusDaoList.size()-1; i++) {
            for (int j = 0; j < censusDaoList.size() - i - 1; j++) {
                CensusDao census1 = censusDaoList.get(j);
                CensusDao census2 = censusDaoList.get(j + 1);
                if (indiaCensusCsvComparator.compare(census1, census2) > 0) {
                    censusDaoList.set(j, census2);
                    censusDaoList.set(j + 1, census1);
                }
            }
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyzerException {
        return this.loadCensusData(csvFilePath,UsCensusCsv.class);
    }
}

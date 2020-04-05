package com.censusAnalyzer.Service;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DAO.IndiaCensusDao;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaStateCodeCsv;
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
    Map<String, IndiaCensusDao> censusMap;
    List<IndiaCensusDao> indiaCensusDtoList;

    public CensusAnalyzer() {
        censusMap = new HashMap<>();
    }

    public int loadCensusData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            IcsvBuilder csvBuilder = CsvBuilderFactory.CsvBuilder();
            Iterator<IndiaCensusCsv> censusCsvIterator = csvBuilder.getCsvFileIterator(reader,IndiaCensusCsv.class);
            while (censusCsvIterator.hasNext()) {
                IndiaCensusCsv indiaCensusCsv = censusCsvIterator.next();
                censusMap.put(indiaCensusCsv.state,new IndiaCensusDao(indiaCensusCsv));
            }
            indiaCensusDtoList = censusMap.values().stream().collect(Collectors.toList());
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

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numberOfEnteries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException {
        if(indiaCensusDtoList.size()==0 || indiaCensusDtoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Data");
        Comparator<IndiaCensusDao> indiaCensusCsvComparator = Comparator.comparing(census -> census.state);
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDtoList);
        return sortedCensusJson;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(indiaCensusDtoList.size()==0 || indiaCensusDtoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<IndiaCensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.population);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDtoList);
        return sortedCensusJson;
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(indiaCensusDtoList.size()==0 || indiaCensusDtoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<IndiaCensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDtoList);
        return sortedCensusJson;
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
        if(indiaCensusDtoList.size()==0 || indiaCensusDtoList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        Comparator<IndiaCensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDtoList);
        return sortedCensusJson;
    }

    private void sort(Comparator<IndiaCensusDao> indiaCensusCsvComparator) {
        for (int i = 0; i < indiaCensusDtoList.size()-1; i++){
            for (int j=0; j < indiaCensusDtoList.size()-i-1; j++){
                IndiaCensusDao census1 = indiaCensusDtoList.get(j);
                IndiaCensusDao census2 = indiaCensusDtoList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0){
                    indiaCensusDtoList.set(j,census2);
                    indiaCensusDtoList.set(j+1,census1);
                }
            }
        }
    }



}

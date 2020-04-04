package com.censusAnalyzer.Service;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaCensusDto;
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
    Map<String, IndiaCensusDto> censusMap;
    List<IndiaCensusDto> indiaCensusDtoList;

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
                censusMap.put(indiaCensusCsv.state,new IndiaCensusDto(indiaCensusCsv));
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
            while (stateCodeCSVIterator.hasNext()) {
                IndiaStateCodeCsv indiaStateCodeCSV = stateCodeCSVIterator.next();
                IndiaCensusDto indiaCensusDTO = censusMap.get(indiaStateCodeCSV.state);
                if (indiaCensusDTO==null)
                    continue;
            }
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
        Comparator<IndiaCensusDto> indiaCensusCsvComparator = Comparator.comparing(census -> census.state);
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDtoList);
        return sortedCensusJson;
    }

    private void sort(Comparator<IndiaCensusDto> indiaCensusCsvComparator) {
        for (int i = 0; i < indiaCensusDtoList.size()-1; i++){
            for (int j=0; j < indiaCensusDtoList.size()-i-1; j++){
                IndiaCensusDto census1 = indiaCensusDtoList.get(j);
                IndiaCensusDto census2 = indiaCensusDtoList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0){
                    indiaCensusDtoList.set(j,census2);
                    indiaCensusDtoList.set(j+1,census1);
                }
            }
        }
    }



}

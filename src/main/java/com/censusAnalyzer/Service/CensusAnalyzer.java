package com.censusAnalyzer.Service;

import com.censusAnalyzer.Builder.CsvBuilderFactory;
import com.censusAnalyzer.Builder.IcsvBuilder;
import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.IndiaStateCodeCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyzer {
    List<IndiaCensusCsv> censusCsvList = null;
    List<IndiaStateCodeCsv> stateCodeCsv = null;
    public int loadCensusData(String csvFilePath) throws CensusAnalyzerException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            IcsvBuilder csvBuilder = CsvBuilderFactory.CsvBuilder();
             censusCsvList = csvBuilder.getCsvFileList(reader,IndiaCensusCsv.class);
            return censusCsvList.size();
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
           List<IndiaStateCodeCsv> stateCodeList = csvBuilder.getCsvFileList(reader,IndiaStateCodeCsv.class);
            return stateCodeList.size();
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
        if(censusCsvList.size()==0 || censusCsvList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Data");
        Comparator<IndiaCensusCsv> indiaCensusCsvComparator = Comparator.comparing(census -> census.state);
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new Gson().toJson(censusCsvList);
        return sortedCensusJson;
    }
    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyzerException {
        if(censusCsvList.size()==0 ||  censusCsvList==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Data");
        Comparator<IndiaStateCodeCsv> indiaStateCodeCsvComparator = Comparator.comparing(census -> census.stateCode);
        this.sorting(indiaStateCodeCsvComparator);
        String sortedCensusJson = new Gson().toJson(censusCsvList);
        return sortedCensusJson;
    }

    private void sort(Comparator<IndiaCensusCsv> indiaCensusCsvComparator) {
        for (int i = 0; i < censusCsvList.size()-1; i++){
            for (int j=0; j < censusCsvList.size()-i-1; j++){
                IndiaCensusCsv census1 = censusCsvList.get(j);
                IndiaCensusCsv census2 = censusCsvList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0){
                    censusCsvList.set(j,census2);
                    censusCsvList.set(j+1,census1);
                }
            }
        }
    }

    private void sorting(Comparator<IndiaStateCodeCsv> indiaStateCodeCsvComparator) {
        for (int i = 0; i < stateCodeCsv.size()-1; i++){
            for (int j=0; j < stateCodeCsv.size()-i-1; j++){
               IndiaStateCodeCsv census1 = stateCodeCsv.get(j);
               IndiaStateCodeCsv census2 = stateCodeCsv.get(j+1);
                if (indiaStateCodeCsvComparator.compare(census1,census2)>0){
                    stateCodeCsv.set(j, census2);
                    stateCodeCsv.set(j+1,census1);
                }
            }
        }
    }


}

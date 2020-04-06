package com.censusAnalyzer;

import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.UsCensusCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.censusAnalyzer.Service.CensusAnalyzer;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyzerTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.json";
    private static  final String INCORRECT_CSV_DATA_FILE_PATH = "./src/test/resources/IndiaStateCensusWrongData.csv";

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_TYPE = "./src/test/resources/IndiaStateCode.json";
    private static final String INCORRECT_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCodeWrongData.csv";

    private static final String US_CENSUS_CSV_FILE_PATH="./src/test/resources/USCensusData.csv";


    @Test
    public void givenIndiaCensusCsvFile_returnNumberOfRecords() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            int numberOfRecord = censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test public void givenIndiaCensusCsvFile_whenFileIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_whenDelimiterIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_whenCsvHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_returnCorrectRecord() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
           int numberOfRecord =censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_STATE_CODE_FILE_PATH);
           Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenPathIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,WRONG_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,WRONG_STATE_CODE_FILE_TYPE);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenDelimiterIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INCORRECT_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenHeadIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INCORRECT_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }


    @Test
    public void givenIndiaCensusData_whenSortedOnState_shouldReturnResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.STATE);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Andhra Pradesh",censusCsv[0].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIndiaCensusData_whenSortedOnState_shouldReturnSortedData() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.STATE);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("West Bengal",censusCsv[28].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnPopulation_shouldReturnResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.POPULATION);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Sikkim",censusCsv[0].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnDensity_shouldReturnResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.DENSITY);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnArea_shouldReturnResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.INDIA);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.AREA);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Goa",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_shouldReturnCorrectData() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.US);
           int numberOfRecord = censusAnalyzer.loadCensusData(CensusAnalyzer.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_whenSorted_shouldReturnResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.US);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.STATE);
            UsCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,UsCensusCsv[].class);
            Assert.assertEquals("Alabama",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_whenSorted_shouldReturnLastResult() {
        try {
            CensusAnalyzer censusAnalyzer =new CensusAnalyzer(CensusAnalyzer.Country.US);
            censusAnalyzer.loadCensusData(CensusAnalyzer.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getSortedCensusData(CensusAnalyzer.SortingMode.STATE);
            UsCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,UsCensusCsv[].class);
            Assert.assertEquals("Wyoming",censusCsv[50].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }
}

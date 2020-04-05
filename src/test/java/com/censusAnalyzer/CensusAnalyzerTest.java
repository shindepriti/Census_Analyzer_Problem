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
    private CensusAnalyzer censusAnalyzer;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.json";
    private static  final String INCORRECT_CSV_DATA_FILE_PATH = "./src/test/resources/IndiaStateCensusWrongData.csv";

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_TYPE = "./src/test/resources/IndiaStateCode.json";
    private static final String INCORRECT_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCodeWrongData.csv";

    private static final String US_CENSUS_CSV_FILE_PATH="./src/test/resources/USCensusData.csv";

    @Before
    public void init(){
        censusAnalyzer = new CensusAnalyzer();
    }

    @Test
    public void givenIndiaCensusCsvFile_returnNumberOfRecords() {
        try {

            int numberOfRecord = censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test public void givenIndiaCensusCsvFile_whenFileIncorrect_shouldReturnException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_WhenTypeIncorrect_shouldReturnException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_whenDelimiterIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadIndiaCensusData(INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusCsvFile_whenCsvHeaderIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadIndiaCensusData(INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException  e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_returnCorrectRecord() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
           int numberOfRecord = censusAnalyzer.loadStateCodeData(INDIA_STATE_CODE_FILE_PATH);
           Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenPathIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadStateCodeData(WRONG_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenTypeIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadStateCodeData(WRONG_STATE_CODE_FILE_TYPE);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenDelimiterIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadStateCodeData(INCORRECT_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_whenHeadIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadStateCodeData(INCORRECT_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }


    @Test
    public void givenIndiaCensusData_whenSortedOnState_shouldReturnResult() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getStateWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Andhra Pradesh",censusCsv[0].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIndiaCensusData_whenSortedOnState_shouldReturnSortedData() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getStateWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("West Bengal",censusCsv[28].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnPopulation_shouldReturnResult() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getPopulationWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Sikkim",censusCsv[0].state);
        } catch (CensusAnalyzerException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnDensity_shouldReturnResult() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getDensityWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_whenSortedOnArea_shouldReturnResult() {
        try {
            censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getAreaWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,IndiaCensusCsv[].class);
            Assert.assertEquals("Goa",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_shouldReturnCorrectData() {
        try {
           int numberOfRecord = censusAnalyzer.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_whenSorted_shouldReturnResult() {
        try {
            censusAnalyzer.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getUsStateSortedCensusData(US_CENSUS_CSV_FILE_PATH);
            UsCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,UsCensusCsv[].class);
            Assert.assertEquals("Alabama",censusCsv[0].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_whenSorted_shouldReturnLastResult() {
        try {
            censusAnalyzer.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getUsStateSortedCensusData(US_CENSUS_CSV_FILE_PATH);
            UsCensusCsv[] censusCsv = new Gson().fromJson(sortedCensusData,UsCensusCsv[].class);
            Assert.assertEquals("Wyoming",censusCsv[50].state);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }
}

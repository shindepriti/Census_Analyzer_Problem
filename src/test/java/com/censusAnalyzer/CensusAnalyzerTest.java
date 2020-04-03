package com.censusAnalyzer;

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

    @Before
    public void init(){
        censusAnalyzer = new CensusAnalyzer();
    }

    @Test
    public void givenStateCensusCsvFile_returnNumberOfRecords() {
        try {

            int numberOfRecord = censusAnalyzer.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test public void givenStateCensusCsvFile_whenIncorrect_shouldReturnException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_shouldReturnException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_whenDelimiterIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadCensusData(INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_whenCsvHeaderIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadCensusData(INCORRECT_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_returnCorrectRecord() {
        try {
           int numberOfRecord = censusAnalyzer.loadStateCodeData(INDIA_STATE_CODE_FILE_PATH);
           Assert.assertEquals(37,numberOfRecord);
        } catch (CensusAnalyzerException e) {
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
}

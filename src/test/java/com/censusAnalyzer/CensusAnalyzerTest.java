package com.censusAnalyzer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyzerTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.json";
    private  static  final String WRONG_CSV_DATA_FILE_PATH = "./src/test/resources/IndiaStateCensusWrongData.csv";

    CensusAnalyzer censusAnalyzer;
    @Before
    public void init(){
        censusAnalyzer = new CensusAnalyzer();
    }

    @Test
    public void givenStateCensusCsvFile_checkToEnsure_returnNumberOfRecords() {
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
            censusAnalyzer.loadCensusData(WRONG_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_whenCsvHeaderIncorrect_shouldReturnException() {
        try {
            censusAnalyzer.loadCensusData(WRONG_CSV_DATA_FILE_PATH);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }
}

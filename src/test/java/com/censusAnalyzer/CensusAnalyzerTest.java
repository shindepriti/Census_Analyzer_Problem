package com.censusAnalyzer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyzerTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resouresc/IndiaStateCensusData.json";
   
    @Test
    public void givenStateCensusCsvFile_checkToEnsure_returnNumberOfRecords() {
        try {
            CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
            int numberOfRecord = censusAnalyzer.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numberOfRecord);
        } catch (CensusAnalyzerException e) {
            e.printStackTrace();
        }
    }

    @Test public void givenStateCensusCsvFile_whenIncorrect_shouldReturnException() {
        try {
            CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
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
            CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyzerException.class);
            censusAnalyzer.loadCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyzerException e) {
            Assert.assertEquals(CensusAnalyzerException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }

    }
}

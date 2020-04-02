package com.censusAnalyzer;

import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyzerTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    @Test
    public void givenStateCensusCsvFile_checkToEnsure_returnNumberOfRecords() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfRecord = censusAnalyzer.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29,numberOfRecord);
    }
}

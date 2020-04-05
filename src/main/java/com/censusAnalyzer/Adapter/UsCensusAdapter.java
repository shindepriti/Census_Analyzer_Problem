package com.censusAnalyzer.Adapter;

import com.censusAnalyzer.DAO.CensusDao;
import com.censusAnalyzer.DTO.UsCensusCsv;
import com.censusAnalyzer.Exception.CensusAnalyzerException;

import java.util.Map;

public class UsCensusAdapter extends CensusAdapter {
    public Map<String, CensusDao> censusMap;

    @Override
    public Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CensusAnalyzerException {
        censusMap = super.loadCensusData(UsCensusCsv.class,csvFilePath[0]);
        return censusMap;
    }
}

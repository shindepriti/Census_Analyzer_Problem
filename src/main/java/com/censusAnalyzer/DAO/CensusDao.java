package com.censusAnalyzer.DAO;

import com.censusAnalyzer.DTO.IndiaCensusCsv;
import com.censusAnalyzer.DTO.UsCensusCsv;

public class CensusDao {

    public String state;
    public double population;
    public double totalArea;
    public double totalDensity;
    public String  stateCode;
    public double populationDensity;

    public CensusDao(IndiaCensusCsv indiaCensusCsv) {
        state = indiaCensusCsv.state;
        population = indiaCensusCsv.population;
        totalArea = indiaCensusCsv.areaInSqKm;
        totalDensity = indiaCensusCsv.densityPerSqKm;
    }

    public CensusDao(UsCensusCsv usCensusCsv){
        state = usCensusCsv.state;
        stateCode = usCensusCsv.stateId;
        population = usCensusCsv.population;
        totalArea = usCensusCsv.totalArea;
        populationDensity = usCensusCsv.populationDensity;
    }
}

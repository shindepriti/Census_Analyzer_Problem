package com.censusAnalyzer.DAO;

import com.censusAnalyzer.DTO.IndiaCensusCsv;

public class IndiaCensusDao {

    public String state;
    public long population;
    public long areaInSqKm;
    public long densityPerSqKm;
    public String  stateCode;

    public IndiaCensusDao(IndiaCensusCsv indiaCensusCsv) {
        state = indiaCensusCsv.state;
        population = indiaCensusCsv.population;
        areaInSqKm = indiaCensusCsv.areaInSqKm;
        densityPerSqKm = indiaCensusCsv.densityPerSqKm;
    }
}

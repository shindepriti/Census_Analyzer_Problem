package com.censusAnalyzer.DTO;

public class IndiaCensusDto {

    public String state;
    public long population;
    public long areaInSqKm;
    public long densityPerSqKm;
    public String  stateCode;

    public IndiaCensusDto(IndiaCensusCsv indiaCensusCsv) {
        state = indiaCensusCsv.state;
        population = indiaCensusCsv.population;
        areaInSqKm = indiaCensusCsv.areaInSqKm;
        densityPerSqKm = indiaCensusCsv.densityPerSqKm;
    }
}

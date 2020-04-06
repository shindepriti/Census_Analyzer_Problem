package com.censusAnalyzer.Service;

import com.censusAnalyzer.Adapter.CensusAdapterFactory;
import com.censusAnalyzer.DAO.CensusDao;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyzer {

    public enum Country {INDIA,US}
    public enum SortingMode{STATE,POPULATION,DENSITY,AREA}
    Map<String, CensusDao> censusMap;
    List<CensusDao> censusDaoList;
    private Country country;

    public CensusAnalyzer(Country country)
    {
        this.country=country;
    }

    public int loadCensusData(Country country,String... csvFilePath) throws  CensusAnalyzerException {
        censusMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    public String getSortedCensusData(SortingMode mode) throws CensusAnalyzerException {
        if(censusMap.size()==0 || censusMap==null)
            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Data");
        ArrayList censusDTO = censusMap.values().stream()
                .sorted(CensusDao.getSortComparator(mode))
                .map(censusDao -> censusDao.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }

//    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
//        if(censusDaoList.size()==0 || censusDaoList==null)
//            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
//        Comparator<CensusDao> censusDaoComparator = Comparator.comparing(census -> census.population);
//        this.sort(censusDaoComparator);
//        String sortedCensusJson = new Gson().toJson(censusDaoList);
//        return sortedCensusJson;
//    }
//
//    public String getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
//        if(censusDaoList.size()==0 || censusDaoList==null)
//            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
//        Comparator<CensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.totalDensity);
//        this.sort(indiaCensusDaoComparator);
//        String sortedCensusJson = new Gson().toJson(censusDaoList);
//        return sortedCensusJson;
//    }
//
//    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyzerException{
//        if(censusDaoList.size()==0 || censusDaoList==null)
//            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
//        Comparator<CensusDao> indiaCensusDaoComparator = Comparator.comparing(census -> census.totalArea);
//        this.sort(indiaCensusDaoComparator);
//        String sortedCensusJson = new Gson().toJson(censusDaoList);
//        return sortedCensusJson;
//    }
//
//    public String getUsStateSortedCensusData(String csvFilePath) throws CensusAnalyzerException {
//        if(censusDaoList.size()==0 || censusDaoList==null)
//            throw new CensusAnalyzerException(CensusAnalyzerException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
//        Comparator<CensusDao> censusDaoComparator = Comparator.comparing(census -> census.state);
//        this.sort(censusDaoComparator);
//        String sortedCensusJson = new Gson().toJson(censusDaoList);
//        return sortedCensusJson;
//    }
//
//    private void sort(Comparator<CensusDao> indiaCensusCsvComparator) {
//        for (int i = 0; i < censusDaoList.size()-1; i++) {
//            for (int j = 0; j < censusDaoList.size() - i - 1; j++) {
//                CensusDao census1 = censusDaoList.get(j);
//                CensusDao census2 = censusDaoList.get(j + 1);
//                if (indiaCensusCsvComparator.compare(census1, census2) > 0) {
//                    censusDaoList.set(j, census2);
//                    censusDaoList.set(j + 1, census1);
//                }
//            }
//        }
//    }

}

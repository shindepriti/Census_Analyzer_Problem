package com.censusAnalyzer.Exception;

public class CensusAnalyzerException extends Throwable {

    public enum ExceptionType {
        CSV_FILE_PROBLEM,CSV_TEMPLATE_PROBLEM,NO_CENSUS_DATA,INVALID_COUNTRY
    }

    public static ExceptionType type;

    public CensusAnalyzerException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
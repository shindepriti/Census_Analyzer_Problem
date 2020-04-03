package com.censusAnalyzer.Exception;

public class CensusAnalyzerException extends Throwable {

    public enum ExceptionType {
        CSV_FILE_PROBLEM,CSV_TEMPLATE_PROBLEM
    }

    public static ExceptionType type;

    public CensusAnalyzerException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
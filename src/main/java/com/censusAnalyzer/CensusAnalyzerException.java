package com.censusAnalyzer;

public class CensusAnalyzerException extends Throwable {
    enum ExceptionType {
        CSV_FILE_PROBLEM,CSV_TEMPLATE_PROBLEM
    }

    ExceptionType type;

    public CensusAnalyzerException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
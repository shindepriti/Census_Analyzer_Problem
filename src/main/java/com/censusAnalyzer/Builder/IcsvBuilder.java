package com.censusAnalyzer.Builder;
import com.censusAnalyzer.Exception.CensusAnalyzerException;
import com.censusAnalyzer.Exception.CsvBuilderException;

import java.io.Reader;
import java.util.Iterator;

public interface IcsvBuilder {
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CsvBuilderException, CensusAnalyzerException;
}

package com.censusAnalyzer.Builder;

import java.io.Reader;
import java.util.Iterator;

public interface IcsvBuilder {
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass);
}

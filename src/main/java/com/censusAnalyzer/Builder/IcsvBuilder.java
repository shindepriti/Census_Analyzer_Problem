package com.censusAnalyzer.Builder;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;


public interface IcsvBuilder<E> {
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass);
    public List<E> getCsvFileList(Reader reader, Class csvClass);
}

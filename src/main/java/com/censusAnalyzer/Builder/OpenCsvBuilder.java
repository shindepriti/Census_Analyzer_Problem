package com.censusAnalyzer.Builder;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;

public class OpenCsvBuilder implements IcsvBuilder {
    public  Iterator<IcsvBuilder> getCsvFileIterator(Reader reader, Class csvClass){
        CsvToBeanBuilder<IcsvBuilder> csvToBeanBuilder =  new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvToBeanBuilder.withSeparator(',');
        CsvToBean<IcsvBuilder> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }
}

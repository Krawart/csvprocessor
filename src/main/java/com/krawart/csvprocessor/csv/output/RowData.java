package com.krawart.csvprocessor.csv.output;

import com.krawart.csvprocessor.processors.CsvBeanProcessor;

/**
 * Every Output DataRow class must implement this interface to be used by default implementation of
 * {@link CsvBeanProcessor}
 */
public interface RowData<T extends RowData<T>> extends Comparable<T> {
}

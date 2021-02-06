package com.krawart.csvprocessor.csv.rows;

import com.krawart.csvprocessor.processing.CsvBeanProcessor;

/**
 * Every DataRow class must implement this interface to be used by default implementation of
 * {@link CsvBeanProcessor}
 */
public interface RowData<T extends RowData<T>> extends Comparable<T> {
}
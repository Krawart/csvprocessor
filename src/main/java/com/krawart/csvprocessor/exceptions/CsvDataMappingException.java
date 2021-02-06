package com.krawart.csvprocessor.exceptions;

public class CsvDataMappingException extends RuntimeException{
  public CsvDataMappingException() {
    super("Internal app error. There were some problem with csv mapping.");
  }
}

package com.krawart.csvprocessor.processing;

import com.krawart.csvprocessor.beans.CsvBean;
import com.krawart.csvprocessor.csv.rows.DataRow;
import com.krawart.csvprocessor.enums.FileType;
import com.krawart.csvprocessor.exceptions.CsvDataMappingException;
import com.krawart.csvprocessor.exceptions.InputFileNotFoundException;
import com.krawart.csvprocessor.exceptions.UnsupportedPropertyInApplicationPropertiesException;
import com.krawart.csvprocessor.exceptions.WriteOperationException;
import com.krawart.csvprocessor.utils.ApplicationProperties;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.krawart.csvprocessor.Application.INPUT_DIRECTORY_PATH;
import static com.krawart.csvprocessor.Application.OUTPUT_DIRECTORY_PATH;

public abstract class CsvBeanProcessor<T extends CsvBean<T>, R extends DataRow> {
  private static final String DEFAULT_FILENAME = "data.csv";
  protected final Logger log = Logger.getLogger(this.getClass().getName());
  protected Class<T> beanType;

  protected CsvBeanProcessor(Class<T> beanType) {
    this.beanType = beanType;
  }

  /**
   * Method handles export to required format
   *
   * @param properties ApplicationProperties specified on startup from args
   * @return Exit status of export operations
   */
  public int analyzeAndExport(ApplicationProperties properties) {
    String filename = properties.getFilename();

    List<T> beans = readAll(filename);

    List<R> analyzedBeans = analyzeBeans(beans);

    int status;
    switch (properties.getFileType()) {
      case CSV:
        status = exportToCsv(analyzedBeans, filename);
        break;
      case HTML:
        status = exportToHtml(analyzedBeans, filename);
        break;
      case XLS:
        status = exportToXls(analyzedBeans, filename);
        break;
      case XLSX:
        status = exportToXlsx(analyzedBeans, filename);
        break;
      default:
        throw new UnsupportedPropertyInApplicationPropertiesException(
          "Application properties contents unsupported value " + properties.getFileType().getType() +
            " with value " + properties.getFileType());
    }
    return status;
  }

  /**
   * Read all csv rows to pojo bean
   *
   * @param filenameFromProperties filename specified on startup of program
   * @return List of beans - raw data from csv
   */
  private List<T> readAll(String filenameFromProperties) {
    String filename = getFilename(filenameFromProperties);

    log.info("Reading file from " + INPUT_DIRECTORY_PATH + " directory.");

    FileReader fileReader;
    try {
      fileReader = new FileReader(INPUT_DIRECTORY_PATH + filename);
      CsvToBean<T> csvToBeanParser = new CsvToBeanBuilder<T>(fileReader).withType(beanType).build();

      return csvToBeanParser.parse();
    } catch (FileNotFoundException e) {
      throw new InputFileNotFoundException();
    }
  }

  protected abstract List<R> analyzeBeans(List<T> beans);

  /**
   * This method will be called when ApplicationProperties contains ".csv" fileType property. This is also default
   * option if no fileType is specified
   *
   * @param analyzedBeans Processed data to DataRow format
   * @return Exit Status code;
   */
  protected int exportToCsv(List<R> analyzedBeans, String filename) {
    String uri = OUTPUT_DIRECTORY_PATH + getOutputFilename(filename, FileType.CSV);
    try {
      Writer writer = new FileWriter(uri);
      StatefulBeanToCsv<R> beanToCsv = new StatefulBeanToCsvBuilder<R>(writer).build();
      beanToCsv.write(analyzedBeans);
      writer.close();
      return 0;
    } catch (IOException e) {
      throw new WriteOperationException("Cannot write to file " + uri);
    } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
      throw new CsvDataMappingException();
    }
  }

  /**
   * This method will be called when ApplicationProperties contains ".html" fileType property
   *
   * @param analyzedBeans Processed data to DataRow format
   * @return Exit Status code;
   */
  protected int exportToHtml(List<R> analyzedBeans, String filename) {
    return 0; // TODO = implementation is missing
  }

  /**
   * This method will be called when ApplicationProperties contains ".xls" fileType property
   *
   * @param analyzedBeans Processed data to DataRow format
   * @return Exit Status code;
   */
  protected int exportToXls(List<R> analyzedBeans, String filename) {
    return 0; // TODO = implementation is missing
  }

  /**
   * This method will be called when ApplicationProperties contains ".xlsx" fileType property
   *
   * @param analyzedBeans Processed data to DataRow format
   * @return Exit Status code;
   */
  protected int exportToXlsx(List<R> analyzedBeans, String filename) {
    return 0; // TODO = implementation is missing
  }


  private String getFilename(String filename) {
    if (Objects.isNull(filename) || filename.isBlank()) {
      return DEFAULT_FILENAME;
    }
    return filename;
  }

  private String getOutputFilename(String filename, FileType fileType) {
    Instant now = Instant.now();

    /*
     * Filename has .csv suffix and we want to replace it with our new file suffix
     * */
    String name = filename.substring(0, filename.length() - 4);

    return now + name + fileType.getType();
  }
}
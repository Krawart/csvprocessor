package com.krawart.csvprocessor;

import com.krawart.csvprocessor.beans.CsvBean;
import com.krawart.csvprocessor.processing.CsvBeanProcessor;
import com.krawart.csvprocessor.processing.impl.MarketShareCsvProcessor;
import com.krawart.csvprocessor.csv.rows.DataRow;
import com.krawart.csvprocessor.utils.ApplicationProperties;

import java.io.File;
import java.util.logging.Logger;

public class Application {
  /*
   * APPLICATION SETTINGS
   * */
  private static final String ABSOLUTE_PATH = new File("").getAbsolutePath();
  public static final String INPUT_DIRECTORY_PATH = ABSOLUTE_PATH + "/input/";
  public static final String OUTPUT_DIRECTORY_PATH = ABSOLUTE_PATH + "/output/";
  private static final Logger log = Logger.getLogger(Application.class.getName());

  public static void main(String[] args) {
    System.out.println(INPUT_DIRECTORY_PATH);
    ApplicationProperties properties = ApplicationProperties.ofArgs(args);

    CsvBeanProcessor<? extends CsvBean<?>, ? extends DataRow> converter;
    switch (properties.getDataType()) {
      case 0:
        converter = new MarketShareCsvProcessor();
        break;
      case 1:
        /*
         * More reader implementations can be added here.
         * */
      default:
        log.warning("Data schema for this dataType option is not implemented yet.");
        System.exit(0);
        return;
    }

    System.exit(converter.analyzeAndExport(properties));
  }
}

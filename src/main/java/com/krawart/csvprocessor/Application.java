package com.krawart.csvprocessor;

import com.krawart.csvprocessor.enums.Quarter;
import com.krawart.csvprocessor.processing.CsvBeanProcessor;
import com.krawart.csvprocessor.processing.impl.MarketShareCsvProcessor;
import com.krawart.csvprocessor.utils.ApplicationProperties;

import java.util.logging.Logger;

public class Application {
  private static final Logger log = Logger.getLogger(Application.class.getName());

  public static void main(String[] args) {
    ApplicationProperties properties = ApplicationProperties.ofArgs(args);

    CsvBeanProcessor<?, ?> converter;
    switch (properties.getDataType()) {
      case 0:
        converter = new MarketShareCsvProcessor("Czech Republic", 2010, Quarter.Q4);
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

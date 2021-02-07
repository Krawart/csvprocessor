package com.krawart.csvprocessor.utils;

import com.krawart.csvprocessor.enums.FileType;
import com.krawart.csvprocessor.exceptions.InputFileNotFoundException;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.krawart.csvprocessor.utils.FileIOUtils.INPUT_DIRECTORY_PATH;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class ApplicationProperties {
  private static final Logger logger = Logger.getLogger(ApplicationProperties.class.getName());

  private String filename = "data.csv";
  private int dataType = 0;
  private FileType fileType = FileType.CSV;

  private ApplicationProperties() {
  }

  private ApplicationProperties(Map<String, String> properties) {
    String filenameSpec = properties.get("filename");
    if (Objects.nonNull(filenameSpec) && isCsvFileName(filenameSpec)) {
      checkFileExists(filenameSpec);
      this.filename = filenameSpec;
    }

    String dataTypeSpec = properties.get("dataType");
    if (Objects.nonNull(dataTypeSpec) && isNumeric(dataTypeSpec)) {
      this.dataType = Integer.parseInt(dataTypeSpec);
    }

    String fileTypeSpec = properties.get("fileType");
    if (Objects.nonNull(fileTypeSpec)) {
      FileType type;
      try {
        type = FileType.valueOf(fileTypeSpec.toUpperCase());
      } catch (IllegalArgumentException e) {
        exitWithMessageAndStatusCode(
          "FileType (" + properties.get("fileType") + ") is not in supported\n" +
            "Supported values: " + Arrays.toString(FileType.values()));
        return;
      }
      this.fileType = type;
    }
  }

  private static boolean isCsvFileName(String filename) {
    return filename.endsWith(".csv");
  }

  private static void checkFileExists(String filename) {
    boolean exists = new File(INPUT_DIRECTORY_PATH + filename).exists();
    if (!exists) {
      throw new InputFileNotFoundException();
    }
  }

  private static void exitWithMessageAndStatusCode(String message) {
    logger.warning(message);
    System.exit(-1);
  }

  public static ApplicationProperties ofArgs(String[] args) {
    if (args.length == 0) {
      return new ApplicationProperties();
    }

    ApplicationProperties properties = getApplicationProperties(args);
    if (!isCsvFileName(properties.filename)) {
      exitWithMessageAndStatusCode(
        "Filename (" + properties.filename + ") is not in correct format => 'your-file-name.csv'");
    }
    return properties;
  }

  private static ApplicationProperties getApplicationProperties(String[] args) {
    Map<String, String> properties = new HashMap<>();

    for (int i = 0; i < args.length; i++) {
      String[] keyAndValue = args[ i ].split("=");
      if (keyAndValue.length == 2) {
        properties.put(keyAndValue[ 0 ], keyAndValue[ 1 ]);
      } else {
        exitWithMessageAndStatusCode("Invalid argument specification given on index " + i + ".");
      }
    }

    return new ApplicationProperties(properties);
  }

  public String getFilename() {
    return filename;
  }

  public int getDataType() {
    return dataType;
  }

  public FileType getFileType() {
    return fileType;
  }
}

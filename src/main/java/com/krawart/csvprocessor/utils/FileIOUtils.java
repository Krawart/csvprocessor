package com.krawart.csvprocessor.utils;

import com.krawart.csvprocessor.Application;
import com.krawart.csvprocessor.enums.FileType;
import com.krawart.csvprocessor.exceptions.HtmlTemplateNotFoundException;
import com.krawart.csvprocessor.exceptions.WriteOperationException;

import java.io.*;
import java.time.Instant;

public interface FileIOUtils {

  static String readHtmlTemplate() {
    File htmlTemplateFile = new File(Application.ABSOLUTE_PATH + "/template.html");
    StringBuilder sb = new StringBuilder();

    FileReader fileReader;
    try {
      fileReader = new FileReader(htmlTemplateFile);
    } catch (FileNotFoundException e) {
      throw new HtmlTemplateNotFoundException();
    }

    try {
      String str;
      BufferedReader reader = new BufferedReader(fileReader);
      while ((str = reader.readLine()) != null) {
        sb.append(str);
      }
      reader.close();
    } catch (IOException e) {
      throw new HtmlTemplateNotFoundException();
    }

    return sb.toString();
  }

  static int writeHtmlDocument(String filename, String html) {
    File newHtmlFile = new File(Application.OUTPUT_DIRECTORY_PATH + getOutputFilename(filename, FileType.HTML));

    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(newHtmlFile);
      fileWriter.write(html);
      fileWriter.close();
    } catch (IOException e) {
      throw new WriteOperationException("Problem when tried to save html export to " + newHtmlFile.getAbsolutePath());
    }
    return 0;
  }

  static String getOutputFilename(String filename, FileType fileType) {
    Instant now = Instant.now();

    /*
     * Filename has .csv suffix and we want to replace it with our new file suffix
     * */
    String name = filename.substring(0, filename.length() - 4);

    return now + name + fileType.getType();
  }
}

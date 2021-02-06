package com.krawart.csvprocessor.exceptions;

public class InputFileNotFoundException extends RuntimeException {
  public InputFileNotFoundException() {
    super("File not found in input directory. " +
      "Please specify correct filename as argument {filename=your-data.csv} or use default {data.csv} naming.");
  }
}

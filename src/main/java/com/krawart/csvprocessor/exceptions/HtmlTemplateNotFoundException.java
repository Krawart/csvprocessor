package com.krawart.csvprocessor.exceptions;

public class HtmlTemplateNotFoundException extends RuntimeException {
  public HtmlTemplateNotFoundException() {
    super("File not found in root directory.");
  }
}

package com.krawart.csvprocessor.enums;

public enum FileType {

  CSV(".csv"),
  HTML(".html"),
  XLS(".xls"),
  XLSX(".xlsx");

  private final String type;

  FileType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}

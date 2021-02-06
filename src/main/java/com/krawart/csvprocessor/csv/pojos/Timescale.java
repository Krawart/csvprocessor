package com.krawart.csvprocessor.csv.pojos;

import com.krawart.csvprocessor.enums.Quarter;

public class Timescale {

  private final int year;
  private final Quarter quarter;

  public Timescale(int year, Quarter quarter) {
    this.year = year;
    this.quarter = quarter;
  }

  public int getYear() {
    return year;
  }

  public Quarter getQuarter() {
    return quarter;
  }
}
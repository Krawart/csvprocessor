package com.krawart.csvprocessor.beans;

import com.krawart.csvprocessor.csv.converters.QuarterConverter;
import com.krawart.csvprocessor.csv.pojos.Timescale;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvNumber;

public class MarketShare implements CsvBean<MarketShare> {

  @CsvBindByName(column = "Country")
  private String country;

  @CsvCustomBindByName(column = "Timescale", converter = QuarterConverter.class)
  private Timescale timescale;

  @CsvBindByName(column = "Vendor")
  private String vendor;

  @CsvBindByName(column = "Units") //FIXME = read all numbers from string as int
  private int units;

  public String getCountry() {
    return country;
  }

  public Timescale getQuarter() {
    return timescale;
  }

  public String getVendor() {
    return vendor;
  }

  public int getUnits() {
    return units;
  }
}

package com.krawart.csvprocessor.beans;

import com.krawart.csvprocessor.csv.converters.QuarterConverter;
import com.krawart.csvprocessor.csv.pojos.Timescale;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

public class MarketShareInput implements CsvBean<MarketShareInput> {

  @CsvBindByName(column = "Country")
  private String country;

  @CsvCustomBindByName(column = "Timescale", converter = QuarterConverter.class)
  private Timescale timescale;

  @CsvBindByName(column = "Vendor")
  private String vendor;

  @CsvBindByName(column = "Units")
  private String units;

  public String getCountry() {
    return country;
  }

  public Timescale getTimescale() {
    return timescale;
  }

  public String getVendor() {
    return vendor;
  }

  public String getUnits() {
    return units;
  }
}

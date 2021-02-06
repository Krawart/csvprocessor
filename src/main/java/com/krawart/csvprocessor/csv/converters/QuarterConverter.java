package com.krawart.csvprocessor.csv.converters;

import com.krawart.csvprocessor.csv.pojos.Timescale;
import com.krawart.csvprocessor.enums.Quarter;
import com.opencsv.bean.AbstractBeanField;

public class QuarterConverter extends AbstractBeanField<Timescale, String> {

  public QuarterConverter() {
    super();
  }

  @Override
  protected Object convert(String value) {
    String[] split = value.split(" ");

    return new Timescale(Integer.parseInt(split[ 0 ]), Quarter.valueOf(split[ 1 ].toUpperCase()));
  }

  @Override
  public String convertToWrite(Object value) {
    Timescale timescale = (Timescale) value;
    return String.format("%s %s", timescale.getYear(), timescale.getQuarter());
  }

}

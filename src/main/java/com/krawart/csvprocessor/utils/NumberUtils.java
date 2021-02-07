package com.krawart.csvprocessor.utils;

import java.text.DecimalFormat;

public abstract class NumberUtils {

  public static final DecimalFormat PERCENTAGE_FORMATTER = new DecimalFormat("###.00");
  public static final DecimalFormat THOUSANDS_FORMATTER = new DecimalFormat("###,###");

  private NumberUtils() {
  }
}

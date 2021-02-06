package com.krawart.csvprocessor.csv.pojos;

import com.krawart.csvprocessor.beans.MarketShareInput;
import com.krawart.csvprocessor.enums.Quarter;

import java.util.Objects;

public class MarketShareData {
  private final String vendorName;
  private final String country;
  private final long soldUnits;
  private final Quarter quarter;
  private final int year;

  private MarketShareData(Builder builder) {
    this.vendorName = builder.vendorName;
    this.country = builder.country;
    this.soldUnits = builder.soldUnits;
    this.quarter = builder.quarter;
    this.year = builder.year;
  }

  public static MarketShareData ofMarketShare(MarketShareInput bean) {
    return new Builder()
      .vendorName(bean.getVendor())
      .country(bean.getCountry())
      .soldUnits(getNormalizeSoldUnitsInputData(bean.getUnits()))
      .quarter(bean.getTimescale().getQuarter())
      .year(bean.getTimescale().getYear())
      .build();
  }

  private static long getNormalizeSoldUnitsInputData(String units) {
    units = units.replace(".", "");
    return Long.parseUnsignedLong(units);
  }

  public String getVendorName() {
    return vendorName;
  }

  public String getCountry() {
    return country;
  }

  public long getSoldUnits() {
    return soldUnits;
  }

  public Quarter getQuarter() {
    return quarter;
  }

  public int getYear() {
    return year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MarketShareData)) return false;

    MarketShareData that = (MarketShareData) o;

    if (soldUnits != that.soldUnits) return false;
    if (year != that.year) return false;
    if (!Objects.equals(vendorName, that.vendorName)) return false;
    if (!Objects.equals(country, that.country)) return false;
    return quarter == that.quarter;
  }

  @Override
  public int hashCode() {
    int result = vendorName != null
      ? vendorName.hashCode()
      : 0;
    result = 31 * result + (country != null
      ? country.hashCode()
      : 0);
    result = 31 * result + (int) (soldUnits ^ (soldUnits >>> 32));
    result = 31 * result + (quarter != null
      ? quarter.hashCode()
      : 0);
    result = 31 * result + year;
    return result;
  }

  public static class Builder {
    private String vendorName;
    private String country;
    private long soldUnits;
    private Quarter quarter;
    private int year;

    public Builder builder() {
      return this;
    }

    public Builder vendorName(String value) {
      vendorName = value;
      return this;
    }

    public Builder country(String value) {
      country = value;
      return this;
    }

    public Builder soldUnits(long value) {
      soldUnits = value;
      return this;
    }

    public Builder quarter(Quarter value) {
      quarter = value;
      return this;
    }

    public Builder year(int value) {
      year = value;
      return this;
    }

    public MarketShareData build() {
      return new MarketShareData(this);
    }
  }
}

package com.krawart.csvprocessor.csv.rows;

import com.krawart.csvprocessor.beans.MarketShare;
import com.krawart.csvprocessor.enums.Quarter;

import java.util.Objects;

public class MarketShareRow implements DataRow {
  private final String vendorName;
  private final String country;
  private final int soldUnits;
  private final Quarter quarter;
  private final int year;

  private MarketShareRow(Builder builder) {
    this.vendorName = builder.vendorName;
    this.country = builder.country;
    this.soldUnits = builder.soldUnits;
    this.quarter = builder.quarter;
    this.year = builder.year;
  }

  public String getVendorName() {
    return vendorName;
  }

  public String getCountry() {
    return country;
  }

  public int getSoldUnits() {
    return soldUnits;
  }

  public Quarter getQuarter() {
    return quarter;
  }

  public int getYear() {
    return year;
  }

  public static MarketShareRow ofMarketShare(MarketShare bean) {
    return new Builder()
      .vendorName(bean.getVendor())
      .country(bean.getCountry())
      .soldUnits(bean.getUnits())
      .quarter(bean.getQuarter().getQuarter())
      .year(bean.getQuarter().getYear())
      .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MarketShareRow)) return false;

    MarketShareRow that = (MarketShareRow) o;

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
    result = 31 * result + soldUnits;
    result = 31 * result + (quarter != null
      ? quarter.hashCode()
      : 0);
    result = 31 * result + year;
    return result;
  }

  public static class Builder {
    private String vendorName;
    private String country;
    private int soldUnits;
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

    public Builder soldUnits(int value) {
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

    public MarketShareRow build() {
      return new MarketShareRow(this);
    }
  }
}

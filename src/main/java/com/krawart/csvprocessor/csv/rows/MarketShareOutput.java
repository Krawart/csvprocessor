package com.krawart.csvprocessor.csv.rows;

import com.opencsv.bean.CsvBindByName;

import java.util.StringJoiner;

public class MarketShareOutput implements RowData<MarketShareOutput> {
  @CsvBindByName
  private final String vendor;

  @CsvBindByName
  private final long units;

  @CsvBindByName
  private final double share;

  public MarketShareOutput(String vendor, long units, double share) {
    this.vendor = vendor;
    this.units = units;
    this.share = share;
  }

  public double getShare() {
    return share;
  }

  @SuppressWarnings("unused")
  public int compareVendorTo(MarketShareOutput o) {
    return this.getVendor().compareTo(o.getVendor());
  }

  public String getVendor() {
    return vendor;
  }

  /**
   * Is alternative method to default compareTo method
   *
   * @param o Another object of the same type
   * @return Integer used to sort
   */
  public int compareUnitsTo(MarketShareOutput o) {
    return this.compareTo(o);
  }

  @Override
  public int compareTo(MarketShareOutput o) {
    if (this.getUnits() < o.getUnits()) {
      return 1;
    } else if (this.getUnits() == o.getUnits()) {
      return 0;
    }
    return -1;
  }

  public long getUnits() {
    return units;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + MarketShareOutput.class.getSimpleName() + "[", "]")
      .add("vendor='" + vendor + "'")
      .add("units=" + units)
      .add("share=" + share)
      .toString();
  }
}

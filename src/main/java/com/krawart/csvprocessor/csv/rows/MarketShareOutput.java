package com.krawart.csvprocessor.csv.rows;

import com.opencsv.bean.CsvBindByName;

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

  public int compareVendorTo(MarketShareOutput o) {
    return this.getVendor().compareTo(o.getVendor());
  }

  public String getVendor() {
    return vendor;
  }
}

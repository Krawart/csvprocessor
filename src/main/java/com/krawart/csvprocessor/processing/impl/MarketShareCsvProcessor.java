package com.krawart.csvprocessor.processing.impl;

import com.krawart.csvprocessor.beans.MarketShare;
import com.krawart.csvprocessor.csv.rows.MarketShareRow;
import com.krawart.csvprocessor.processing.CsvBeanProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class MarketShareCsvProcessor extends CsvBeanProcessor<MarketShare, MarketShareRow> {

  public MarketShareCsvProcessor() {
    super(MarketShare.class);
  }

  @Override
  protected List<MarketShareRow> analyzeBeans(List<MarketShare> beans) {
    List<MarketShareRow> rows = beans.stream().map(MarketShareRow::ofMarketShare).collect(Collectors.toList());
    return rows;
  }
}

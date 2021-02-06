package com.krawart.csvprocessor.processing.impl;

import com.krawart.csvprocessor.beans.MarketShareInput;
import com.krawart.csvprocessor.csv.rows.MarketShareOutput;
import com.krawart.csvprocessor.enums.Quarter;
import com.krawart.csvprocessor.processing.CsvBeanProcessor;
import com.krawart.csvprocessor.utils.FileIOUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MarketShareCsvProcessor extends CsvBeanProcessor<MarketShareInput, MarketShareOutput> {

  /*
   * Filter parameters = could be refactored to be parsed by run arguments
   * */
  private final String country = "Czech Republic";
  private final int year = 2010;
  private final Quarter quarter = Quarter.Q4;


  public MarketShareCsvProcessor() {
    super(MarketShareInput.class);
  }


  @Override
  protected int exportToHtml(List<MarketShareOutput> analyzedBeans, String filename) {

    String html = FileIOUtils.readHtmlTemplate();

    String title = "MarketShareTable";
    StringBuilder bodyBuilder = new StringBuilder();

    bodyBuilder.append("<table>");

    bodyBuilder.append("<thead>");
    bodyBuilder.append("<th>Vendor</th>");
    bodyBuilder.append("<th>Units</th>");
    bodyBuilder.append("<th>Share</th>");
    bodyBuilder.append("</thead>");

    bodyBuilder.append("<tbody>");

    int total = 0;
    for (MarketShareOutput item : analyzedBeans) {
      total += item.getUnits();
      bodyBuilder.append("<tr>");
      bodyBuilder.append("<td>").append(item.getVendor()).append("</td>");
      bodyBuilder.append("<td>").append(item.getUnits()).append("</td>");
      bodyBuilder.append("<td>").append(item.getShare() * 100).append("%</td>");
      bodyBuilder.append("</tr>");
    }

    bodyBuilder.append("<tr>");
    bodyBuilder.append("<td>Total</td>");
    bodyBuilder.append("<td>").append(total).append("</td>");
    bodyBuilder.append("<td>100%</td>");
    bodyBuilder.append("</tr>");

    bodyBuilder.append("</tbody>");
    bodyBuilder.append("</table>");


    html = html.replace("$title", title);
    html = html.replace("$body", bodyBuilder.toString());
    return FileIOUtils.writeHtmlDocument(filename, html);
  }

  @Override
  protected List<MarketShareOutput> processBeans(List<MarketShareInput> beans) {
    Map<String, Long> unitsByVendor = new HashMap<>();

    long totalUnits = 0L;
    long units;
    for (MarketShareInput bean : getFilteredBeans(beans)) {
      units = getNormalizeSoldUnitsInputData(bean.getUnits());
      unitsByVendor.merge(
        bean.getVendor(),
        units,
        Long::sum);
      totalUnits += units;
    }

    return getOutputFromMap(unitsByVendor, totalUnits);
  }

  private List<MarketShareInput> getFilteredBeans(List<MarketShareInput> beans) {
    return beans.stream()
      .filter(bean ->
        bean.getCountry().equals(country)
          && bean.getTimescale().getYear() == year
          && bean.getTimescale().getQuarter().equals(quarter))
      .collect(Collectors.toList());
  }

  private static long getNormalizeSoldUnitsInputData(String units) {
    units = units.replace(".", "");
    return Long.parseUnsignedLong(units);
  }

  private List<MarketShareOutput> getOutputFromMap(Map<String, Long> unitsByVendor, final long totalUnits) {
    return unitsByVendor.entrySet().stream()
      .map(entry -> {
        String vendor = entry.getKey();
        long units = entry.getValue();
        return new MarketShareOutput(vendor, units, getShare(units, totalUnits));
      })
      .sorted()
      .collect(Collectors.toList());
  }

  private float getShare(long sold, long sum) {
    return ((float) sold) / sum;
  }

}
package com.krawart.csvprocessor.processing.impl;

import com.krawart.csvprocessor.beans.MarketShareInput;
import com.krawart.csvprocessor.csv.rows.MarketShareOutput;
import com.krawart.csvprocessor.enums.Quarter;
import com.krawart.csvprocessor.processing.CsvBeanProcessor;
import com.krawart.csvprocessor.utils.FileIOUtils;
import com.krawart.csvprocessor.utils.HtmlUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MarketShareCsvProcessor extends CsvBeanProcessor<MarketShareInput, MarketShareOutput> {

  private final String country;
  private final int year;
  private final Quarter quarter;

  /**
   * Filter parameters = should be refactored to be parsed as runtime arguments
   *
   * @param country filter results by country
   * @param year    filter results by year
   * @param quarter filter results by quarter
   */
  public MarketShareCsvProcessor(String country, int year, Quarter quarter) {
    super(MarketShareInput.class);
    this.country = country;
    this.year = year;
    this.quarter = quarter;
  }

  @Override
  protected int exportToHtml(List<MarketShareOutput> analyzedBeans, String filename) {

    String html = FileIOUtils.readHtmlTemplate();

    html = html.replace("$title", "MarketShareTable");
    html = html.replace("$body", buildHtmlTable(analyzedBeans));

    FileIOUtils.writeHtmlDocument(filename, html);

    return 0;
  }

  private String buildHtmlTable(List<MarketShareOutput> analyzedBeans) {
    StringBuilder bodyBuilder = new StringBuilder();

    bodyBuilder.append("<p>Table 1, PC Quarterly Market Share, the ")
      .append(country).append(", ").append(quarter).append("-").append(year)
      .append("</p>");

    bodyBuilder.append("<table>");
    bodyBuilder.append("<thead>");
    bodyBuilder.append("<th></th>");
    bodyBuilder.append("<th>Vendor</th>");
    bodyBuilder.append("<th>Units</th>");
    bodyBuilder.append("<th>Share</th>");
    bodyBuilder.append("</thead>");

    bodyBuilder.append("<tbody>");

    Long total = 0L;
    MarketShareOutput item;
    DecimalFormat percentage = new DecimalFormat("###.00");
    DecimalFormat thousands = new DecimalFormat("###,###");
    for (int i = 0; i < analyzedBeans.size(); i++) {
      item = analyzedBeans.get(i);

      bodyBuilder.append("<tr>");
      HtmlUtils.appendTdElement(bodyBuilder, i + 1);
      HtmlUtils.appendTdElement(bodyBuilder, item.getVendor());
      HtmlUtils.appendTdElement(bodyBuilder, thousands.format(item.getUnits()));
      HtmlUtils.appendTdElement(bodyBuilder, percentage.format(item.getShare() * 100));
      bodyBuilder.append("</tr>");

      total += item.getUnits();
    }

    bodyBuilder.append("<tr style='background: #FFFF99'>");
    HtmlUtils.appendTdElement(bodyBuilder, "");
    HtmlUtils.appendTdElement(bodyBuilder, "Total");
    HtmlUtils.appendTdElement(bodyBuilder, thousands.format(total));
    HtmlUtils.appendTdElement(bodyBuilder, (100 + "%")); // Sum of all shares
    bodyBuilder.append("</tr>");

    bodyBuilder.append("</tbody>");
    bodyBuilder.append("</table>");

    return bodyBuilder.toString();
  }

  /**
   * Implementation for current dataType
   *
   * @param beans Raw data read from csv file
   * @return Processed data - filtered, merged and sorted
   */
  @Override
  protected List<MarketShareOutput> processBeans(List<MarketShareInput> beans) {
    Map<String, Long> unitsByVendor = new HashMap<>();

    long totalUnits = 0L;
    long units;
    for (MarketShareInput bean : getFilteredBeans(beans)) {
      units = getNormalizeSoldUnitsInputData(bean.getUnits());

      // TODO = If multiple records for one filter setup is not possible, exception should be thrown instead merge
      unitsByVendor.merge(
        bean.getVendor(),
        units,
        Long::sum);

      totalUnits += units;
    }

    return getOutputFromMap(unitsByVendor, totalUnits);
  }

  /**
   * Helper function to filter input beans along the filter parameters specified, when object is created
   *
   * @param beans Raw data read from csv file
   * @return Filtered data by country, year and quarter
   */
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
    if (unitsByVendor == null) throw new IllegalArgumentException("unitsByVendor are null. Process cannot continue");

    return unitsByVendor.entrySet().stream()
      .map(entry -> {
        String vendor = entry.getKey();
        long units = entry.getValue();
        return new MarketShareOutput(vendor, units, getShare(units, totalUnits));
      })
      /*
       * Can be used instead default compareTo comparator
       * */
//      .sorted(MarketShareOutput::compareVendorTo)
      .sorted()
      .collect(Collectors.toList());
  }

  private double getShare(long sold, long sum) {
    return ((double) sold) / sum;
  }

}
package com.krawart.csvprocessor.utils;

public interface HtmlUtils {

  /**
   * Simplifies td element creation
   *
   * @param builder Builder to append next td element
   * @param content Any object, what is going to be showed in table as string
   */
  static void appendTdElement(StringBuilder builder, Object content) {
    builder.append("<td>").append(content).append("</td>");
  }

  /**
   * Simplifies th element creation
   *
   * @param builder Builder to append next td element
   * @param content Any object, what is going to be showed in table as string
   */
  static void appendThElement(StringBuilder builder, Object content) {
    builder.append("<th>").append(content).append("</th>");
  }
}

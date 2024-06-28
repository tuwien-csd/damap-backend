package org.damap.base.domain;

public enum DatasetSizeRange {
  LESS_THAN_100MB(0, 99_999_999L, "< 100 MB"),
  FROM_100MB_TO_1GB(100_000_000L, 999_999_999L, "100 - 1000 MB"),
  FROM_1GB_TO_5GB(1_000_000_000L, 4_999_999_999L, "1 - 5 GB"),
  FROM_5GB_TO_20GB(5_000_000_000L, 19_999_999_999L, "5 - 20 GB"),
  FROM_20GB_TO_50GB(20_000_000_000L, 49_999_999_999L, "20 - 50 GB"),
  FROM_50GB_TO_100GB(50_000_000_000L, 99_999_999_999L, "50 - 100 GB"),
  FROM_100GB_TO_500GB(100_000_000_000L, 499_999_999_999L, "100 - 500 GB"),
  FROM_500GB_TO_1TB(500_000_000_000L, 999_999_999_999L, "500 - 1000 GB"),
  FROM_1TB_TO_5TB(1_000_000_000_000L, 4_999_999_999_999L, "1 - 5 TB"),
  FROM_5TB_TO_10TB(5_000_000_000_000L, 9_999_999_999_999L, "5 - 10 TB"),
  FROM_10TB_TO_100TB(10_000_000_000_000L, 99_999_999_999_999L, "10 - 100 TB"),
  FROM_100TB_TO_500TB(100_000_000_000_000L, 499_999_999_999_999L, "100 - 500 TB"),
  FROM_500TB_TO_1PB(500_000_000_000_000L, 999_999_999_999_999L, "500 - 1000 TB"),
  MORE_THAN_1PB(1_000_000_000_000_000L, Long.MAX_VALUE, "> 1 PB"),
  UNKNOWN(-1, -1, "I don't know yet");

  private final long minSize;
  private final long maxSize;
  private final String label;

  DatasetSizeRange(long minSize, long maxSize, String label) {
    this.minSize = minSize;
    this.maxSize = maxSize;
    this.label = label;
  }

  public long getMinSize() {
    return minSize;
  }

  public long getMaxSize() {
    return maxSize;
  }

  public String getLabel() {
    return label;
  }

  /**
   * Retrieves the label for a given size.
   *
   * @param size the size to evaluate
   * @return the corresponding label of the size range
   */
  public static String getLabelForSize(long size) {
    for (DatasetSizeRange range : values()) {
      if (size >= range.getMinSize() && size <= range.getMaxSize()) {
        return range.getLabel();
      }
    }
    return "Unknown size";
  }
}

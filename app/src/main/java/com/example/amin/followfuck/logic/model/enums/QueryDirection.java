package com.example.amin.followfuck.logic.model.enums;

import com.example.amin.followfuck.logic.impl.utils.EnumLookup;

public enum QueryDirection {
  PREV("prev"),
  NEXT("next");

  private final String code;

  QueryDirection(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<QueryDirection> lookup = new EnumLookup<>(QueryDirection.class);

  public static QueryDirection lookup(String name) {
    return lookup.lookup(name);
  }
}

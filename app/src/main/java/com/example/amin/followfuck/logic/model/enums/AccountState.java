package com.example.amin.followfuck.logic.model.enums;

import com.example.amin.followfuck.logic.impl.utils.EnumLookup;

/**
 * working, lock.
 */
public enum AccountState {
  WORKING("working"),
  LOCK("lock");

  private final String code;

  AccountState(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<AccountState> lookup = new EnumLookup<>(AccountState.class);

  public static AccountState lookup(String name) {
    return lookup.lookup(name);
  }
}

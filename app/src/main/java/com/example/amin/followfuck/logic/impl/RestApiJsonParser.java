package com.example.amin.followfuck.logic.impl;

import com.example.amin.followfuck.logic.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}

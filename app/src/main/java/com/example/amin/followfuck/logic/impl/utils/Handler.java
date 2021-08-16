package com.example.amin.followfuck.logic.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}

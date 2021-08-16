package com.example.amin.followfuck.logic.impl;

import okhttp3.Request;

public class RestApiRequest<T> {

  public Request request;
  RestApiJsonParser<T> jsonParser;
}

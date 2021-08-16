package com.example.amin.followfuck.logic;

import com.example.amin.followfuck.logic.exception.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}

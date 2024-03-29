package com.example.amin.followfuck.logic.impl;

import com.example.amin.followfuck.logic.SubscriptionErrorHandler;
import com.example.amin.followfuck.logic.SubscriptionListener;
import com.example.amin.followfuck.logic.impl.utils.Handler;

class WebsocketRequest<T> {

    WebsocketRequest(SubscriptionListener<T> listener, SubscriptionErrorHandler errorHandler) {
        this.updateCallback = listener;
        this.errorHandler = errorHandler;
    }

    String signatureVersion = "2";
    String name;
    Handler<WebSocketConnection> connectionHandler;
    Handler<WebSocketConnection> authHandler = null;
    final SubscriptionListener<T> updateCallback;
    RestApiJsonParser<T> jsonParser;
    final SubscriptionErrorHandler errorHandler;
}

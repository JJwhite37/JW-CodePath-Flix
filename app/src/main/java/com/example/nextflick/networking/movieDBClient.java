package com.example.nextflick.networking;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;


public class movieDBClient {
    private AsyncHttpClient client;

    public movieDBClient() {
        this.client = new AsyncHttpClient();
    }
    public void getMovieInfo(final String query, JsonHttpResponseHandler handler) {
        client.get(query, handler);
    }
}

package com.evastos.googleimages.api.client;

import android.os.AsyncTask;

import com.evastos.googleimages.api.parser.Parser;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class HttpClient<T, S> {

    private ClientResultListener<T, S> clientResultListener;
    private Parser<T> responseParser;
    private Parser<S> errorParser;
    private HttpMethod httpMethod;
    private RunClientAsyncTask runClientAsyncTask;

    public HttpClient(final Parser<T> responseParser, final Parser<S> errorParser,
                      final HttpMethod httpMethod) {
        this.responseParser = responseParser;
        this.errorParser = errorParser;
        this.httpMethod = httpMethod;
    }

    /**
     * Executes HTTP request to URL
     *
     * @param url the URL
     */
    public void runClient(final String url) {
        runClientAsyncTask = new RunClientAsyncTask(url);
        runClientAsyncTask.execute();
    }

    public void setClientResultListener(ClientResultListener<T, S> clientResultListener) {
        this.clientResultListener = clientResultListener;
    }

    /**
     * Returns true if HTTP request if it is being executed, false otherwise
     *
     * @return is HTTP request in process
     */
    public boolean isRunning() {
        return runClientAsyncTask != null ? runClientAsyncTask.isRunning() : false;
    }

    /**
     * Cancels HTTP request if it is being executed
     */
    public void cancelTask() {
        if (runClientAsyncTask != null) {
            runClientAsyncTask.cancel(true);
        }
    }

    private class RunClientAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private String url;
        private T responseObj;
        private S errorObj;
        private boolean running = false;

        public RunClientAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpConnectionHandler connectionHandler = new HttpConnectionHandler();
            HttpConnectionHandler.HttpResult httpResult = connectionHandler.openHttpConnection(url, httpMethod);
            if (httpResult.isHttpOk()) {
                responseObj = responseParser.parse(httpResult.getResultString());
            } else {
                errorObj = errorParser.parse(httpResult.getResultString());
            }
            return httpResult.isHttpOk();
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            running = false;
            if (isCancelled()) {
                return;
            }
            if (clientResultListener != null) {
                if (isSuccess) {
                    clientResultListener.onSuccess(HttpClient.this, responseObj);
                } else {
                    clientResultListener.onError(HttpClient.this, errorObj);
                }
            }
        }

        public boolean isRunning() {
            return running;
        }
    }

}

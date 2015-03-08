package com.evastos.googleimages.api.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @author Eva Stos on 7.3.2015..
 */
public class HttpConnectionHandler {

    private static final int CONNECTION_TIMEOUT = 30000;
    private int connectionTimeout = CONNECTION_TIMEOUT;
    private static final int SOCKET_TIMEOUT = 30000;
    private int socketTimeout = SOCKET_TIMEOUT;
    public static int HTTP_ERROR_UNKNOWN = -1;

    public HttpConnectionHandler() {
        super();
    }

    private static String readStream(InputStream inputStream) {
        String response = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, Charset.forName("UTF-8")), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            response = stringBuilder.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return response;
    }

    /**
     * Opens HTTP connection to URL declared in urlString
     *
     * @param urlString  the URL
     * @param httpMethod
     * @return HttpResult result object
     */
    public HttpResult openHttpConnection(final String urlString, final HttpMethod httpMethod) {

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setConnectTimeout(connectionTimeout);
            httpConnection.setReadTimeout(socketTimeout);
            httpConnection.setRequestMethod(httpMethod.toString());
            httpConnection.connect();
            return new HttpResult(readStream(httpConnection.getInputStream()), httpConnection.getResponseCode());
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new HttpResult("", HTTP_ERROR_UNKNOWN);
    }

    public class HttpResult {

        private String resultString = "";

        public HttpResult(String result, int responseCode) {
            this.resultString = result;
            this.responseCode = responseCode;
        }

        private int responseCode = HTTP_ERROR_UNKNOWN;

        public String getResultString() {
            return resultString;
        }

        public boolean isHttpOk() {
            return responseCode == HttpURLConnection.HTTP_OK;
        }


    }

}

package com.wingman.client.api.net;

import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.IOException;
import java.nio.file.Path;

/**
 * {@link HttpClient} contains the recommended API for handling web requests.
 * <p>
 * It utilizes the {@link OkHttpClient} library for efficient request handling.
 * <p>
 * Request caching is not enabled by default because of potential file conflict
 * in the event of multiple client instances being run at the same time.
 */
public class HttpClient extends OkHttpClient {

    /**
     * Performs a synchronous download of the file from the URL specified, to the save path specified.
     *
     * @param url the URL of the file
     * @param savePath the save path of the downloaded file
     * @throws IOException if the download failed
     */
    public void downloadFileSync(String url, Path savePath) throws IOException {
        Response response = downloadUrlSync(url);
        BufferedSink sink = Okio.buffer(Okio.sink(savePath.toFile()));
        sink.writeAll(response.body().source());
        sink.close();
    }

    /**
     * Performs an asynchronous download of the file from the URL specified, to the save path specified.
     *
     * @param url the URL of the file
     * @param savePath the save path of the downloaded file
     * @throws IOException if the download failed
     */
    public void downloadFileAsync(String url, final Path savePath) throws IOException {
        downloadUrlAsync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedSink sink = Okio.buffer(Okio.sink(savePath.toFile()));
                sink.writeAll(response.body().source());
                sink.close();
            }
        });
    }

    /**
     * Constructs a synchronous request to the URL specified.
     *
     * @param url the URL of a request
     * @return the response for the download request
     * @throws IOException if the download failed
     */
    public Response downloadUrlSync(String url) throws IOException {
        Request request = getRequestBuilder()
                .url(url)
                .build();

        return newCall(request)
                .execute();
    }

    /**
     * Constructs an asynchronous request to the URL specified, and places it on the OkHttp worker thread.
     * The instance of {@link Callback} specified in the arguments is called upon completion of the request.
     *
     * @param url the URL of your request
     * @param callback the {@link Callback} called after request completion
     * @throws IOException if the download failed
     */
    public void downloadUrlAsync(String url, Callback callback) throws IOException {
        Request request = getRequestBuilder()
                .url(url)
                .build();

        newCall(request).enqueue(callback);
    }

    /**
     * Sends a HEAD request to the URL specified and returns the value of the Content-Length response header.
     *
     * @param url the URL to get the Content-Length of
     * @return the Content-Length of the URL
     * @throws IOException if the download failed
     * @throws NumberFormatException if the Content-Length returned is not a number
     */
    public int getContentLength(String url) throws IOException, NumberFormatException {
        Request request = getRequestBuilder()
                .url(url)
                .head()
                .build();

        Response response = newCall(request)
                .execute();

        int length = Integer.parseInt(response.header("Content-Length"));

        response.body().close();

        return length;
    }

    /**
     * @return a request builder with request headers attempting to mimic a real browser
     */
    public Request.Builder getRequestBuilder() {
        return new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0")
                .addHeader("Accept-Language", "en-US,en;q=0.5")
                .addHeader("DNT", "1")
                .addHeader("Connection", "keep-alive");
    }
}

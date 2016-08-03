package com.wingman.client.api.net;

import com.google.common.base.Throwables;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import okio.BufferedSink;
import okio.Okio;

import java.io.IOException;
import java.nio.file.Path;

/**
 * {@link HttpClient} contains the recommended API for handling web requests. <br>
 *
 * It utilizes the {@link OkHttpClient} library for efficient request handling. <br>
 * Request caching is not enabled by default because of potential file conflict in the event of multiple client instances being run at the same time.
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
            public void onFailure(Request request, IOException e) {
                Throwables.propagate(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                BufferedSink sink = Okio.buffer(Okio.sink(savePath.toFile()));
                sink.writeAll(response.body().source());
                sink.close();
            }
        });
    }

    /**
     * Constructs a synchronous request to the URL specified. <br>
     * Returns {@link Response} upon completion.
     *
     * @param url the URL of your request
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
     * Constructs an asynchronous request to the URL specified, and places it on the OkHttp worker thread. <br>
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
     * Constructs a {@link com.squareup.okhttp.Request.Builder} with request headers attempting to mimic a real browser.
     */
    public Request.Builder getRequestBuilder() {
        return new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0")
                .addHeader("Accept-Language", "en-US,en;q=0.5")
                .addHeader("DNT", "1")
                .addHeader("Connection", "keep-alive");
    }
}

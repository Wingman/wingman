package com.wingman.client.util;

import com.wingman.client.ui.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtil {

    /**
     * Attempts to retrieve a {@link InputStream} representation of a file
     * as seen from the client's class loader resource path.
     *
     * @param path a path to a file accessible through the client class loader
     * @return a {@link InputStream} representation of the file passed
     */
    public static InputStream getFile(String path) {
        return Client.class.getResourceAsStream(path);
    }

    /**
     * Attempts to retrieve a {@code byte[]} representation of a file
     * as seen from the client's class loader resource path.
     *
     * @param path a path to a file accessible through the client class loader
     * @return a {@code byte[]} representation of a file accessible through the client class loader,
     *         or an empty array if the file does not exist
     */
    public static byte[] getFileAsBytes(String path) throws IOException {
        InputStream inputStream = getFile(path);
        if (inputStream != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int read;
            byte[] data = new byte[4096];
            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                output.write(data, 0, read);
            }
            inputStream.close();
            return output.toByteArray();
        }
        return new byte[0];
    }

    private FileUtil() {
        // This class should not be instantiated
    }
}

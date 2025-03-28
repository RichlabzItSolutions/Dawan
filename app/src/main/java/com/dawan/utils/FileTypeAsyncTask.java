package com.dawan.utils;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileTypeAsyncTask extends AsyncTask<String, Void, FileTypeAsyncTask.FileType> {
    private FileTypeCallback callback;

    public FileTypeAsyncTask(FileTypeCallback callback) {
        this.callback = callback;
    }

    @Override
    protected FileType doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            // Get the Content-Type header
            String contentType = connection.getContentType();
            if (contentType != null) {
                // Check if it's an image
                if (contentType.startsWith("image/")) {
                    return FileType.IMAGE;
                }
                // Check if it's a video
                else if (contentType.startsWith("video/")) {
                    return FileType.VIDEO;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileType.UNKNOWN; // Return unknown if unable to determine file type
    }

    @Override
    protected void onPostExecute(FileType fileType) {
        if (callback != null) {
            callback.onFileTypeReceived(fileType);
        }
    }

    public interface FileTypeCallback {
        void onFileTypeReceived(FileType fileType);
    }

    public enum FileType {
        IMAGE,
        VIDEO,
        UNKNOWN
    }
}

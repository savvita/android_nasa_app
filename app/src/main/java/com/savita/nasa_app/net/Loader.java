package com.savita.nasa_app.net;


import android.util.Log;
import android.widget.ImageView;

import com.savita.nasa_app.configs.AppConfig;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.Consumer;

import javax.net.ssl.HttpsURLConnection;

public class Loader {
    private static final String TAG = "Loader";
    public static void loadImageInto(String url, ImageView into) {
        into.post( () -> Picasso.with(into.getContext())
                .load(url)
                .into(into));
    }

    public static void downloadImage(String url, File into, Consumer<Void> onCompleted, Consumer<Void> onError) {
        try {
            URL url1 = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) url1.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url1.openStream(), AppConfig.BUFFER_SIZE);

            OutputStream output = new FileOutputStream(into);

            byte data[] = new byte[AppConfig.BUFFER_SIZE];
            int count;

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();

            output.close();
            input.close();

            if(onCompleted != null) {
                onCompleted.accept(null);
            }
        } catch(Exception ex) {
            Log.d(TAG, "downloadImage: ", ex);
            if(onError != null) {
                onError.accept(null);
            }
        }
    }
}

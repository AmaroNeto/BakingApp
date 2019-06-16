package com.amaro.bakingapp.util;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static URL parseToURL(String path) {

        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}

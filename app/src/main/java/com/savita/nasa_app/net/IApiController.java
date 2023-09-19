package com.savita.nasa_app.net;

import java.util.HashMap;

public interface IApiController {
    String get(String baseUrl, HashMap<String, String> params);
}

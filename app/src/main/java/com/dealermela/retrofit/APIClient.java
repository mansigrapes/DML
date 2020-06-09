package com.dealermela.retrofit;


import android.support.annotation.NonNull;

import com.dealermela.util.AppConstants;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = AppConstants.BASE_URL;
    private static Retrofit retrofit = null;
//    private static Map<String, String> queryParams = new HashMap<>();

    public static Retrofit getClient() {
//        queryParams.put("api key","djkfbhsjdbfsdfmkdmfksdmfk");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(new BasicAuthInterceptor("", ""))
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    private static OkHttpClient get_HTTPClient(final Map<String, String> headers) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder(); // <-- this is the important line

                for (Map.Entry<String, String> pairs : headers.entrySet()) {
                    if (pairs.getValue() != null) {
                        requestBuilder.header(pairs.getKey(), pairs.getValue());
                    }
                }
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }
}

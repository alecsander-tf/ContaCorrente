package br.com.contacorrente.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.contacorrente.network.exception.NotFoundException;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.118:8080/api/v1/";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder().create();

    private static OkHttpClient.Builder okHttpClientBuilder =
            new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Response response = chain.proceed(chain.request());
                        if (!response.isSuccessful()) {
                            throw new NotFoundException("Server error code: " + response.code() + " with error message: " + response.message());
                        }
                        return response;
                    });

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


}

package org.ganymede.minicompiler.Api;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiHandler {

    private static final String BASE_URL = "https://api.jdoodle.com/v1/";

    public static final String API_ID = "acdd7e90f117557aa9c03c31050e702c";
    public static final String API_SECRET = "3a0809fce806d756fee73fdfc89009fee56007b23d16815ef9ab81f09a2cf4b0";
    public static final String LANGUAGE = "c";
    public static final String VERSION_INDEX = "0";


    private static Retrofit retrofit;

    public static ApiService getRetrofitInstance(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }

}

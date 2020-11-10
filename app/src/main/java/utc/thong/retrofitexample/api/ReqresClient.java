package utc.thong.retrofitexample.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReqresClient {
    private static Retrofit retrofit = null;

    private ReqresClient() {

    }

    public static Retrofit getReqresClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ReqresApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}

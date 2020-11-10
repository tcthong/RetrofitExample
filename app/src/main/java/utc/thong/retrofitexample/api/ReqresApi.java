package utc.thong.retrofitexample.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReqresApi {
    String BASE_URL = "https://reqres.in/";

    @GET("api/users?per_page=12")
    Call<ReqresResponse> getUsers();
}

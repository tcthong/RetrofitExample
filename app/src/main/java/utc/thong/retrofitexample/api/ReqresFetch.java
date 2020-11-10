package utc.thong.retrofitexample.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utc.thong.retrofitexample.model.User;

public class ReqresFetch {
    private static final String TAG = ReqresFetch.class.getSimpleName();
    private ReqresApi reqresApi;

    public ReqresFetch() {
        reqresApi = ReqresClient.getReqresClient()
                .create(ReqresApi.class);
    }

    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> res = new MutableLiveData<>();
        reqresApi.getUsers().enqueue(new Callback<ReqresResponse>() {
            @Override
            public void onResponse(Call<ReqresResponse> call, Response<ReqresResponse> response) {
                if (response.isSuccessful()) {
                    ReqresResponse reqresResponse = response.body();
                    List<User> users = reqresResponse.getUsers();
                    res.setValue(users);
                    Log.d(TAG, "Size: " + Integer.toString(users.size()));
                }
            }

            @Override
            public void onFailure(Call<ReqresResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    call.cancel();
                }

                Log.d(TAG, t.getMessage());
            }
        });

        return res;
    }
}

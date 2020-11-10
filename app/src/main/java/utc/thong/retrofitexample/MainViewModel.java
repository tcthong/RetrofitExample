package utc.thong.retrofitexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import utc.thong.retrofitexample.api.ReqresFetch;
import utc.thong.retrofitexample.model.User;

public class MainViewModel extends ViewModel {
    private ReqresFetch reqresFetch;
    private LiveData<List<User>> usersLiveData;

    public MainViewModel() {
        reqresFetch = new ReqresFetch();
        usersLiveData = reqresFetch.getUsers();
    }

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }
}

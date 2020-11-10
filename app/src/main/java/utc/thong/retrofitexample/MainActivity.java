package utc.thong.retrofitexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import utc.thong.retrofitexample.model.User;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        init();
    }

    private void init() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayLoadingUi();

        viewModel.getUsersLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    displaySuccessUi();
                    recyclerView.setAdapter(new UserAdapter(users));
                }
            }
        });
    }

    private void displayLoadingUi() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void displaySuccessUi() {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> users;

        public UserAdapter(List<User> users) {
            this.users = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.user_item, parent, false);
            return new UserHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bindUser(users.get(position));
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder {
        private User user;
        private ImageView imgAvatar;
        private TextView tvFullName;
        private TextView tvEmail;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.avatar_img);
            tvFullName = itemView.findViewById(R.id.full_name_tv);
            tvEmail = itemView.findViewById(R.id.email_tv);
        }

        public void bindUser(User user) {
            this.user = user;
            Glide.with(itemView)
                    .load(user.getAvatar())
                    .error(R.drawable.ic_error_black_80dp)
                    .placeholder(R.drawable.ic_place_holder_black_80dp)
                    .circleCrop()
                    .into(imgAvatar);

            tvFullName.setText(user.getFullName());
            tvEmail.setText(user.getEmail());
        }
    }
}
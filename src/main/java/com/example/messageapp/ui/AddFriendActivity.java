package com.example.messageapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;
import com.example.messageapp.features.addfriend.adapter.AddFriendAdapter;
import com.example.messageapp.features.addfriend.AddFriendViewModel;
import com.example.messageapp.model.User;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AddFriendActivity extends AppCompatActivity {

    private RecyclerView rvSearchResults;
    private AddFriendAdapter userAdapter;
    private AddFriendViewModel userViewModel;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        rvSearchResults = findViewById(R.id.rvSearchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);  // Đảm bảo có EditText để tìm kiếm

        userViewModel = new ViewModelProvider(this).get(AddFriendViewModel.class);

        userAdapter = new AddFriendAdapter(new AddFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(User user) {
                showUserOptions(user);
            }
        });

        rvSearchResults.setAdapter(userAdapter);

        // Lắng nghe sự thay đổi dữ liệu và cập nhật giao diện
        userViewModel.getSearchResults().observe(this, users -> {
            if (users != null) {
                userAdapter.setUserList(users);
                userAdapter.notifyDataSetChanged();
            }
        });

        // Xử lý tìm kiếm khi người dùng nhập từ khóa
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                String query = charSequence.toString();
                if (!query.isEmpty()) {
                    userViewModel.searchUsers(query);  // Tìm kiếm người dùng theo từ khóa
                } else {
                    // Gọi phương thức trong ViewModel để làm trống danh sách kết quả tìm kiếm
                    userViewModel.clearSearchResults();  // Cập nhật danh sách trống
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
    }

    private void showUserOptions(User user) {
        View view = LayoutInflater.from(this).inflate(R.layout.addfriend_bottomsheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        TextView tvAddFriend = view.findViewById(R.id.tvAddFriend);
        TextView tvViewProfile = view.findViewById(R.id.tvViewProfile);

        tvAddFriend.setOnClickListener(v -> {
            Toast.makeText(AddFriendActivity.this, "Thêm bạn bè: " + user.getFullName(), Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        tvViewProfile.setOnClickListener(v -> {
            Toast.makeText(AddFriendActivity.this, "Xem thông tin: " + user.getFullName(), Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }
}

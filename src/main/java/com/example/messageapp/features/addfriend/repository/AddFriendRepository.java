package com.example.messageapp.features.addfriend.repository;

import android.util.Log;

import com.example.messageapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFriendRepository {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersRef;
    private List<User> users = new ArrayList<>();
    private OnUsersLoadedListener listener;

    public AddFriendRepository(OnUsersLoadedListener listener) {
        this.listener = listener;
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("users");  // Đảm bảo rằng "users" là node trong Firebase của bạn
    }

    public interface OnUsersLoadedListener {
        void onUsersLoaded(List<User> userList);  // Gửi lại danh sách người dùng
        void onError(String error);  // Xử lý lỗi
    }

    public void fetchUsers() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();  // Xóa danh sách cũ
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);  // Lấy đối tượng User từ snapshot
                    if (user != null) {
                        users.add(user);  // Thêm người dùng vào danh sách
                    }
                }
                listener.onUsersLoaded(users);  // Trả về danh sách người dùng đã lấy
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AddFriendRepository", "Error fetching users", databaseError.toException());
                listener.onError("Lỗi khi lấy danh sách người dùng");  // Xử lý lỗi khi tải dữ liệu
            }
        });
    }

    public void searchUsers(String query, OnUsersLoadedListener listener) {
        List<User> filteredUsers = null; // Tìm kiếm logic của bạn (Firebase hoặc bộ lọc)
        if (filteredUsers != null) {
            listener.onUsersLoaded(filteredUsers);
        } else {
            listener.onError("No results found");
        }
    }
}

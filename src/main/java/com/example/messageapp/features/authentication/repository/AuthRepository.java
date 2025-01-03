package com.example.messageapp.features.authentication.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.messageapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> authStatus;

    public AuthRepository() {
        mAuth = FirebaseAuth.getInstance();
        authStatus = new MutableLiveData<>();
    }

    // Đăng ký người dùng
    public MutableLiveData<Boolean> register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công, có thể lưu thông tin người dùng vào Firestore nếu cần
                        FirebaseUser user = mAuth.getCurrentUser();
                        authStatus.setValue(true);  // Trả về trạng thái thành công
                    } else {
                        // Đăng ký thất bại
                        authStatus.setValue(false);
                    }
                });
        return authStatus;
    }

    // Đăng nhập người dùng
    public MutableLiveData<Boolean> login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        authStatus.setValue(true);  // Trả về trạng thái thành công
                    } else {
                        // Đăng nhập thất bại
                        authStatus.setValue(false);
                    }
                });
        return authStatus;
    }

    // Đăng xuất người dùng
    public void logout() {
        mAuth.signOut();
    }

    // Lấy thông tin người dùng hiện tại
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Kiểm tra trạng thái người dùng đã đăng nhập hay chưa
    public boolean isUserLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}

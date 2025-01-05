package com.example.messageapp.features.authentication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class AuthRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MutableLiveData<Boolean> authStatus;
    private MutableLiveData<Boolean> userProfileStatus;

    public AuthRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        authStatus = new MutableLiveData<>();
        userProfileStatus = new MutableLiveData<>();
    }

    // Đăng ký người dùng
    public MutableLiveData<Boolean> register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        authStatus.setValue(true);  // Đăng ký thành công
                    } else {
                        authStatus.setValue(false);  // Đăng ký thất bại
                    }
                });
        return authStatus;
    }

    // Đăng nhập người dùng
    public MutableLiveData<Boolean> login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        authStatus.setValue(true);  // Đăng nhập thành công
                    } else {
                        authStatus.setValue(false);  // Đăng nhập thất bại
                    }
                });
        return authStatus;
    }

    // Kiểm tra và tạo thông tin người dùng trong Firestore
    public MutableLiveData<Boolean> checkAndCreateUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullname = documentSnapshot.getString("fullname");
                            String dob = documentSnapshot.getString("dob");
                            String gender = documentSnapshot.getString("gender");

                            // Nếu thông tin chưa đầy đủ, tạo tài liệu mới
                            if (fullname == null || dob == null || gender == null) {
                                createEmptyUserProfile(userId, currentUser.getEmail());
                            } else {
                                userProfileStatus.setValue(true);  // Thông tin đầy đủ
                            }
                        } else {
                            // Tạo tài liệu người dùng mới nếu không tồn tại
                            createEmptyUserProfile(userId, currentUser.getEmail());
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error checking user info", e));

        }
        return userProfileStatus;
    }

    // Tạo tài liệu người dùng mới trong Firestore
    private void createEmptyUserProfile(String userId, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("fullname", null);
        user.put("dob", null);
        user.put("gender", null);
        user.put("status", "offline");

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    userProfileStatus.setValue(false);  // Chuyển đến màn hình nhập thông tin
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error creating user profile", e);
                    userProfileStatus.setValue(false);
                });
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

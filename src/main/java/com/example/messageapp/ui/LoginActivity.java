package com.example.messageapp.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.R;
import com.example.messageapp.databinding.ActivityLoginBinding;
import com.example.messageapp.features.authentication.AuthViewModel;
import com.example.messageapp.features.authentication.repository.AuthRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra trạng thái đăng nhập
        boolean isLoggedIn = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Người dùng đã đăng nhập, chuyển trực tiếp đến MessagesActivity
            Intent intent = new Intent(LoginActivity.this, MessagesActivity.class);
            startActivity(intent);
            finish(); // Đóng LoginActivity
            return;
        }

        // Khởi tạo Firebase
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo AuthRepository và AuthViewModel
        AuthRepository authRepository = new AuthRepository();
        AuthViewModel.AuthViewModelFactory factory = new AuthViewModel.AuthViewModelFactory(authRepository);
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        // Xử lý sự kiện đăng nhập
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            authViewModel.login(email, password).observe(this, success -> {
                if (success) {
                    String userId = firebaseAuth.getCurrentUser().getUid();

                    // Kiểm tra thông tin người dùng trong Firestore
                    checkUserProfile(userId);
                } else {
                    Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void checkUserProfile(String userId) {
        firestore.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Kiểm tra thông tin cá nhân đã đầy đủ chưa
                            boolean hasFullProfile = document.contains("fullName") &&
                                    document.contains("dob") &&
                                    document.contains("gender") &&
                                    document.contains("avatar");

                            if (hasFullProfile) {
                                // Thông tin đầy đủ, chuyển đến MessagesActivity
                                Intent intent = new Intent(LoginActivity.this, MessagesActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Thông tin chưa đầy đủ, chuyển đến CompleteProfileActivity
                                navigateToCompleteProfile();
                            }
                        } else {
                            // Document không tồn tại, chuyển đến CompleteProfileActivity
                            navigateToCompleteProfile();
                        }
                    } else {
                        Log.e("LoginActivity", "Lỗi khi kiểm tra Firestore: ", task.getException());
                        Toast.makeText(this, "Lỗi khi kiểm tra thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToCompleteProfile() {
        Intent intent = new Intent(LoginActivity.this, CompleteProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(
                LoginActivity.this,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        );
        startActivity(intent, options.toBundle());
    }
}

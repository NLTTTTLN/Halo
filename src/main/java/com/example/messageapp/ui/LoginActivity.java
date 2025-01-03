package com.example.messageapp.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.R;
import com.example.messageapp.databinding.ActivityLoginBinding;
import com.example.messageapp.features.authentication.AuthViewModel;
import com.example.messageapp.features.authentication.repository.AuthRepository;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create the AuthRepository and ViewModelFactory
        AuthRepository authRepository = new AuthRepository();
        AuthViewModel.AuthViewModelFactory factory = new AuthViewModel.AuthViewModelFactory(authRepository);

        // Use the factory to get the ViewModel
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            authViewModel.login(email, password).observe(this, success -> {
                if (success) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang màn hình chính
                } else {
                    Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void navigateToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        // Sử dụng ActivityOptions để thêm hiệu ứng chuyển màn hình
        ActivityOptions options = ActivityOptions.makeCustomAnimation(
                LoginActivity.this,
                R.anim.slide_in_left, // Hiệu ứng trượt từ phải
                R.anim.slide_out_left // Hiệu ứng trượt ra từ trái
        );
        startActivity(intent, options.toBundle());
    }
}


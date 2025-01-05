package com.example.messageapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.R;
import com.example.messageapp.databinding.ActivityRegisterBinding;
import com.example.messageapp.features.authentication.AuthViewModel;
import com.example.messageapp.features.authentication.repository.AuthRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo một instance của AuthRepository
        AuthRepository authRepository = new AuthRepository();

        // Sử dụng AuthViewModelFactory để khởi tạo AuthViewModel
        AuthViewModel.AuthViewModelFactory factory = new AuthViewModel.AuthViewModelFactory(authRepository);
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        binding.btnRegister.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String retypePassword = binding.etRetypePassword.getText().toString().trim();

            // Kiểm tra nếu mật khẩu và nhập lại mật khẩu khớp
            if (!password.equals(retypePassword)) {
                Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra nếu bất kỳ trường nào bị bỏ trống
            if (email.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.register(email, password).observe(this, success -> {
                if (success) {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    new android.os.Handler().postDelayed(() -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Đảm bảo màn hình đăng ký được đóng lại
                    }, 500);
                } else {
                    Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    @Override
    public void onBackPressed() {
        // Đảm bảo áp dụng hiệu ứng khi quay lại màn hình trước
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);  // Slide in from left, slide out to right
    }
}

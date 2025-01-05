package com.example.messageapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.completeprofile.WelcomeFragment;

public class CompleteProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        // Hiển thị bước đầu tiên (WelcomeFragment)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeFragment())
                    .commit();
        }
    }

    public void navigateToNextFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Thêm vào stack để hỗ trợ điều hướng
                .commit();
    }
}

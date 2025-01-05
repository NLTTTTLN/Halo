package com.example.messageapp.ui.completeprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.CompleteProfileActivity;

public class GenderFragment extends Fragment {

    private String selectedGender; // Biến lưu giới tính người dùng đã chọn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender, container, false);


        // Thiết lập sự kiện chọn giới tính (Nam/Nữ)
        ImageView ivMale = view.findViewById(R.id.ivMale);
        ImageView ivFemale = view.findViewById(R.id.ivFemale);

        // Xử lý khi chọn giới tính Nam
        ivMale.setOnClickListener(v -> {
            ivMale.setAlpha(1f); // Làm nổi bật Nam
            ivFemale.setAlpha(0.5f); // Giảm độ sáng Nữ
            selectedGender = "Nam"; // Lưu giới tính Nam
        });

        // Xử lý khi chọn giới tính Nữ
        ivFemale.setOnClickListener(v -> {
            ivFemale.setAlpha(1f); // Làm nổi bật Nữ
            ivMale.setAlpha(0.5f); // Giảm độ sáng Nam
            selectedGender = "Nữ"; // Lưu giới tính Nữ
        });

        // Thiết lập sự kiện cho nút Tiếp theo
        Button btnContinue = view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            if (selectedGender != null) {
                // Lưu giới tính vào Bundle
                Bundle args = getArguments();
                args.putString("gender", selectedGender);

                // Chuyển sang fragment Avatar
                AvatarFragment avatarFragment = new AvatarFragment();
                avatarFragment.setArguments(args);
                ((CompleteProfileActivity) getActivity()).navigateToNextFragment(avatarFragment);
            }
        });

        return view;
    }
}

package com.example.messageapp.ui.completeprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.MessagesActivity;

public class FinishFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        // Hiển thị thông báo "Yay xong rồi, chat chít thôi"
        view.findViewById(R.id.tvFinishMessage).setVisibility(View.VISIBLE);

        // Xử lý sự kiện khi người dùng nhấn nút "Let's go!"
        Button btnLetsGo = view.findViewById(R.id.btnLetsGo);
        btnLetsGo.setOnClickListener(v -> {
            // Chuyển đến MessagesActivity
            Intent intent = new Intent(getActivity(), MessagesActivity.class);
            startActivity(intent);
            getActivity().finish(); // Đóng activity hiện tại
        });

        return view;
    }
}

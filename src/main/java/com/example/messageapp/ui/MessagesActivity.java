package com.example.messageapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnMessages, btnSettings, btnContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cài đặt Adapter cho RecyclerView (Giả sử bạn có một adapter sẵn)
        // recyclerView.setAdapter(new MessagesAdapter(messages));

        // Lấy các button từ layout bottom_nav.xml
        btnMessages = findViewById(R.id.btnMessages);
        btnSettings = findViewById(R.id.btnSettings);
        btnContacts = findViewById(R.id.btnContacts);

        // Cài đặt sự kiện cho các button
        btnMessages.setOnClickListener(v -> {
            // Xử lý sự kiện khi người dùng nhấn vào nút Tin nhắn
            // Ví dụ: Hiển thị danh sách tin nhắn
            Toast.makeText(MessagesActivity.this, "Bạn đang ở trang Tin nhắn", Toast.LENGTH_SHORT).show();
        });

        btnContacts.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this, ContactsActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            // Xử lý sự kiện khi người dùng nhấn vào nút Cài đặt
            // Ví dụ: Chuyển đến màn hình Cài đặt
            Toast.makeText(MessagesActivity.this, "Chuyển đến màn hình Cài đặt", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        // Xử lý quay lại màn hình trước (nếu cần)
        super.onBackPressed();
    }
}

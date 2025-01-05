package com.example.messageapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;
import com.example.messageapp.features.contacts.adapter.ContactsAdapter;
import com.example.messageapp.features.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView rvContacts;
    private EditText etSearchContacts;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactList;
    private List<Contact> filteredList;
    private ImageView btnAddFriend;  // Khai báo btnAddFriend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Ánh xạ các thành phần giao diện
        rvContacts = findViewById(R.id.rvContacts);
        etSearchContacts = findViewById(R.id.etSearchContacts);
        btnAddFriend = findViewById(R.id.btnAddFriend);  // Khai báo btnAddFriend

        // Tạo danh sách liên hệ giả lập
        contactList = generateDummyContacts();
        filteredList = new ArrayList<>(contactList);

        // Cài đặt RecyclerView và Adapter
        contactsAdapter = new ContactsAdapter(filteredList);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactsAdapter);

        // Thêm tính năng tìm kiếm
        etSearchContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Sự kiện click vào btnAddFriend
        btnAddFriend.setOnClickListener(v -> {
            // Chuyển đến màn hình AddFriendActivity
            Intent intent = new Intent(ContactsActivity.this, AddFriendActivity.class);
            startActivity(intent);
        });
    }

    // Tìm kiếm liên hệ
    private void filterContacts(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(contactList);
        } else {
            for (Contact contact : contactList) {
                if (contact.getFullName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(contact);
                }
            }
        }
        contactsAdapter.notifyDataSetChanged();
    }

    // Tạo danh sách liên hệ giả lập
    private List<Contact> generateDummyContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Nguyễn Văn A", "a@example.com", "https://example.com/avatar/a.png"));
        contacts.add(new Contact("Trần Thị B", "b@example.com", "https://example.com/avatar/b.png"));
        contacts.add(new Contact("Phạm Văn C", "c@example.com", "https://example.com/avatar/c.png"));
        contacts.add(new Contact("Lê Thị D", "d@example.com", "https://example.com/avatar/d.png"));
        contacts.add(new Contact("Vũ Văn E", "e@example.com", "https://example.com/avatar/e.png"));
        return contacts;
    }
}

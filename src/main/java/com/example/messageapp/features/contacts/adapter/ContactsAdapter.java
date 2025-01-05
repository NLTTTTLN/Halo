package com.example.messageapp.features.contacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;
import com.example.messageapp.features.contacts.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactsAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.fullName.setText(contact.getFullName());

        // Hiển thị avatar
        if (contact.getAvatarUrl() != null && !contact.getAvatarUrl().isEmpty()) {
            Picasso.get().load(contact.getAvatarUrl()).into(holder.avatarImageView);
        } else {
            // Nếu không có URL, có thể hiển thị ảnh mặc định
            holder.avatarImageView.setImageResource(R.drawable.ic_default_avatar); // ảnh mặc định
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        ImageView avatarImageView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tvContactName);
            avatarImageView = itemView.findViewById(R.id.ivContactAvatar);
        }
    }
}

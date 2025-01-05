package com.example.messageapp.features.addfriend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;
import com.example.messageapp.model.User;

import java.util.List;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.UserViewHolder> {

    private List<User> userList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemLongClick(User user);
    }

    public AddFriendAdapter(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getFullName());
        holder.userEmail.setText(user.getEmail());

        // Set up long click listener
        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemLongClick(user);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tvUserName);
            userEmail = itemView.findViewById(R.id.tvUserEmail);
        }
    }
}

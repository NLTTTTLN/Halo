package com.example.messageapp.features.addfriend;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messageapp.model.User;
import com.example.messageapp.features.addfriend.repository.AddFriendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddFriendViewModel extends ViewModel {

    private AddFriendRepository userRepository;
    private MutableLiveData<List<User>> searchResults;  // Đảm bảo MutableLiveData

    public AddFriendViewModel() {
        userRepository = new AddFriendRepository(new AddFriendRepository.OnUsersLoadedListener() {
            @Override
            public void onUsersLoaded(List<User> userList) {
                searchResults.setValue(userList);  // Cập nhật kết quả tìm kiếm ban đầu
            }

            @Override
            public void onError(String error) {
                Log.e("AddFriendViewModel", "Error loading users: " + error);  // Ghi log lỗi
            }
        });
        searchResults = new MutableLiveData<>();
        userRepository.fetchUsers();  // Gọi phương thức fetchUsers() để tải dữ liệu người dùng
    }

    public LiveData<List<User>> getSearchResults() {
        return searchResults;
    }

    public void searchUsers(String query) {
        userRepository.searchUsers(query, new AddFriendRepository.OnUsersLoadedListener() {
            @Override
            public void onUsersLoaded(List<User> userList) {
                searchResults.setValue(userList);  // Cập nhật kết quả tìm kiếm
            }

            @Override
            public void onError(String error) {
                Log.e("AddFriendViewModel", "Search error: " + error);
            }
        });
    }

    // Phương thức để reset lại kết quả tìm kiếm khi query trống
    // Phương thức để reset kết quả tìm kiếm thành danh sách trống
    public void clearSearchResults() {
        searchResults.postValue(new ArrayList<>()); // Cập nhật kết quả tìm kiếm thành danh sách trống
    }

}

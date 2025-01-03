// AuthViewModel.java
package com.example.messageapp.features.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.features.authentication.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registerStatus = new MutableLiveData<>();

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Đăng nhập
    public LiveData<Boolean> login(String email, String password) {
        return authRepository.login(email, password);
    }

    // Đăng ký
    public LiveData<Boolean> register(String email, String password) {
        return authRepository.register(email, password);
    }

    // Factory để khởi tạo ViewModel với AuthRepository
    public static class AuthViewModelFactory implements ViewModelProvider.Factory {
        private final AuthRepository authRepository;

        public AuthViewModelFactory(AuthRepository authRepository) {
            this.authRepository = authRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AuthViewModel.class)) {
                return (T) new AuthViewModel(authRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

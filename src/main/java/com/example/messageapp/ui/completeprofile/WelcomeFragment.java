package com.example.messageapp.ui.completeprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.CompleteProfileActivity;

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        view.findViewById(R.id.btnContinue).setOnClickListener(v -> {
            // Chuyển đến FullNameFragment
            ((CompleteProfileActivity) getActivity()).navigateToNextFragment(new FullNameFragment());
        });

        return view;
    }
}

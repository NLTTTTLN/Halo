package com.example.messageapp.ui.completeprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.CompleteProfileActivity;

public class FullNameFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fullname, container, false);

        EditText etFullName = view.findViewById(R.id.etFullname);

        view.findViewById(R.id.btnContinue).setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();

            if (!fullName.isEmpty()) {
                // Lưu vào Bundle hoặc ViewModel
                Bundle args = new Bundle();
                args.putString("fullName", fullName);

                // Chuyển đến DoBFragment thay vì GenderFragment
                DoBFragment fragment = new DoBFragment();
                fragment.setArguments(args);

                // Gọi phương thức navigateToNextFragment để chuyển fragment
                ((CompleteProfileActivity) getActivity()).navigateToNextFragment(fragment);
            }
        });

        return view;
    }
}

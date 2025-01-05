package com.example.messageapp.ui.completeprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messageapp.R;
import com.example.messageapp.ui.CompleteProfileActivity;

import java.util.Calendar;

public class DoBFragment extends Fragment {

    private NumberPicker pickerDay, pickerMonth, pickerYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dob, container, false);

        pickerDay = view.findViewById(R.id.number_picker_day);
        pickerMonth = view.findViewById(R.id.number_picker_month);
        pickerYear = view.findViewById(R.id.number_picker_year);

        setupNumberPickers();

        view.findViewById(R.id.btn_continue).setOnClickListener(v -> {
            int day = pickerDay.getValue();
            int month = pickerMonth.getValue();
            int year = pickerYear.getValue();

            String dob = day + "/" + month + "/" + year;

            // Lưu thông tin ngày sinh và chuyển đến GenderFragment
            Bundle args = getArguments();
            args.putString("dob", dob);

            GenderFragment genderFragment = new GenderFragment();
            genderFragment.setArguments(args);

            ((CompleteProfileActivity) getActivity()).navigateToNextFragment(genderFragment);
        });

        return view;
    }

    private void setupNumberPickers() {
        // Day (1-31)
        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);
        pickerDay.setWrapSelectorWheel(true);

        // Month (1-12)
        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerMonth.setWrapSelectorWheel(true);

        // Year (currentYear - 100 to currentYear)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        pickerYear.setMinValue(currentYear - 100);
        pickerYear.setMaxValue(currentYear);
        pickerYear.setWrapSelectorWheel(true);
    }
}

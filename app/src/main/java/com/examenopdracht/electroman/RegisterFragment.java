package com.examenopdracht.electroman;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.examenopdracht.electroman.databinding.FragmentRegisterBinding;
import com.examenopdracht.electroman.ui.viewmodel.RegisterViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;

    private FragmentRegisterBinding viewDataBinding;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register,
                container,
                false);

        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //requireActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        initializeDataBinding();
        setupObservers();
    }

    private void initializeDataBinding() {
        viewDataBinding.setRegisterViewModel(registerViewModel);
        viewDataBinding.setLifecycleOwner(this);
    }

    private void setupObservers() {
        registerViewModel.getShowDatePicker().observe(getViewLifecycleOwner(), show -> {
            if (show) {
                showDatePickerDialog();
            }
        });

        registerViewModel.getNavigateBack().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }

    private void showDatePickerDialog() {
        LocalDate now = LocalDate.now();

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    registerViewModel.setDate(selectedDate);
                },
                now.getYear(),
                now.getMonthValue() - 1,
                now.getDayOfMonth());

        datePickerDialog.show();
    }
}
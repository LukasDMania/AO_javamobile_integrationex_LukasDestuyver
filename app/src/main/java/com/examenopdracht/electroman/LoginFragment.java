package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;
import com.examenopdracht.electroman.databinding.FragmentLoginBinding;
import com.examenopdracht.electroman.ui.viewmodel.LoginViewModel;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;
import com.examenopdracht.electroman.util.MockData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private SharedViewModel sharedViewModel;
    private FragmentLoginBinding viewDataBinding;

    private MockData mockData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Add mock users
        UserRepository userRepository = new UserRepository(requireActivity().getApplication());
        WorkOrderRepository workOrderRepository = new WorkOrderRepository(requireActivity().getApplication());
        userRepository.deleteAllUsers();
        workOrderRepository.deleteAllWorkOrders();

        mockData = new MockData(getActivity().getApplication());

        ElectromanDatabase.dbWriteExecutor.execute(() -> {
            mockData.insertMockUsers(10);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);

        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewDataBinding.setLoginViewModel(loginViewModel);
        viewDataBinding.setLifecycleOwner(this);

        loginViewModel.getNavigateToMainFragment().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_loginFragment_to_mainFragment);
                loginViewModel.getNavigateToCreateUserFragment().setValue(false);
            }
        });

        loginViewModel.getNavigateToCreateUserFragment().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                loginViewModel.getNavigateToMainFragment().setValue(false);
            }
        });

        loginViewModel.getLoggedInUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                sharedViewModel.setCurrentUser(user);
            }
        });
    }
}
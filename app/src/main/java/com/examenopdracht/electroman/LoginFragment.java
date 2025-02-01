package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.databinding.FragmentLoginBinding;
import com.examenopdracht.electroman.ui.viewmodel.LoginViewModel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding viewDataBinding;

    private final List<User> mockUsers = Arrays.asList(
            new User("John", "Doe", "admin", "admin", LocalDate.of(1990, 1, 1), "New York", "10001", "5th Avenue", "101", null),
            new User("Jane", "Smith", "janesmith", "securePass!", LocalDate.of(1985, 5, 15), "Los Angeles", "90001", "Sunset Blvd", "200", "A"),
            new User("Alice", "Johnson", "alicej", "alice1234", LocalDate.of(1992, 3, 20), "San Francisco", "94103", "Market St", "300", null),
            new User("Bob", "Williams", "bobw", "b0bSecure", LocalDate.of(1988, 12, 11), "Chicago", "60601", "Michigan Ave", "400", "B"),
            new User("Charlie", "Brown", "charlieb", "ch4rlieP@ss", LocalDate.of(1995, 7, 25), "Houston", "77001", "Main St", "500", null)
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);



        // Add mock users
        UserRepository userRepository = new UserRepository(requireActivity().getApplication());
        //userRepository.deleteAllUsers();
        ElectromanDatabase.dbWriteExecutor.execute(() -> {
            for (User user : mockUsers) {
                if (userRepository.getUserByUserName(user.getUserName()) == null) {
                    Log.d("LoginFragment", "Inserting user: " + user.getUserName() + " " + user.getPassword());
                    userRepository.insertUser(user);
                }
            }
        });

        ElectromanDatabase.dbWriteExecutor.execute(() -> {
            List<User> users = userRepository.getAllUsers();
            for (User user : users) {
                Log.d("LoginFragment", "User: " + user.getId() +  user.getUserName() + " " + user.getPassword());
            }
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
    }
}
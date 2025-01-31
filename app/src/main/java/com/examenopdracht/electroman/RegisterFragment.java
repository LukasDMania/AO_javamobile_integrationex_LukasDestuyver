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
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.databinding.FragmentLoginBinding;
import com.examenopdracht.electroman.ui.viewmodel.LoginViewModel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RegisterFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}
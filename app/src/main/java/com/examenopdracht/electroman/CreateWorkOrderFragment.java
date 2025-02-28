package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.examenopdracht.electroman.databinding.FragmentCreateWorkOrderBinding;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;
import com.examenopdracht.electroman.ui.viewmodel.WorkOrderCreateViewModel;

public class CreateWorkOrderFragment extends Fragment {
    private WorkOrderCreateViewModel workOrderCreateViewModel;
    private SharedViewModel sharedViewModel;
    private FragmentCreateWorkOrderBinding viewDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workOrderCreateViewModel = new ViewModelProvider(this).get(WorkOrderCreateViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_work_order,
                container,
                false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setWorkOrderCreateViewModel(workOrderCreateViewModel);
        viewDataBinding.setLifecycleOwner(this);

        setupObservers();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(viewDataBinding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Work Order Create");
        setupMenu();
    }

    private void setupObservers(){
        // TODO: implement
    }

    private void setupMenu(){
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.work_order_detail_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.work_order_create_action_save) {
                    //TODO: implement
                    return true;
                } else if (id == R.id.work_order_create_action_cancel) {
                    getActivity().getOnBackPressedDispatcher().onBackPressed();
                    return true;
                }

                return false;
            }
        };

        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }
}
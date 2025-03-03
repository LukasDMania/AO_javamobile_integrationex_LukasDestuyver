package com.examenopdracht.electroman;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.examenopdracht.electroman.databinding.FragmentCreateWorkOrderBinding;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;
import com.examenopdracht.electroman.ui.viewmodel.WorkOrderCreateViewModel;

import java.util.Objects;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeDataBinding();
        setupToolbar();
        setupMenu();
        setupObservers();
    }

    private void initializeDataBinding() {
        viewDataBinding.setWorkOrderCreateViewModel(workOrderCreateViewModel);
        viewDataBinding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(viewDataBinding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Work Order Create");
    }

    private void setupObservers() {
        // TODO: implement
    }

    private void setupMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.work_order_create_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.work_order_create_action_save) {
                    if (workOrderCreateViewModel.validInputWorkOrder()) {
                        workOrderCreateViewModel.doesWorkOrderAlreadyExistAndInsert(Objects.requireNonNull(sharedViewModel.getCurrentUser().getValue()).getId());

                        workOrderCreateViewModel.getInsertSuccess().observe(getViewLifecycleOwner(), success -> {
                            if (success) {
                                NavController navController = NavHostFragment.findNavController(CreateWorkOrderFragment.this);
                                if (navController.getCurrentDestination().getId() != R.id.mainFragment) {
                                    navController.navigate(R.id.action_createWorkOrderFragment_to_mainFragment);
                                }
                            } else {
                                viewDataBinding.errorMessageTextView.setVisibility(View.VISIBLE);
                            }
                        });

                    } else {
                        Log.d("CreateWorkOrderFragment", "setupMenu: " + workOrderCreateViewModel.getErrorMessage().getValue());
                        viewDataBinding.errorMessageTextView.setVisibility(View.VISIBLE);
                    }
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
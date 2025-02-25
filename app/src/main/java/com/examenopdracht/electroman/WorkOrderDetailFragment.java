package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.databinding.FragmentDetailBinding;
import com.examenopdracht.electroman.ui.viewmodel.RegisterViewModel;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;
import com.examenopdracht.electroman.ui.viewmodel.WorkOrderDetailViewModel;
public class WorkOrderDetailFragment extends Fragment {
    private WorkOrderDetailViewModel workOrderDetailViewModel;
    private SharedViewModel sharedViewModel;
    private FragmentDetailBinding viewDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workOrderDetailViewModel = new ViewModelProvider(this).get(WorkOrderDetailViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detail,
                container,
                false);

        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setWorkOrderDetailViewModel(workOrderDetailViewModel);
        viewDataBinding.setLifecycleOwner(this);

        setupObservers();
    }

    private void setupObservers(){
        //TODO:
        sharedViewModel.getSelectedWorkOrder().observe(getViewLifecycleOwner(), workOrder -> {
            if (workOrder != null) {
                workOrderDetailViewModel.setWorkOrderDetail(workOrder);
            }
        });
    }

}
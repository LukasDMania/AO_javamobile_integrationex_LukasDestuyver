package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
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

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.databinding.FragmentDetailBinding;
import com.examenopdracht.electroman.ui.viewmodel.MainFragmentViewModel;
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

        ((AppCompatActivity) requireActivity()).setSupportActionBar(viewDataBinding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Work Order Details");

        setupMenu();
    }

    private void setupMenu(){
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();

                WorkOrder workOrder = sharedViewModel.getSelectedWorkOrder().getValue();
                if (workOrder != null && workOrder.isProcessed()) {
                    menuInflater.inflate(R.menu.work_order_detail_processed_menu, menu);
                } else {
                    menuInflater.inflate(R.menu.work_order_detail_unprocessed_menu, menu);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.work_order_detail_action_save) {
                    if (workOrderDetailViewModel.validRepairInfo()){
                        Log.d("WorkOrderDetailFragment", "Saving work order details");
                        workOrderDetailViewModel.updateWorkOrder();
                        NavController navController = NavHostFragment.findNavController(WorkOrderDetailFragment.this);
                        navController.navigate(R.id.action_workOrderDetailFragment_to_mainFragment);
                        return true;
                    } else {
                        Log.d("ERROR_MESSAGE", "Showing error message");
                        viewDataBinding.tvErrorMessage.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> {
                            viewDataBinding.tvErrorMessage.animate()
                                    .alpha(0f)
                                    .setDuration(800)
                                    .withEndAction(() -> viewDataBinding.tvErrorMessage.setVisibility(View.GONE));  // Set visibility to GONE after fading out
                        }, 3000);

                        Log.d("ERROR_MESSAGE", "Error message: " + workOrderDetailViewModel.getErrorMessage().getValue());
                        Log.d("ERROR_MESSAGE", "Error message visibility: " + viewDataBinding.tvErrorMessage.getVisibility());
                    }
                } else if (id == R.id.work_order_detail_action_cancel) {
                    getActivity().getOnBackPressedDispatcher().onBackPressed();
                    return true;
                } else if (id == R.id.work_order_detail_action_reopen) {
                    Log.d("WorkOrderDetailFragment", "Reopening work order");
                    workOrderDetailViewModel.reopenWorkOrder();
                    viewDataBinding.etRepairInfo.setEnabled(true);
                    requireActivity().invalidateOptionsMenu();
                }

                return false;
            }
        };

        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private void setupObservers(){
        //TODO:
        sharedViewModel.getSelectedWorkOrder().observe(getViewLifecycleOwner(), workOrder -> {
            if (workOrder != null) {
                workOrderDetailViewModel.setWorkOrderDetail(workOrder);

                if (workOrder.isProcessed()) {
                    viewDataBinding.etRepairInfo.setEnabled(false);
                } else {
                    viewDataBinding.etRepairInfo.setEnabled(true);
                }
            }
        });
    }

}
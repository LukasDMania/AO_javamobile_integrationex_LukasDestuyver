package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.databinding.FragmentMainBinding;
import com.examenopdracht.electroman.ui.viewmodel.MainFragmentViewModel;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;
import com.examenopdracht.electroman.util.MockData;

import java.util.List;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel;
    private SharedViewModel sharedViewModel;
    private WorkOrderAdapter workOrderAdapter;
    private RecyclerView workOrderRecyclerView;
    private Toolbar toolbar;
    private FragmentMainBinding viewDataBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main,
                container,
                false);

        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewDataBinding.setMainFragmentViewModel(mainFragmentViewModel);
        viewDataBinding.setLifecycleOwner(this);

        initializeViews(view);
        setupToolbar();
        setupObservers();
        setupRecyclerView();
    }

    private void initializeViews(View view) {
        workOrderRecyclerView = view.findViewById(R.id.workOrdersRecyclerView);
        Log.d("MainFragment", "RecyclerView reference: " + (workOrderRecyclerView != null ? "found" : "null"));
        toolbar = view.findViewById(R.id.toolbar);
    }
    private void setupToolbar(){
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        //toolbar.inflateMenu(R.menu.);
        toolbar.setOnMenuItemClickListener(this::handleToolbarMenuClick);
    }
    private boolean handleToolbarMenuClick(MenuItem item){
        return false;
    }

    private void setupRecyclerView() {
        workOrderAdapter = new WorkOrderAdapter(requireContext());
        workOrderAdapter.setWorkOrders(mainFragmentViewModel.getWorkOrders().getValue());

        // Click listener for when you click on a row
        workOrderAdapter.setOnWorkOrderClickListener(workOrder -> {
            Log.d("MainFragment", "WorkOrder clicked: " + workOrder.getCustomerName());
            mainFragmentViewModel.getNavigateToWorkOrderDetail().setValue(true);
            sharedViewModel.setSelectedWorkOrder(workOrder);
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        workOrderRecyclerView.setLayoutManager(layoutManager);
        workOrderRecyclerView.setAdapter(workOrderAdapter);

        // Add devider line between rows
        workOrderRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), layoutManager.getOrientation()));


        RecyclerView.Adapter adapter = workOrderRecyclerView.getAdapter();
        if (adapter != null) {
            Log.d("MainFragment", "Adapter is set: " + adapter.getClass().getSimpleName());
        } else {
            Log.d("MainFragment", "Adapter is null");
        }

    }

    private void setupObservers(){
        sharedViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                mainFragmentViewModel.setCurrentUser(user);
                mainFragmentViewModel.loadWorkOrders();
            }
        });


        mainFragmentViewModel.getWorkOrders().observe(getViewLifecycleOwner(), workOrders -> {
            Log.d("MainFragment", "Observed workOrders: " + (workOrders != null ? workOrders.size() : "null"));
            workOrderAdapter.setWorkOrders(workOrders);
        });

        mainFragmentViewModel.getNavigateToWorkOrderDetail().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                //TODO: Navigate to work order detail fragment
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_mainFragment_to_workOrderDetailFragment);
                mainFragmentViewModel.getNavigateToWorkOrderDetail().setValue(false);
            }
        });
    }
}
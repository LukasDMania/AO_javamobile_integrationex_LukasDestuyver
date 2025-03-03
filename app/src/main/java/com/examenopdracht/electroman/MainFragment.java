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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.databinding.FragmentMainBinding;
import com.examenopdracht.electroman.ui.viewmodel.MainFragmentViewModel;
import com.examenopdracht.electroman.ui.viewmodel.SharedViewModel;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel;
    private SharedViewModel sharedViewModel;

    private FragmentMainBinding viewDataBinding;
    private RecyclerView workOrderRecyclerView;
    private WorkOrderAdapter workOrderAdapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeDataBinding();
        initializeViews(view);
        setupToolbar();
        setupMenu();
        setupObservers();
        setupRecyclerView();
    }

    private void initializeDataBinding() {
        viewDataBinding.setMainFragmentViewModel(mainFragmentViewModel);
        viewDataBinding.setLifecycleOwner(this);
    }

    private void initializeViews(View view) {
        workOrderRecyclerView = view.findViewById(R.id.workOrdersRecyclerView);
        Log.d("MainFragment", "RecyclerView reference: " + (workOrderRecyclerView != null ? "found" : "null"));
    }

    private void setupToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(viewDataBinding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Overview");
    }

    private void setupMenu() {
        Log.d("MainFragment", "Setting up menu");
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_fragment_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.main_fragment_menu_add) {
                    NavController navController = NavHostFragment.findNavController(MainFragment.this);
                    navController.navigate(R.id.action_mainFragment_to_createWorkOrderFragment);
                    return true;
                }
                return false;
            }
        };

        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private void setupObservers() {
        sharedViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                mainFragmentViewModel.setCurrentUser(user);
                mainFragmentViewModel.loadWorkOrders();
            }
        });

        mainFragmentViewModel.getWorkOrders().observe(getViewLifecycleOwner(), workOrders -> {
            Log.d("MainFragment", "Observed workOrders: " + (workOrders != null ? workOrders.size() : "null"));
            for (WorkOrder workOrder : workOrders) {
                Log.d("MainFragment", "WorkOrder: " + workOrder.toString());
            }
            workOrderAdapter.setWorkOrders(workOrders);
        });

        mainFragmentViewModel.getNavigateToWorkOrderDetail().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_mainFragment_to_workOrderDetailFragment);
                mainFragmentViewModel.getNavigateToWorkOrderDetail().setValue(false);
            }
        });
    }

    private void setupRecyclerView() {
        workOrderAdapter = new WorkOrderAdapter(requireContext());
        workOrderAdapter.setWorkOrders(mainFragmentViewModel.getWorkOrders().getValue());

        workOrderAdapter.setOnWorkOrderClickListener(workOrder -> {
            Log.d("MainFragment", "WorkOrder clicked: " + workOrder.getCustomerName());
            mainFragmentViewModel.getNavigateToWorkOrderDetail().setValue(true);
            sharedViewModel.setSelectedWorkOrder(workOrder);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        workOrderRecyclerView.setLayoutManager(layoutManager);
        workOrderRecyclerView.setAdapter(workOrderAdapter);

        // Add dividr line between rows
        workOrderRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), layoutManager.getOrientation()));

        RecyclerView.Adapter adapter = workOrderRecyclerView.getAdapter();
        if (adapter != null) {
            Log.d("MainFragment", "Adapter is set: " + adapter.getClass().getSimpleName());
        } else {
            Log.d("MainFragment", "Adapter is null");
        }
    }
}
package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.ui.viewmodel.MainFragmentViewModel;
import com.examenopdracht.electroman.util.MockData;

import java.util.List;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel;
    private WorkOrderAdapter workOrderAdapter;
    private RecyclerView workOrderRecyclerView;
    private TextView userFullName;
    private Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MainFragment", "onCreateView - inflating layout");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("MainFragment", "onCreateView - layout inflated");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void initializeViews(View view) {
        workOrderRecyclerView = view.findViewById(R.id.workOrdersRecyclerView);
        Log.d("MainFragment", "RecyclerView reference: " + (workOrderRecyclerView != null ? "found" : "null"));
        userFullName = view.findViewById(R.id.userFullName);
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
        List<WorkOrder> mockData = MockData.getMockWorkOrdersForUserId(31L);
        workOrderAdapter.setWorkOrders(mockData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        workOrderRecyclerView.setLayoutManager(layoutManager);
        workOrderRecyclerView.setAdapter(workOrderAdapter);
        RecyclerView.Adapter adapter = workOrderRecyclerView.getAdapter();
        if (adapter != null) {
            Log.d("MainFragment", "Adapter is set: " + adapter.getClass().getSimpleName());
        } else {
            Log.d("MainFragment", "Adapter is null");
        }

    }

    private void observeViewModel(){
        mainFragmentViewModel.getWorkOrders().observe(getViewLifecycleOwner(), workOrders -> {
            Log.d("MainFragment", "Observed workOrders: " + (workOrders != null ? workOrders.size() : "null"));
            workOrderAdapter.setWorkOrders(workOrders);
        });

    }
}
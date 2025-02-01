package com.examenopdracht.electroman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examenopdracht.electroman.ui.viewmodel.MainFragmentViewModel;

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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void initializeViews(View view){
        workOrderRecyclerView = view.findViewById(R.id.workOrdersRecyclerView);
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
        workOrderAdapter = new WorkOrderAdapter();
        workOrderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        workOrderRecyclerView.setAdapter(workOrderAdapter);
    }

    private void observeViewModel(){
        mainFragmentViewModel.getWorkOrders().observe(getViewLifecycleOwner(), workOrders -> {
            workOrderAdapter.setWorkOrders(workOrders);
            workOrderAdapter.notifyDataSetChanged();
        });
    }
}
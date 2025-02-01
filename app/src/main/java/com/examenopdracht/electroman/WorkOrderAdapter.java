package com.examenopdracht.electroman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.ArrayList;
import java.util.List;

class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.WorkOrderViewHolder> {
    private List<WorkOrder> workOrders = new ArrayList<>();
    private OnWorkOrderClickListener listener;

    public interface OnWorkOrderClickListener {
        void onWorkOrderClick(WorkOrder workOrder);
    }

    public void setOnWorkOrderClickListener(OnWorkOrderClickListener listener) {
        this.listener = listener;
    }

    public void setWorkOrders(List<WorkOrder> newWorkOrders) {
        this.workOrders = newWorkOrders == null ? new ArrayList<>() : new ArrayList<>(newWorkOrders);
    }

    @NonNull
    @Override
    public WorkOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_order, parent, false);
        return new WorkOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOrderViewHolder holder, int position) {
        WorkOrder workOrder = workOrders.get(position);
        holder.bind(workOrder);
    }

    @Override
    public int getItemCount() {
        return workOrders.size();
    }

    class WorkOrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityText;
        private final TextView deviceText;
        private final TextView problemText;
        private final TextView customerText;
        private final CheckBox processedCheckBox;

        public WorkOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cityText = itemView.findViewById(R.id.cityText);
            deviceText = itemView.findViewById(R.id.deviceText);
            problemText = itemView.findViewById(R.id.problemText);
            customerText = itemView.findViewById(R.id.customerText);
            processedCheckBox = itemView.findViewById(R.id.processedCheckBox);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onWorkOrderClick(workOrders.get(position));
                }
            });
        }

        public void bind(WorkOrder workOrder) {
            cityText.setText(workOrder.getCity());
            deviceText.setText(workOrder.getDevice());
            problemText.setText(workOrder.getProblemCode());
            customerText.setText(workOrder.getCustomerName());
            processedCheckBox.setChecked(workOrder.isProcessed());
        }
    }
}
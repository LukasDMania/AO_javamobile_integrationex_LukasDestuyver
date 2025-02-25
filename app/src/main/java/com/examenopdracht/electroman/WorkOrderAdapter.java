package com.examenopdracht.electroman;

import android.content.Context;
import android.util.Log;
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

class WorkOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ROW = 1;

    private List<WorkOrder> workOrders = new ArrayList<>();
    private OnWorkOrderClickListener listener;


    private final LayoutInflater mInflater;

    public WorkOrderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public interface OnWorkOrderClickListener {
        void onWorkOrderClick(WorkOrder workOrder);
    }

    public void setOnWorkOrderClickListener(OnWorkOrderClickListener listener) {
        this.listener = listener;
    }

    public void setWorkOrders(List<WorkOrder> newWorkOrders) {
        this.workOrders = newWorkOrders == null ? new ArrayList<>() : new ArrayList<>(newWorkOrders);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ROW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row, parent, false);
            return new WorkOrderViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind();
        } else if (holder instanceof WorkOrderViewHolder) {
            WorkOrder workOrder = workOrders.get(position - 1); // Adjust for header
            ((WorkOrderViewHolder) holder).bind(workOrder);
        }
    }

    @Override
    public int getItemCount() {
        return workOrders.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView column1, column2, column3, column4, column5;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            column1 = itemView.findViewById(R.id.cityHeader);
            column2 = itemView.findViewById(R.id.deviceHeader);
            column3 = itemView.findViewById(R.id.problemHeader);
            column4 = itemView.findViewById(R.id.customerHeader);
            column5 = itemView.findViewById(R.id.processedHeader);
        }

        public void bind() {
            column1.setText("City");
            column2.setText("Device");
            column3.setText("Problem");
            column4.setText("Customer");
            column5.setText("Processed");
        }
    }

    class WorkOrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityText;
        private final TextView deviceText;
        private final TextView problemText;
        private final TextView customerText;
        private final CheckBox processedCheckBox;

        public WorkOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cityText = itemView.findViewById(R.id.cityRow);
            deviceText = itemView.findViewById(R.id.deviceRow);
            problemText = itemView.findViewById(R.id.problemRow);
            customerText = itemView.findViewById(R.id.customerRow);
            processedCheckBox = itemView.findViewById(R.id.processedRow);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    position = position - 1;
                }

                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onWorkOrderClick(workOrders.get(position));
                }
            });
        }

        public void bind(WorkOrder workOrder) {
            Log.d("WorkOrderAdapter", "Binding WorkOrder: " + workOrder.toString());
            cityText.setText(workOrder.getCity());
            deviceText.setText(workOrder.getDevice());
            problemText.setText(workOrder.getProblemCode());
            customerText.setText(workOrder.getCustomerName());
            processedCheckBox.setChecked(workOrder.isProcessed());
        }

    }
}
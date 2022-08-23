package com.example.unipolimovilapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipolimovilapp.R;
import com.example.unipolimovilapp.model.departmentInfo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class departmentInfoAdapter extends FirestoreRecyclerAdapter<departmentInfo, departmentInfoAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public departmentInfoAdapter(@NonNull FirestoreRecyclerOptions<departmentInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull departmentInfo departmentInfo) {
        //en esta clase se le asigna el valor obtenido de la base de datos a los items
        viewHolder.name.setText(departmentInfo.getUserName());
        viewHolder.jobtitle.setText(departmentInfo.getJobTitle());
        viewHolder.workinghours.setText(departmentInfo.getWorkingHours());
        viewHolder.email.setText(departmentInfo.getEmail());
        viewHolder.phone.setText(departmentInfo.getPhone());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_department_info_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //en esta clase se obtienen los objetos del item dentro del recyclerview que contendran la informacion
        TextView name, jobtitle, workinghours, email, phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.responsable_name);
            jobtitle = itemView.findViewById(R.id.responsable_jobtitle);
            workinghours = itemView.findViewById(R.id.responsable_workinghours);
            email = itemView.findViewById(R.id.responsable_email);
            phone = itemView.findViewById(R.id.responsable_phone);
        }
    }
}

package com.example.unipolimovilapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipolimovilapp.DepartmentActivity;
import com.example.unipolimovilapp.R;
import com.example.unipolimovilapp.model.department;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class departmentAdapter extends FirestoreRecyclerAdapter<department,departmentAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param mContext
     */
    Activity mActivity;
    public departmentAdapter(@NonNull FirestoreRecyclerOptions<department> options, Activity mContext) {
        super(options);
        mActivity = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull department department) {
        //en esta clase se le asigna el valor obtenido de la base de datos a los items

        //se guarda el id de cada view_department_single que corresponde a Departments
        DocumentSnapshot documentSnapshot = getSnapshots()
                .getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.Department.setText(department.getName());

        //al hacer click en el view_department_single se llama al activity_Department con el id del view seleccionado
        viewHolder.Department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mActivity, DepartmentActivity.class);
                i.putExtra("id_department", id);
                mActivity.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_department_single, parent, false);
        return  new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //en esta clase se obtienen los objetos del item dentro del recyclerview que contendran la informacion
        Button Department;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Department = itemView.findViewById(R.id.btn_department);
        }
    }
}

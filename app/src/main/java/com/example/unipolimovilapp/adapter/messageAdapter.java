package com.example.unipolimovilapp.adapter;

import android.content.Context;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipolimovilapp.CommunityNewsFragment;
import com.example.unipolimovilapp.R;
import com.example.unipolimovilapp.model.communitynew;
import com.example.unipolimovilapp.model.message;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class messageAdapter extends FirestoreRecyclerAdapter<message, messageAdapter.ViewHolder> {

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public messageAdapter(@NonNull FirestoreRecyclerOptions<message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull message message) {
        //en esta clase se le asigna el valor obtenido de la base de datos a los items
        DocumentSnapshot documentSnapshot = getSnapshots()
                .getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //se llama a la coleccion UniversityStaff para obtener el nombre del usuario que envio el mensaje
        mFirestore.collection("UniversityStaff")
                .document(message.getUser())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        long longSeconds= message.getCreatedAt().getSeconds() *1000L;
                        Date dateCreatedAt = new Date(longSeconds);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd/MM/yyyy", Locale.getDefault());
                        viewHolder.date.setText(sdf.format(dateCreatedAt));
                        viewHolder.title.setText(message.getTitle());
                        viewHolder.message.setText(message.getMessage());
                        viewHolder.user.setText(document.getString("userName"));

                        viewHolder.progressBar.setVisibility(View.GONE);

                    }
                });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_single, parent, false);
        return  new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //en esta clase se obtienen los objetos del item dentro del recyclerview que contendran la informacion
        TextView title,message,date,user;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.PB_message);
            progressBar.setVisibility(View.VISIBLE);

            title = itemView.findViewById(R.id.message_title);
            message = itemView.findViewById(R.id.message_body);
            date = itemView.findViewById(R.id.message_time);
            user = itemView.findViewById(R.id.message_user);
        }
    }
}

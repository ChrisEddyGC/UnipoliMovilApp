package com.example.unipolimovilapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ablanco.zoomy.Zoomy;
import com.example.unipolimovilapp.R;
import com.example.unipolimovilapp.model.Agenda;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class agendaAdapter extends FirestoreRecyclerAdapter<Agenda,agendaAdapter.ViewHolder> {

    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public agendaAdapter(@NonNull FirestoreRecyclerOptions<Agenda> options,Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Agenda agenda) {
        //en esta clase se le asigna el valor obtenido de la base de datos a los items
        Picasso.get()
                .load(agenda.getImageURL())
                .placeholder(R.drawable.unipolilogo)
                .into(viewHolder.image);

        Zoomy.Builder builder = new Zoomy.Builder(activity)
                .target(viewHolder.image)
                .enableImmersiveMode(false)
                .animateZooming(false);
        builder.register();
        viewHolder.progressBar.setVisibility(View.GONE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_agenda_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //en esta clase se obtienen los objetos del view_agenda_single
        ProgressBar progressBar;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.PB_calendar);
            progressBar.setVisibility(View.VISIBLE);
            image = itemView.findViewById(R.id.AgendaImage);
        }
    }
}

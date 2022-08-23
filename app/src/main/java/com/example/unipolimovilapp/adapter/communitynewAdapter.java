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
import com.example.unipolimovilapp.model.communitynew;
import com.example.unipolimovilapp.model.message;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class communitynewAdapter extends FirestoreRecyclerAdapter<communitynew, communitynewAdapter.ViewHolder> {

    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public communitynewAdapter(@NonNull FirestoreRecyclerOptions<communitynew> options, Activity activity) {
        super(options);
        this.activity = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull communitynew communitynew) {
        //en esta clase se le asigna el valor obtenido de la base de datos a los items
        viewHolder.message.setText(communitynew.getMessage());

        Picasso.get()
                .load(communitynew.getImageURL())
                .placeholder(R.drawable.unipolilogo)
                .into(viewHolder.image);
        //io.github.imablanco:zoomy:1.0.0
        Zoomy.Builder builder = new Zoomy.Builder(activity)
                .target(viewHolder.image)
                .enableImmersiveMode(false)
                .animateZooming(false);
        builder.register();

        viewHolder.progressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public communitynewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_communitynew_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //en esta clase se obtienen los objetos del view_communitynew_single
        TextView message;
        ImageView image;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.PB_communitynew);
            progressBar.setVisibility(View.VISIBLE);

            message = itemView.findViewById(R.id.communitynew_message);
            image = itemView.findViewById(R.id.communitynew_image);
        }
    }
}

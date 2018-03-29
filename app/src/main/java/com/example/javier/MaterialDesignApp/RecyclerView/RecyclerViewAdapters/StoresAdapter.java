package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.MaterialDesignApp.R;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Stores;

import java.util.ArrayList;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder>{

    private ArrayList<Stores> stores;
    Context context;
    static boolean logo=false;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
        }
    };


    // Adapter's Constructor
    public StoresAdapter(Context context, ArrayList<Stores> stores) {
        this.stores = stores;
        this.context = context;
    }

    // Create new views. This is invoked by the layout manager.
    @Override
    public StoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Set strings to the views
        final TextView textViewTitle = (TextView) holder.view.findViewById(R.id.textViewItemName);
        final TextView textViewContent = (TextView) holder.view.findViewById(R.id.textViewItemBirthday);
        final ImageView imageViewImage = (ImageView) holder.view.findViewById(R.id.imageViewImage);
        textViewTitle.setText(stores.get(position).getStr_name());
        if (!logo){
            imageViewImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.beautylogo));
            logo=true;
        }else {
            imageViewImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.beautylogoo));
            logo=false;
        }
        textViewContent.setText(stores.get(position).getStr_op_time()+":00-"+stores.get(position).getStr_cl_time()+":00");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stores.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}

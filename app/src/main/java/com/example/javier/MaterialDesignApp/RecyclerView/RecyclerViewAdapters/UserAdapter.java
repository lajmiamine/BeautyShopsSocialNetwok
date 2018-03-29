package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.MaterialDesignApp.DetailActivity;
import com.example.javier.MaterialDesignApp.MainActivity;
import com.example.javier.MaterialDesignApp.ProfileActivity;
import com.example.javier.MaterialDesignApp.R;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Pos;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.User;
import com.example.javier.MaterialDesignApp.Utils.PicassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private ArrayList<User> users;
    ArrayList<Pos> poses=new ArrayList<>();
    Context context;
    int position;
    View v;


    // Adapter's Constructor
    public UserAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        this.context = context;
    }

    // Create new views. This is invoked by the layout manager.
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Set strings to the views
        final TextView textViewName = (TextView) holder.view.findViewById(R.id.textViewItemName);
        final TextView textViewBirthday = (TextView) holder.view.findViewById(R.id.textViewItemBirthday);
        final ImageView imageViewImage = (ImageView) holder.view.findViewById(R.id.imageViewImage);
        textViewName.setText(users.get(position).getUsr_name());
        textViewBirthday.setText(users.get(position).getUsr_bday_day()+"-"+users.get(position).getUsr_bday_month()+"-"+users.get(position).getUsr_bday_year()+"");
        imageViewImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_contact_icon));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProfileActivity.class);
                Bundle b = new Bundle();
                b.putInt("getUsr_id",users.get(position).getUsr_id());
                b.putString("str_name",users.get(position).getUsr_name());
                b.putInt("getUsr_bday_day",users.get(position).getUsr_bday_day());
                b.putInt("getUsr_bday_month",users.get(position).getUsr_bday_month());
                b.putInt("getUsr_bday_year",users.get(position).getUsr_bday_year());
                Log.v("str_name",users.get(position).getUsr_name());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
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

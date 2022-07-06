package com.adiljutt.inscurancecar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.adiljutt.inscurancecar.MainActivity;
import com.adiljutt.inscurancecar.R;
import com.adiljutt.inscurancecar.model.Cities;

import java.util.List;

/**
 * Created by Adil Jutt on 3/5/2019.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private Context mContext;
    private List<Cities> mUsers;


    public CitiesAdapter(Context mContext, List<Cities> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        if(position<mUsers.size()){
            Cities user = mUsers.get(position);
            viewHolder.city_name.setText(user.getName());
//            holder.tvSiteAddress.setText(site.getAddress());
        }

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(mContext, ""+user.getName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, MainActivity.class);
//                intent.putExtra("city_name",user.getIds());
//                mContext.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView city_name;

        ViewHolder(View itemView) {
            super(itemView);

            city_name = (TextView) itemView.findViewById(R.id.city_name);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                if(pos<mUsers.size()){
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("site",mUsers.get(pos));
                    mContext.startActivity(intent);
                }

            });

        }
    }
}

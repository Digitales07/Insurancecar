package com.adiljutt.inscurancecar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiljutt.inscurancecar.CaseDetailActivity;
import com.adiljutt.inscurancecar.InVoiceReoprtDetailsActivity;
import com.adiljutt.inscurancecar.InvoiceListDetailActivity;
import com.adiljutt.inscurancecar.R;
import com.adiljutt.inscurancecar.model.Cases;
import com.adiljutt.inscurancecar.model.VoiceReports;

import java.util.List;

/**
 * Created by Adil Jutt on 3/5/2019.
 */

public class InvioceAdapter extends RecyclerView.Adapter<InvioceAdapter.ViewHolder> {

    private Context mContext;
    private List<VoiceReports> mUsers;


    public InvioceAdapter(Context mContext, List<VoiceReports> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        VoiceReports user = mUsers.get(position);
//            viewHolder.city_name.setText(user.getOwner_names());
            viewHolder.id.setText(user.getReport_id());
//            holder.tvSiteAddress.setText(site.getAddress());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(mContext, ""+user.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, InvoiceListDetailActivity.class);
                    intent.putExtra("case_detail",user.getReport_id());
                    mContext.startActivity(intent);

                }
            });


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView id;

        ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.insurance_id);
        }
    }
}

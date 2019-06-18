package com.felixtechlabs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BananaPriceAdapter extends RecyclerView.Adapter<BananaPriceAdapter.BananaPriceVh> {
    List<BananaPriceModel> pricelist = new ArrayList<>();
    Context context;

    public BananaPriceAdapter(List<BananaPriceModel> pricelist, Context context) {
        this.pricelist = pricelist;
        this.context = context;
    }


    @NonNull
    @Override
    public BananaPriceVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_items_banana_rates,viewGroup,false);
        BananaPriceVh bananaPriceVh = new BananaPriceVh(view);
        return bananaPriceVh;
    }

    @Override
    public void onBindViewHolder(@NonNull BananaPriceVh bananaPriceVh, int i) {
    bananaPriceVh.tvBananaRates.setText(pricelist.get(i).getBody());

    }



    @Override
    public int getItemCount() {
        return pricelist.size();
    }

    public class BananaPriceVh extends RecyclerView.ViewHolder {
        TextView tvBananaRates;

        public BananaPriceVh(@NonNull View itemView) {
            super(itemView);
            tvBananaRates=itemView.findViewById(R.id.tvBananaRates);


        }
    }
}

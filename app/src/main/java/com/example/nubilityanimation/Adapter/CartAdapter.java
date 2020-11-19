package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Modal.Cart;
import com.example.nubilityanimation.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Cart> mcartList ;
    private Context mContext;

    public CartAdapter(List<Cart> mcartList, Context mContext) {
        this.mcartList = mcartList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_cart,null);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Cart cart = mcartList.get(position);
        holder.pro_title.setText("Product Name: "+cart.getProduct_name());
        holder.pro_price.setText("Product Price " +cart.getProduct_price());



    }

    @Override
    public int getItemCount() {
        return mcartList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder
    {

        private TextView pro_title,pro_price;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            pro_title=itemView.findViewById(R.id.custom_user_product_name);
            pro_price=itemView.findViewById(R.id.custom_user_product_price);
        }
    }
}

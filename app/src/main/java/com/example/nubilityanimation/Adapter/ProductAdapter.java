package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.ProductForUser;
import com.example.nubilityanimation.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>  {

    private Context mContext;
    private List<ProductForUser> mProducts;
    private RecyclarViewInterface mRecyclarViewInterface;

    public ProductAdapter(Context mContext, List<ProductForUser> mProducts, RecyclarViewInterface mRecyclarViewInterface) {
        this.mContext = mContext;
        this.mProducts = mProducts;
        this.mRecyclarViewInterface = mRecyclarViewInterface;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.custom_product_layout,null);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductForUser productForUser = mProducts.get(position);
        holder.title.setText("Name: "+productForUser.getProductName());
        holder.stock.setText("Stock: "+productForUser.getProductStock());
        holder.price.setText("Price(Rs): "+productForUser.getProductPrice());

        if (productForUser.getProductImage().isEmpty())
        {
            Glide.with(mContext).load(R.drawable.user)
                    .into(holder.mImage);
        }
        else
        {
            Glide.with(mContext).load(productForUser.getProductImage())
                    .into(holder.mImage);
        }

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImage;
        private TextView title,stock,price;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mImage= itemView.findViewById(R.id.custom_product_image);
            title=itemView.findViewById(R.id.custom_product_title);
            stock=itemView.findViewById(R.id.cuatom_product_stock);
            price=itemView.findViewById(R.id.custom_product_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclarViewInterface.onClickListner(getAdapterPosition());

                }
            });

        }
    }
}

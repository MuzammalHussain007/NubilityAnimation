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
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.Cart;
import com.example.nubilityanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Cart> mcartList ;
    private Context mContext;
    int quantity=1;
    private RecyclarViewInterface recyclarViewInterface;
    private DatabaseReference mFirebaseDatabase;


    public CartAdapter(List<Cart> mcartList, Context mContext, RecyclarViewInterface recyclarViewInterface) {
        this.mcartList = mcartList;
        this.mContext = mContext;
        this.recyclarViewInterface = recyclarViewInterface;
        mFirebaseDatabase= FirebaseDatabase.getInstance().getReference(ConstantClass.USERCART);
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_cart,null);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {
        final Cart cart = mcartList.get(position);
        holder.pro_title.setText(cart.getProduct_name());
        holder.pro_price.setText("Rs "+cart.getProduct_price()+"/-");
        holder.pro_quantity.setText(cart.getProduct_quantity());
        if (cart.getProduct_image()=="")
        {
            Glide.with(mContext).load(R.drawable.user).into(holder.mImage);
        }
        else
        {
            Glide.with(mContext).load(cart.getProduct_image()).into(holder.mImage);
        }

        holder.pro_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity=quantity+Integer.valueOf(cart.getProduct_quantity());
                holder.pro_quantity.setText(String.valueOf(quantity));
                if (quantity==0 || quantity > 0)
                {
                    holder.pro_decrease.setVisibility(View.VISIBLE);
                }
                HashMap<String ,Object> hashMap = new HashMap<>();
                String send = String.valueOf(quantity);
                hashMap.put("product_quantity",send); ///error
                mFirebaseDatabase.child(FirebaseAuth.getInstance().getUid()).child(cart.getProduct_id()).updateChildren(hashMap);
            }
        });
        holder.pro_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ;
                quantity=quantity-Integer.valueOf(cart.getProduct_quantity());
                holder.pro_quantity.setText(String.valueOf(quantity));
                if (quantity==0 || quantity < 0)
                {
                    holder.pro_decrease.setVisibility(View.GONE);
                }
                HashMap<String ,Object> hashMap = new HashMap<>();
                String send = String.valueOf(quantity);
                hashMap.put("product_quantity",send);
                mFirebaseDatabase.child(FirebaseAuth.getInstance().getUid()).child(cart.getProduct_id()).updateChildren(hashMap);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mcartList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImage,pro_increase,pro_decrease;

        private TextView pro_title,pro_price,pro_quantity;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            pro_title=itemView.findViewById(R.id.custom_user_product_name);
            pro_price=itemView.findViewById(R.id.custom_user_product_price);
            pro_quantity=itemView.findViewById(R.id.custom_product_quantity);
            mImage=itemView.findViewById(R.id.custom_user_product_image);
            pro_increase=itemView.findViewById(R.id.custom_product_increment);
            pro_decrease=itemView.findViewById(R.id.custom_product_decrement);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclarViewInterface.onClickListner(getAdapterPosition());
                }
            });
        }
    }
}

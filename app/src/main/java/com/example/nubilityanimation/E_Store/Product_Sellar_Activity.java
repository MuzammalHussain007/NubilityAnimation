package com.example.nubilityanimation.E_Store;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.Cart;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Product_Sellar_Activity extends AppCompatActivity {
    private ImageView product_image,increment,decrement;
    private TextView quantity,title,description,stock,price;
    private Button add_to_Cart;
    private DatabaseReference mReference,product;
    private int item_quantity=0;
    private String product_id,product_name,product_price,product_des,product_stock,image;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home   :
            {
                startActivity(new Intent(Product_Sellar_Activity.this,ProductViewActivity.class));
                break;
            }
            case R.id.cart_shoping :
            {
                startActivity(new Intent(Product_Sellar_Activity.this,UserCartActivity.class));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Product Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_product__sellar_);
        init();
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item_quantity>=0)
                {
                    decrement.setVisibility(View.VISIBLE);
                }
                item_quantity=item_quantity+1;
                quantity.setText(String.valueOf(item_quantity));
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_quantity=item_quantity-1;
                quantity.setText(String.valueOf(item_quantity));
                if (item_quantity<0)
                {
                    decrement.setVisibility(View.GONE);
                    item_quantity=0;
                    quantity.setText(String.valueOf(item_quantity));
                }
            }
        });
        add_to_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stock = Integer.parseInt(product_stock);
                if (stock<item_quantity)
                {
                    Toast.makeText(getApplicationContext(),"Stock is Short",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (stock <0)
                    {
                        Toast.makeText(getApplicationContext(),"Product Out of Stock",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (item_quantity==0 || item_quantity<0)
                        {
                            Toast.makeText(getApplicationContext(), "Select atleast one quantity", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String id = mReference.push().getKey();
                            Cart cart = new Cart(id, product_id, product_name, product_price, String.valueOf(item_quantity), image);
                            mReference.child(FirebaseAuth.getInstance().getUid()).child(product_id).setValue(cart)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),"Item Add into Cart",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }


                }
            }
        });


    }

    private void init() {
        add_to_Cart=findViewById(R.id.button_for_add_to_cart);
        title=findViewById(R.id.product_seller_title);
        description=findViewById(R.id.product_seller_description);
        quantity=findViewById(R.id.product_quantity);
        product_image=findViewById(R.id.product_seller_image);
        increment=findViewById(R.id.product_increment);
        decrement=findViewById(R.id.product_decrement);
        stock=findViewById(R.id.product_seller_stock);
        price=findViewById(R.id.product_seller_price);
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERCART);

        Bundle intent= getIntent().getExtras();
        product_id=intent.get("pro_id").toString();
        product_name=intent.getString("pro_name");
        product_des=intent.getString("pro_des");
        image=intent.get("pro_image").toString();
        product_price=intent.getString("pro_price");
        product_stock=intent.getString("pro__stock");
        if (image==null)
        {
            Glide.with(getApplicationContext()).load(R.drawable.user).into(product_image);
        }
        else
        {
            Glide.with(getApplicationContext()).load(image).into(product_image);
        }
        title.setText("Product Name: "+product_name);
        description.setText("Product Description: "+product_des);
        product=FirebaseDatabase.getInstance().getReference(ConstantClass.PRODUCTFORUSER);
        price.setText("Price: Rs "+product_price+"/-");
        stock.setText("Stock :"+product_stock);

    }
}
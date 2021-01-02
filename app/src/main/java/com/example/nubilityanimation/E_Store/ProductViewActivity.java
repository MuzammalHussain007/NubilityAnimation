package com.example.nubilityanimation.E_Store;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.ProductAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.ProductForUser;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ProductViewActivity extends AppCompatActivity implements RecyclarViewInterface {
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private List<ProductForUser> mProducts;

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
            case android.R.id.home:
            {
                startActivity(new Intent(ProductViewActivity.this, UserHomeActivity.class));
                break;
            }

            case R.id.cart_shoping :
            {
                startActivity(new Intent(ProductViewActivity.this,UserCartActivity.class));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        init();
        getSupportActionBar().setTitle("E-Store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductForUser productForUser = snapshot.getValue(ProductForUser.class);
                mProducts.add(productForUser);
                mRecyclerView.setAdapter(new ProductAdapter(getApplicationContext(),mProducts,ProductViewActivity.this));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {

        mProducts= new ArrayList<>();
        mRecyclerView=findViewById(R.id.product_reycyclarView);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantClass.PRODUCTFORUSER);


    }

    @Override
    public void onClickListner(int position) {
        ProductForUser productForUser = mProducts.get(position);
        String id = productForUser.getProductId();
        String name = productForUser.getProductName();
        String des = productForUser.getProductDescription();
        String url = productForUser.getProductImage();
        String price= productForUser.getProductPrice();
        String pro_stock = productForUser.getProductStock();
        String pro_Author= productForUser.getProductAuthor();
        Intent intent = new Intent(this,Product_Sellar_Activity.class);
        intent.putExtra("pro_id",id);
        intent.putExtra("pro_des",des);
        intent.putExtra("pro_image",url);
        intent.putExtra("pro__stock",pro_stock);
        intent.putExtra("pro_price",price);
        intent.putExtra("pro_name",name);
        intent.putExtra("pro_author",pro_Author);
        startActivity(intent);

    }
}
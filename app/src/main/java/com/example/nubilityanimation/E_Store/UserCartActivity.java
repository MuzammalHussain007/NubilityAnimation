package com.example.nubilityanimation.E_Store;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.CartAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.Cart;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserCartActivity extends AppCompatActivity implements RecyclarViewInterface {
    private DatabaseReference mReference;
    private RecyclerView mRecyclerView;
    private List<Cart> mcartList;
    private Button mbtn_next;
    private ItemTouchHelper.SimpleCallback itemTouchHelper;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                startActivity(new Intent(UserCartActivity.this, UserHomeActivity.class));
                break;
            }

            case R.id.cart_shoping :
            {
                startActivity(new Intent(UserCartActivity.this,UserCartActivity.class));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cart");


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mReference.child(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  Cart cart = snapshot.getValue(Cart.class);
                  mcartList.add(cart);
                  new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView);
                  mRecyclerView.setAdapter(new CartAdapter(mcartList,getApplicationContext(),UserCartActivity.this));
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
        itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mReference.child(FirebaseAuth.getInstance().getUid()).child(mcartList.get(viewHolder.getAdapterPosition()).getProduct_id()).removeValue();
                mcartList.remove(viewHolder.getAdapterPosition());


            }
        };
    }

    private void init() {
        mcartList= new ArrayList<>();
        mRecyclerView=findViewById(R.id.cart_recyclar_view);
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.USERCART);

    }

    @Override
    public void onClickListner(int position) {
        Cart cart = mcartList.get(position);
        String cart_id,product_id,product_name,product_price,product_quantity,product_image;
        cart_id = cart.getCart_id();
        product_id=cart.getProduct_id();
        product_name=cart.getProduct_name();
        product_image=cart.getProduct_image();
        product_price=cart.getProduct_price();
        product_quantity=cart.getProduct_quantity();
        Intent intent = new Intent(UserCartActivity.this,UserAddressActivity.class);
        intent.putExtra("carts_id",cart_id);
        intent.putExtra("products_id",product_id);
        intent.putExtra("products_name",product_name);
        intent.putExtra("products_image",product_image);
        intent.putExtra("products_price",product_price);
        intent.putExtra("products_quantitys",product_quantity);
        startActivity(intent);


    }
}
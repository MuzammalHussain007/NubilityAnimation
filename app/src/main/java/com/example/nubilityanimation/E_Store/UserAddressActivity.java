package com.example.nubilityanimation.E_Store;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.Cart;
import com.example.nubilityanimation.Modal.Order_Product;
import com.example.nubilityanimation.Modal.User_Address_information;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAddressActivity extends AppCompatActivity {


    private ArrayList<Cart> myCartList;
    private EditText name,phone,home_address;
    private Button order_button;
    private Cart cart;
    private String cart_id,pro_id,pro_name,pro_image,pro_price,pro_quantity;
    private DatabaseReference m_database,user_order_ref,user_cart_ref;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home   :
            {
                startActivity(new Intent(UserAddressActivity.this,UserCartActivity.class));
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        init();
        getSupportActionBar().setTitle("User Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().isEmpty() && phone.getText().toString().isEmpty() && home_address.getText().toString().isEmpty() )
                {
                    Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserAddressActivity.this);
                    LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
                    View v = layoutInflater.inflate(R.layout.order_confirmation_dialog,null);
                    alertDialog.setTitle("Confirmation Dialog");
                    TextView textView = v.findViewById(R.id.user_custom_confirmation_dialog);
                    Button yes,no;
                    yes=v.findViewById(R.id.user_order_button_yes);
                    no=v.findViewById(R.id.user_order_button_no);
                    alertDialog.setView(v);
                    final AlertDialog dialog = alertDialog.create();
                    dialog.show();
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = m_database.push().getKey();
                            int price = Integer.parseInt(pro_price);
                            int quantity = Integer.parseInt(pro_quantity);
                            int res= price * quantity;
                            m_database.child(FirebaseAuth.getInstance().getUid()).setValue(new User_Address_information(id,name.getText().toString(),phone.getText().toString(),home_address.getText().toString()));
                            name.setText("");
                            phone.setText("");
                            home_address.setText("");
                            String order_id=user_order_ref.push().getKey();
                            user_order_ref.child(FirebaseAuth.getInstance().getUid()).child(pro_id).child(order_id).setValue(new Order_Product(order_id,cart_id,pro_id,pro_name,String.valueOf(res),pro_quantity,pro_image)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful())
                                 {
                                     Toast.makeText(getApplicationContext(),"Your Order is done SuccessFully",Toast.LENGTH_SHORT).show();
                                 }
                                }
                            });
                            dialog.dismiss();

                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                }




            }
        });
    }

    private void init() {
        order_button=findViewById(R.id.user_order_place_order);
        name=findViewById(R.id.user_order_name);
        phone = findViewById(R.id.user_order_phone);
        home_address=findViewById(R.id.user_order_address);
        myCartList=new ArrayList<>();
        cart= new Cart();
        Bundle bundle= getIntent().getExtras();
        cart_id=bundle.getString("carts_id");
        pro_id=bundle.getString("products_id");
        pro_name=bundle.getString("products_name");
        pro_image=bundle.getString("products_image");
        pro_price=bundle.getString("products_price");
        pro_quantity= bundle.getString("products_quantitys");

        user_cart_ref=FirebaseDatabase.getInstance().getReference(ConstantClass.USERCART);
        user_order_ref=FirebaseDatabase.getInstance().getReference(ConstantClass.USER_ORDER_INFORMATION);
        m_database= FirebaseDatabase.getInstance().getReference(ConstantClass.USER_ADDRESS_INFORMATION);

    }
}
package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class ChangeProductActivity extends AppCompatActivity implements Serializable {

    DatabaseReference database;

    Bundle extras;
    Product product;

    Button remove, modify, cancel, confirm;

    EditText field1, field2, field3, field4, field5, field6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_product);

        database = FirebaseDatabase.getInstance().getReference();

        cancel = (Button) findViewById(R.id.cancelModify);
        confirm = (Button) findViewById(R.id.confirmModification);
        modify = (Button) findViewById(R.id.changeProduct);
        remove = (Button) findViewById(R.id.removeProduct);

        field1 = (EditText) findViewById(R.id.changeProductFormField1);
        field2 = (EditText) findViewById(R.id.changeProductFormField2);
        field3 = (EditText) findViewById(R.id.changeProductFormField3);
        field4 = (EditText) findViewById(R.id.changeProductFormField4);
        field5 = (EditText) findViewById(R.id.changeProductFormField5);
        field6 = (EditText) findViewById(R.id.changeProductFormField6);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("products").child(product.getId()).removeValue();
                Intent ManageProducts = new Intent(ChangeProductActivity.this, ManageProductsActivity.class);
                startActivity(ManageProducts);
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field1.setEnabled(true);
                field2.setEnabled(true);
                field3.setEnabled(true);
                field4.setEnabled(true);
                field5.setEnabled(true);
                field6.setEnabled(true);
                confirm.setEnabled(true);

                modify.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field1.setText(""); field1.setEnabled(false);
                field2.setText(""); field2.setEnabled(false);
                field3.setText(""); field3.setEnabled(false);
                field4.setText(""); field4.setEnabled(false);
                field5.setText(""); field5.setEnabled(false);
                field6.setText(""); field6.setEnabled(false);
                confirm.setEnabled(false);
                cancel.setVisibility(View.GONE);
                modify.setVisibility(View.VISIBLE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference currProduct = database.child("products").child(product.getId());
                if(field1.getText().toString() != null) {
                    currProduct.child("name").setValue(field1.getText().toString());
                } else if(field2.getText().toString() != null) {
                    currProduct.child("criticalQty").setValue(Long.parseLong(field2.getText().toString()));
                } else if(field3.getText().toString() != null) {
                    currProduct.child("costPrice").setValue(Float.parseFloat(field3.getText().toString()));
                } else if(field4.getText().toString() != null) {
                    currProduct.child("price").setValue(Float.parseFloat(field4.getText().toString()));
                } else if(field5.getText().toString() != null) {
                    currProduct.child("priceAtl").setValue(Float.parseFloat(field5.getText().toString()));
                } else if(field6.getText().toString() != null) {
                    currProduct.child("priceSoc").setValue(Float.parseFloat(field6.getText().toString()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        extras = getIntent().getExtras();
        if (extras != null) {
            product = (Product) extras.getSerializable("currentProduct");
        }
    }
}

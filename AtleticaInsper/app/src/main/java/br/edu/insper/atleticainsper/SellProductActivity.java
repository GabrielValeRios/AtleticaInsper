package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellProductActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<String> spinnerSeller;
    private ArrayList<String> spinnerPayMethod;

    Bundle extras;
    TextView productName;
    TextView productPrice;
    Button cancelSell;
    Button confirmSell;

    String pName;
    String pPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        productName = (TextView) findViewById(R.id.FIELD_prodName);
        productPrice = (TextView) findViewById(R.id.FIELD_prodPrice);
        cancelSell = (Button) findViewById(R.id.cancelSell);
        confirmSell = (Button) findViewById(R.id.confirmSell);


        //Spinner para escolher o vendedor
        spinnerSeller = new ArrayList<String>();
        spinnerSeller.add("Felipe Buniac");
        spinnerSeller.add("Gabriel Rios");
        spinnerSeller.add("Gustavo Efeiche");
        spinnerSeller.add("João Pedro Castro");
        spinnerSeller.add("Marcelo Franco");
        Spinner s1 = (Spinner) findViewById(R.id.sellerChooser);
        ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.seller_chooser_item, spinnerSeller);
        s1.setAdapter(adapter1);

        //Spinner para escolher o método de pagamento
        spinnerPayMethod = new ArrayList<String>();
        spinnerPayMethod.add("Cartão");
        spinnerPayMethod.add("Dinheiro");
        Spinner s2 = (Spinner) findViewById(R.id.methodChooser);
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.seller_chooser_item, spinnerPayMethod);
        s2.setAdapter(adapter2);


        //COnfirgurações dos botões
        cancelSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProductsNormal = new Intent(SellProductActivity.this, ProductsNormalActivity.class);
                startActivity(ProductsNormal);
            }
        });

        confirmSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProductsNormal = new Intent(SellProductActivity.this, ProductsNormalActivity.class);

                database = FirebaseDatabase.getInstance().getReference();

                database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        HashMap<String, HashMap<String, String>> products = (HashMap<String, HashMap<String, String>>) snapshot.getValue();

                        for(Map.Entry<String, HashMap<String, String>> entry : products.entrySet()) {

                            Map<String, String> productParams = entry.getValue();
                            String productID = entry.getKey();

                            if(productParams.get("name") != null) {
                                if(productParams.get("name").equals(pName) && productParams.get("available").equals("true")) {
                                    int current_qty = Integer.parseInt(productParams.get("current_qty"));

                                    database.child("products")
                                            .child(productID)
                                            .child("current_qty")
                                            .setValue(String.valueOf(current_qty - 1));

                                    if(Integer.parseInt(productParams.get("current_qty")) == 1) {
                                        Log.i("TEST","qqr merda");
                                        database.child("products")
                                                .child(productID)
                                                .child("available")
                                                .setValue("false");
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                finish();
                startActivity(ProductsNormal);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        extras = getIntent().getExtras();
        if (extras != null) {
            pName = extras.getString("productName");
            pPrice = extras.getString("productPrice");
            productName.setText(pName);
            productPrice.setText("R$ " + pPrice);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void delay(int seconds) {
        try {
            Thread.sleep(seconds*1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

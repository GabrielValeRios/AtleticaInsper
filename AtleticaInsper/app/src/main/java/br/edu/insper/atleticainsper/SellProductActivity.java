package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SellProductActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<String> spinnerSeller;
    private ArrayList<String> spinnerPayMethod;

    Bundle extras;
    TextView productName;
    TextView productPrice;
    Button cancelSell;
    Button confirmSell;

    Product product;

    String seller;
    String pmtMethod;

    SaleHistory saleHistory;
    Sale sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        database = FirebaseDatabase.getInstance().getReference();

        saleHistory = new SaleHistory();

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
        final Spinner s1 = (Spinner) findViewById(R.id.sellerChooser);
        ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.seller_chooser_item, spinnerSeller);
        s1.setAdapter(adapter1);

        //Spinner para escolher o método de pagamento
        spinnerPayMethod = new ArrayList<String>();
        spinnerPayMethod.add("Cartão");
        spinnerPayMethod.add("Dinheiro");
        final Spinner s2 = (Spinner) findViewById(R.id.methodChooser);
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

                seller = s1.getSelectedItem().toString();
                pmtMethod = s2.getSelectedItem().toString();

                database.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Map<String, HashMap<String, Object>> statusMap = (HashMap) snapshot.getValue();
                        Log.i("STATUSMAP", String.valueOf(statusMap));

                        int nextProductID = (int) (long) statusMap.get("nextProductID").get("value");
                        String nextSaleID = (String) statusMap.get("nextSaleID").get("value");

                        sale = new Sale(pmtMethod, product, seller, nextSaleID);
                        sale.compute();
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
            product = (Product) extras.getSerializable("currentProduct");
            productName.setText(product.getName());
            productPrice.setText("R$ " + product.getPrice() + "0");
        }
    }

    @Override
    public void onBackPressed() {
    }
}

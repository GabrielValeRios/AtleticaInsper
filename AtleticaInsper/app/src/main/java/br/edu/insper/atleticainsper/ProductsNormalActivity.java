package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProductsNormalActivity extends AppCompatActivity {

    //Array for product images
    TypedArray prodImgs;
    int i;

    //Reference to Firebase realtime database
    DatabaseReference database;

    //Screen elements
    TableLayout table;
    TableRow row;
    Button logout;
    String info;
    TextView productInfo;
    ImageView productImg;

    //Product parameters
    String available;
    String critical_qty;
    String current_qty;
    String hasDiscount;
    String name;
    String price;
    String priceAtl;
    String priceSoc;
    String sold_qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_normal);

        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(ProductsNormalActivity.this, LoginActivity.class);
                startActivity(Login);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        prodImgs = getResources().obtainTypedArray(R.array.product_imgs);
        i = 0;

        database = FirebaseDatabase.getInstance().getReference();

        database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                HashMap<String, HashMap<String, String>> products = (HashMap<String, HashMap<String, String>>) snapshot.getValue();

                for(Map.Entry<String, HashMap<String, String>> entry : products.entrySet()) {

                    Map<String, String> productParams = entry.getValue();
                    available = productParams.get("available");
                    critical_qty = productParams.get("critical_qty");
                    current_qty = productParams.get("current_qty");
                    hasDiscount = productParams.get("hasDiscount");
                    name = productParams.get("name");
                    price = productParams.get("price");
                    priceAtl = productParams.get("priceAtl");
                    priceSoc = productParams.get("priceSoc");
                    sold_qty = productParams.get("sold_qty");

                    createProduct(name, price);
                    i = Integer.parseInt(entry.getKey().substring(7,10));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void createProduct(final String n, final String p) {

        // [START] Criação das Views necessárias
        productImg = new ImageView(ProductsNormalActivity.this);
        productInfo = new TextView(ProductsNormalActivity.this);
        row = new TableRow(ProductsNormalActivity.this);
        table = (TableLayout) findViewById(R.id.productTable);
        // [END] Criação das Views necessárias


        // [START] Configuração de parâmetros de layout das views recém-criadas
        TableRow.LayoutParams layoutParamsImg = new TableRow.LayoutParams(400,300);
        TableRow.LayoutParams layoutParamsInfo = new TableRow.LayoutParams(740, 300);
        TableLayout.LayoutParams layoutParamsRow = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsRow.setMargins(0,10,0,0);
        productImg.setLayoutParams(layoutParamsImg);
        productInfo.setLayoutParams(layoutParamsInfo);
        row.setLayoutParams(layoutParamsRow);
        // [END] Configuração de parâmetros de layout das views recém-criadas


        // [START] Configuração de layout das informações do produto
        info = n + "\n" + "R$" + p;
        productInfo.setText(info);
        productInfo.setTextSize(20);
        productInfo.setBackgroundColor(Color.parseColor("#bf0e0e"));
        productInfo.setTextColor(Color.parseColor("#ffffff"));
        productInfo.setGravity(Gravity.CENTER);
        productInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SellProduct = new Intent(ProductsNormalActivity.this, SellProductActivity.class);
                SellProduct.putExtra("productName",n);
                SellProduct.putExtra("productPrice",p);
                startActivity(SellProduct);
            }
        });
        // [END] Configuração de layout das informações do produto


        // [START] Configuração de layout da imagem do produto
        productImg.setImageResource(prodImgs.getResourceId(id-1, -1));
        productImg.setBackgroundColor(Color.parseColor("#FF0000"));
        // [END] Configuração de layout da imagem do produto


        // [START] Adição das views na TableRow
        row.addView(productImg, 0);
        row.addView(productInfo, 1);
        // [END] Adição das views na TableRow


        // [START] Adição da row no TableLayout
        if (table != null) {
            table.addView(row, 0);
        }
        // [END] Adição da row no TableLayout
    }
}

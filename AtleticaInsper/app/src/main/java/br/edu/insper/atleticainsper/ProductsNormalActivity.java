package br.edu.insper.atleticainsper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class ProductsNormalActivity extends AppCompatActivity {

    DatabaseReference database;

    //Product inventory
    Inventory inv;

    //Array for product images
    TypedArray prodImgs;

    //Screen elements
    TableLayout table;
    TableRow row;
    Button logout;
    String info;
    TextView productInfo;
    ImageView productImg;
    ScrollView productScrollView;
    LinearLayout loadingStatus;

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

        productScrollView = (ScrollView) findViewById(R.id.productScrollView);
        loadingStatus = (LinearLayout) findViewById(R.id.loadingStatus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference();

        prodImgs = getResources().obtainTypedArray(R.array.product_imgs);

        inv = new Inventory();

        database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Map<String, TreeMap<String, Object>> productMap = new TreeMap<>((HashMap) snapshot.getValue());

                inv.getProductList().clear();

                for(Map.Entry<String, TreeMap<String, Object>> entry : productMap.entrySet()) {

                    Map<String, Object> productParams = entry.getValue();
                    int idAsInteger = Integer.parseInt(entry.getKey());
                    String id = entry.getKey();
                    float costPrice = (float) (long) productParams.get("costPrice");
                    int criticalQty = (int) (long) productParams.get("criticalQty");
                    int currentQty = (int) (long) productParams.get("currentQty");
                    boolean hasDiscount = (boolean) productParams.get("hasDiscount");
                    boolean isAvailable = (boolean) productParams.get("isAvailable");
                    String name = (String) productParams.get("name");
                    float price = (float) (long) productParams.get("price");
                    float priceAtl = (float) (long) productParams.get("priceAtl");
                    float priceSoc = (float) (long) productParams.get("priceSoc");
                    int soldQty = (int) (long) productParams.get("soldQty");

                    Product p = new Product(id, costPrice, criticalQty, currentQty, hasDiscount, isAvailable, name, price, priceAtl, priceSoc, soldQty);
                    inv.addProduct(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        CountDownTimer timer = new CountDownTimer(4000,4000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                loadingStatus.setVisibility(View.GONE);
                productScrollView.setVisibility(View.VISIBLE);
                for (Product p : inv.getProductList()) {
                    createProductLayout(p);
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }

    public void createProductLayout(final Product p) {

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
        if(p.isAvailable()) {
            info = p.getName() + "\n" + "R$" + String.valueOf(p.getPrice()) + "0";
        } else {
            info = p.getName() + "\n" + "Indisponível";
        }
        productInfo.setText(info);
        productInfo.setTextSize(20);
        productInfo.setBackgroundColor(Color.parseColor("#bf0e0e"));
        productInfo.setTextColor(Color.parseColor("#ffffff"));
        productInfo.setGravity(Gravity.CENTER);

        productInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SellProduct = new Intent(ProductsNormalActivity.this, SellProductActivity.class);
                SellProduct.putExtra("currentProduct", p);
                startActivity(SellProduct);
            }
        });
        // [END] Configuração de layout das informações do produto


        // [START] Configuração de layout da imagem do produto
        productImg.setImageResource(prodImgs.getResourceId(Integer.parseInt(p.getId())-1, -1));
        productImg.setBackgroundColor(Color.parseColor("#FF0000"));
        // [END] Configuração de layout da imagem do produto


        // [START] Adição das views na TableRow
        row.addView(productImg, 0);
        row.addView(productInfo, 1);
        // [END] Adição das views na TableRow


        // [START] Adição da row no TableLayout
        if (table != null) {
            table.addView(row);
        }
        // [END] Adição da row no TableLayout
    }

    public Inventory getInventory() {
        return inv;
    }
}

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

    //Product inventory
    Inventory inventory;

    //Array for product images
    TypedArray prodImgs;
    int i;

    //Screen elements
    TableLayout table;
    TableRow row;
    Button logout;
    String info;
    TextView productInfo;
    ImageView productImg;

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
        inventory = new Inventory();
        Log.i("PRODUCT_LIST_ACT", String.valueOf(inventory.getProductList()));
        for(Product p : inventory.getProductList()) {
            createProduct(p.getName(), p.getPrice(), p.getId());
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void createProduct(final String n, final float p, final int id) {

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
        info = n + "\n" + "R$" + String.valueOf(p);
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

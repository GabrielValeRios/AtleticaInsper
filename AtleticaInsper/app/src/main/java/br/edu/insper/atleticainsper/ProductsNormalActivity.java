package br.edu.insper.atleticainsper;

import android.content.Intent;
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

    DatabaseReference database;
    TableLayout productTable;
    Button logout;
    String info;

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

        database = FirebaseDatabase.getInstance().getReference();
        productTable = (TableLayout) findViewById(R.id.productTable);

        database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                HashMap<String, HashMap<String, String>> products = (HashMap<String, HashMap<String, String>>) snapshot.getValue();
                Log.i("HASH_MAP", String.valueOf(products));

                for(Map.Entry<String, HashMap<String, String>> entry : products.entrySet()) {

                    Map<String, String> productParams = entry.getValue();

                    String available = productParams.get("available"); //boolean "true" or "false"
                    String current_qty = productParams.get("current_qty"); //integer "x"
                    String id = productParams.get("id"); //integer "x"
                    String name = productParams.get("name"); //string "xyz"
                    String price = productParams.get("price"); //float "x.x"
                    String sold_qty = productParams.get("sold_qty"); //integer "x"

                    //Criação das Views necessárias; o id productTable se refere ao TableLayout
                    ImageView productImg = new ImageView(ProductsNormalActivity.this);
                    TextView productInfo = new TextView(ProductsNormalActivity.this);
                    TableRow row = new TableRow(ProductsNormalActivity.this);
                    TableLayout table = (TableLayout) findViewById(R.id.productTable);

                    //Configuração de parâmetros de layout das views recém-criadas
                    TableRow.LayoutParams layoutParamsImg = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.6f);
                    TableRow.LayoutParams layoutParamsInfo = new TableRow.LayoutParams(0, 600, 0.4f);
                    TableLayout.LayoutParams layoutParamsRow = new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);

                    layoutParamsRow.setMargins(0,10,0,0);

                    productImg.setLayoutParams(layoutParamsImg);
                    productInfo.setLayoutParams(layoutParamsInfo);
                    row.setLayoutParams(layoutParamsRow);

                    //Configuração das Views
                    productImg.setImageResource(R.drawable.logo);
                    productImg.setBackgroundColor(Color.parseColor("#FF0000"));
                    info = name + "\n" + "R$" + price;
                    productInfo.setText(info);
                    productInfo.setTextSize(20);
                    productInfo.setBackgroundColor(Color.parseColor("#bf0e0e"));
                    productInfo.setTextColor(Color.parseColor("#ffffff"));
                    productInfo.setGravity(Gravity.CENTER);

                    //Adiciono as views na TableRow
                    row.addView(productImg, 0);
                    row.addView(productInfo, 1);

                    //Adiciono a TableRow no TableLayout
                    table.addView(row, 0);
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
}

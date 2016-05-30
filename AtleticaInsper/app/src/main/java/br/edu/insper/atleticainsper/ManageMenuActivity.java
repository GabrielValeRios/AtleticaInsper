package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageMenuActivity extends AppCompatActivity {

    Button manageProducts;
    Button cashFlow;
    Button salesHistory;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        manageProducts = (Button) findViewById(R.id.manageProducts);
        cashFlow = (Button) findViewById(R.id.cashFlow);
        salesHistory = (Button) findViewById(R.id.salesHistory);
        logout = (Button) findViewById(R.id.logout);

        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ManageProducts = new Intent(ManageMenuActivity.this, ManageProductsActivity.class);
                startActivity(ManageProducts);
            }
        });

        cashFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CashFlow = new Intent(ManageMenuActivity.this, CashFlowActivity.class);
                startActivity(CashFlow);
            }
        });

        salesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SalesHistory = new Intent(ManageMenuActivity.this, SalesHistoryActivity.class);
                startActivity(SalesHistory);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(ManageMenuActivity.this, LoginActivity.class);
                startActivity(Login);
            }
        });
    }
}

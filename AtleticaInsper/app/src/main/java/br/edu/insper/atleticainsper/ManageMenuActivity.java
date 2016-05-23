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

    ImageView logoAtletica;
    TextView loggedAs;
    TextView loggedUsername;
    Button manageProducts;
    Button viewCashFlow;
    Button viewSalesHistory;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        logoAtletica = (ImageView) findViewById(R.id.logoAtletica);
        loggedAs = (TextView) findViewById(R.id.loggedAs);
        loggedUsername = (TextView) findViewById(R.id.loggedUsername);
        manageProducts = (Button) findViewById(R.id.manageProducts);
        viewCashFlow = (Button) findViewById(R.id.viewCashFlow);
        viewSalesHistory = (Button) findViewById(R.id.viewSalesHistory);
        logout = (Button) findViewById(R.id.logout);

        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMenuActivity.this, ManageProductsActivity.class);
                startActivity(intent);
            }
        });

        viewCashFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMenuActivity.this, CashFlowActivity.class);
                startActivity(intent);
            }
        });

        viewSalesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMenuActivity.this, SalesHistoryActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

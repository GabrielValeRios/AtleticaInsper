package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMenuActivity extends AppCompatActivity {

    Bundle extras;

    String user;

    TextView loggedUsername;
    Button manageProducts;
    Button cashFlow;
    Button salesHistory;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        loggedUsername = (TextView) findViewById(R.id.loggedUsername);
        manageProducts = (Button) findViewById(R.id.manageProducts);
        cashFlow = (Button) findViewById(R.id.cashFlow);
        salesHistory = (Button) findViewById(R.id.salesHistory);
        logout = (Button) findViewById(R.id.logout);

        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ManageProducts = new Intent(AdminMenuActivity.this, ManageProductsActivity.class);
                startActivity(ManageProducts);
            }
        });

        cashFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CashFlow = new Intent(AdminMenuActivity.this, CashFlowActivity.class);
                startActivity(CashFlow);
            }
        });

        salesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SalesHistory = new Intent(AdminMenuActivity.this, SalesHistoryActivity.class);
                startActivity(SalesHistory);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(AdminMenuActivity.this, LoginActivity.class);
                startActivity(Login);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("loggedUsername");
            loggedUsername.setText(user);
        }
    }
}

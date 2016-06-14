package br.edu.insper.atleticainsper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CashFlowActivity extends AppCompatActivity {

    DatabaseReference database;

    TextView in;
    TextView out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        in = (TextView) findViewById(R.id.in);
        out = (TextView) findViewById(R.id.out);
    }

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference();

        database.child("cashflow").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, HashMap<String, Object>> cashflow = (HashMap) snapshot.getValue();
                Log.i("CASHFLOW", String.valueOf(cashflow));

                int inValue = (int) (long) cashflow.get("in").get("value");
                int outValue = (int) (long) cashflow.get("out").get("value");
                in.setText("Receita desde 12/06/2016: " + "R$" + String.valueOf(inValue) + ".00");
                out.setText("Gasto desde 12/06/2016: " + "R$" + String.valueOf(outValue) + ".00");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

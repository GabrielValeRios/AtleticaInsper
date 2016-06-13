package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SalesHistoryActivity extends AppCompatActivity {

    DatabaseReference database;

    LinearLayout loadingStatus;
    ScrollView saleScrollView;

    TextView saleRecord;
    TextView saleDatetime;
    ViewGroup sale;
    LinearLayout table;

    private String dayAsString, monthAsString, minuteAsString, hourAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);

        database = FirebaseDatabase.getInstance().getReference();

        loadingStatus = (LinearLayout) findViewById(R.id.loadingStatus);
        saleScrollView = (ScrollView) findViewById(R.id.saleScrollView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference();

        database.child("sales").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Map<String, TreeMap<String, Object>> salesMap = new TreeMap<>((HashMap) snapshot.getValue());

                for(Map.Entry<String, TreeMap<String, Object>> entry : salesMap.entrySet()) {

                    Map<String, Object> saleParams = entry.getValue();
                    Map<String, Object> product = (HashMap) saleParams.get("product");

                    String id = entry.getKey();
                    int day = (int) (long) saleParams.get("day");
                    int month = (int) (long) saleParams.get("month");
                    int year = (int) (long) saleParams.get("year");
                    int hour = (int) (long) saleParams.get("hour");
                    int minute = (int) (long) saleParams.get("minute");
                    String method = (String) saleParams.get("method");
                    String productName = (String) product.get("name");
                    float productPrice = (float) (long) product.get("price");
                    String seller = (String) saleParams.get("seller");

                    if(day < 10) {
                        dayAsString = "0" + String.valueOf(day);
                    } else {
                        dayAsString = String.valueOf(day);
                    }

                    if(month < 10) {
                        monthAsString = "0" + String.valueOf(month);
                    } else {
                        monthAsString = String.valueOf(month);
                    }

                    if(minute < 10) {
                        minuteAsString = "0" + String.valueOf(minute);
                    } else {
                        minuteAsString = String.valueOf(minute);
                    }

                    if(hour < 10) {
                        hourAsString = "0" + String.valueOf(hour);
                    } else {
                        hourAsString = String.valueOf(hour);
                    }

                    createSaleLayout(id, dayAsString, monthAsString, year, hourAsString, minuteAsString, method, productName, productPrice, seller);
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
                saleScrollView.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void createSaleLayout(String id, String day, String month, int year, String hour, String minute, String method, String productName, float productPrice, String seller) {

        // [START] Criação das Views necessárias
        String datetime = day + "/" + month + "/" + year + "\n" + hour + ":" + minute;
        String productInfo = productName + "  R$ " + String.valueOf(productPrice) + "0" + " (" + method + ")";

        table = (LinearLayout) findViewById(R.id.saleTable);
        sale = (ViewGroup) getLayoutInflater().inflate(R.layout.sale_layout, null);
        TextView viewDatetime = (TextView) sale.getChildAt(0);
        TextView viewProductInfo = (TextView) sale.getChildAt(1);

        viewDatetime.setText(datetime);
        viewProductInfo.setText(productInfo);

        table.addView(sale, 0);
        // [END] Criação das Views necessárias


//        // [START] Configuração de parâmetros de layout das views recém-criadas
//        LinearLayout.LayoutParams layoutParamsDatetime = new LinearLayout.LayoutParams(400, 300);
//        LinearLayout.LayoutParams layoutParamsRecord = new LinearLayout.LayoutParams(740, 300);
//        TableLayout.LayoutParams layoutParamsLinearLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
//        layoutParamsLinearLayout.setMargins(0,0,0,0);
//
//        saleDatetime.setLayoutParams(layoutParamsDatetime);
//        saleRecord.setLayoutParams(layoutParamsRecord);
//        sale.setLayoutParams(layoutParamsLinearLayout);
//        // [END] Configuração de parâmetros de layout das views recém-criadas
//
//
//        // [START] Configuração de layout da data da venda
//
//        saleDatetime.setTextSize(20);
//        saleDatetime.setBackgroundColor(Color.parseColor("#bf0e0e"));
//        saleDatetime.setTextColor(Color.parseColor("#ffffff"));
//        // [END] Configuração de layout da data da venda
//
//
//        // [START] Confirguração de layout dos dados do produto vendido
//
//        saleRecord.setTextSize(20);
//        saleRecord.setBackgroundColor(Color.parseColor("#bf0e0e"));
//        saleRecord.setTextColor(Color.parseColor("#ffffff"));
//        saleRecord.setGravity(Gravity.CENTER);
//
//
//        // [START] Adição das views na TableRow
//        sale.addView(saleDatetime, 0);
//        sale.addView(saleRecord, 1);
//        // [END] Adição das views na TableRow
//
//        // [START] Adição da row no TableLayout
//        if (table != null) {
//            table.addView(sale, 0);
//        }
//        // [END] Adição da row no TableLayout
    }
}

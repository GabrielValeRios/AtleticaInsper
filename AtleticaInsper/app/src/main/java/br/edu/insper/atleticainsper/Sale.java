package br.edu.insper.atleticainsper;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Sale {

    DatabaseReference database;

    Calendar calendar;

    private int day;
    private int month;
    private int year;

    private int hour;
    private int minute;

    private String method;
    private Product product;
    private String seller;

    private String saleID;

    public Sale(String method, Product product, String seller, String saleID) {

        database = FirebaseDatabase.getInstance().getReference();

        calendar = Calendar.getInstance();

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);

        this.method = method;
        this.product = product;
        this.seller = seller;

        this.saleID = saleID;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getMethod() {
        return method;
    }

    public Product getProduct() {
        return product;
    }

    public String getSeller() {
        return seller;
    }

    public String getTime() {
        return String.valueOf(this.hour) + ":" + String.valueOf(this.minute);
    }

    public String getDate() {
        return String.valueOf(this.day) + "/" + String.valueOf(this.month) + "/" + String.valueOf(this.year);
    }

    public void compute() {

        //Add sale to sales history
        database.child("sales").child(this.saleID).child("day").setValue(this.day);
        database.child("sales").child(this.saleID).child("hour").setValue(this.hour);
        database.child("sales").child(this.saleID).child("method").setValue(this.method);
        database.child("sales").child(this.saleID).child("minute").setValue(this.minute);
        database.child("sales").child(this.saleID).child("month").setValue(this.month);
        database.child("sales").child(this.saleID).child("product").child("name").setValue(product.getName());
        database.child("sales").child(this.saleID).child("product").child("price").setValue(product.getPrice());
        database.child("sales").child(this.saleID).child("seller").setValue(this.seller);
        database.child("sales").child(this.saleID).child("year").setValue(this.year);

        //Change product parameters based on sale
        database.child("products").child(this.product.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Map<String, Object> productDB = (HashMap) snapshot.getValue();
                int currentQty = (int) (long) productDB.get("currentQty");
                int soldQty = (int) (long) productDB.get("soldQty");

                if (currentQty == 1) {
                    database.child("products").child(product.getId()).child("isAvailable").setValue(false);
                }

                database.child("products").child(product.getId()).child("currentQty").setValue(currentQty-1);
                database.child("products").child(product.getId()).child("soldQty").setValue(soldQty+1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Add product price to "in" key in cashflow
        database.child("cashflow").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, HashMap<String, Object>> cashflow = (HashMap) snapshot.getValue();
                int in = (int) (long) cashflow.get("in").get("value");
                database.child("cashflow").child("in").child("value").setValue(in + product.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package br.edu.insper.atleticainsper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SaleHistory {

    private DatabaseReference database;
    Map<String, HashMap<String, Object>> statusDB;

    private ArrayList<Sale> sales;

    private int saleCount;
    private int nextSaleID;

    public SaleHistory() {
    }

    public ArrayList<Sale> getSaleHistory() {
        return sales;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void uploadToDatabase() {
        for(Sale s : this.sales) {
            database.child("sales").child(String.valueOf(nextSaleID)).child("day").setValue(s.getDay());
            database.child("sales").child(String.valueOf(nextSaleID)).child("hour").setValue(s.getHour());
            database.child("sales").child(String.valueOf(nextSaleID)).child("method").setValue(s.getMethod());
            database.child("sales").child(String.valueOf(nextSaleID)).child("minute").setValue(s.getMinute());
            database.child("sales").child(String.valueOf(nextSaleID)).child("month").setValue(s.getMonth());
            database.child("sales").child(String.valueOf(nextSaleID)).child("product").setValue(s.getProduct());
            database.child("sales").child(String.valueOf(nextSaleID)).child("seller").setValue(s.getSeller());
            database.child("sales").child(String.valueOf(nextSaleID)).child("year").setValue(s.getYear());
        }
    }
}

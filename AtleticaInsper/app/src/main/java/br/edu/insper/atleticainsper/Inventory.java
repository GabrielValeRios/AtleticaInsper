package br.edu.insper.atleticainsper;

import android.os.CountDownTimer;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Inventory {

    private DatabaseReference database;

    private ArrayList<Product> products;

    public Inventory() {
        database = FirebaseDatabase.getInstance().getReference();
        products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        this.products.add(p);
    }

    public void removeProduct(String id) {
        for(Product p : this.products) {
            if(p.getId().equals(id)) {
                this.products.remove(p);
            }
        }
    }

    public void updateProduct(String id) {
        for(Product p : this.products) {
            if(p.getId().equals(id)) {

            }
        }
    }

    //Get database and update products list
    public void uploadToDatabase() {
        for (Product p : this.products) {
            database.child("products").child(String.valueOf(p.getId())).child("costPrice").setValue(p.getCostPrice());
            database.child("products").child(String.valueOf(p.getId())).child("criticalQty").setValue(p.getCriticalQty());
            database.child("products").child(String.valueOf(p.getId())).child("currentQty").setValue(p.getCurrentQty());
            database.child("products").child(String.valueOf(p.getId())).child("hasDiscount").setValue(p.hasDiscount());
            database.child("products").child(String.valueOf(p.getId())).child("isAvailable").setValue(p.isAvailable());
            database.child("products").child(String.valueOf(p.getId())).child("name").setValue(p.getName());
            database.child("products").child(String.valueOf(p.getId())).child("price").setValue(p.getPrice());
            database.child("products").child(String.valueOf(p.getId())).child("priceAtl").setValue(p.getPriceAtl());
            database.child("products").child(String.valueOf(p.getId())).child("priceSoc").setValue(p.getPriceSoc());
            database.child("products").child(String.valueOf(p.getId())).child("soldQty").setValue(p.getSoldQty());
        }
    }

    public ArrayList<Product> getProductList() {
        return products;
    }
}

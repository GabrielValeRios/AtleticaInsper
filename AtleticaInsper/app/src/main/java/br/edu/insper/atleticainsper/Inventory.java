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

    public ArrayList<Product> products = new ArrayList<>();

    public Inventory() {
        database = FirebaseDatabase.getInstance().getReference();
        refreshProductList();
        CountDownTimer timer = new CountDownTimer(10000,10000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.i("PRODUCT_LIST", String.valueOf(products));
            }
        };
        timer.start();
    }

    public int addProduct(Product p) {
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
        refreshProductList();

        return 1;
    }

    public int removeProduct(int id) {
        database.child("products").child(String.valueOf(id)).removeValue();
        refreshProductList();
        return 0;
    }

    //Get database and update products list
    public void refreshProductList() {
        database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Map<String, TreeMap<String, Object>> productMap = new TreeMap<>((HashMap) snapshot.getValue());

                products.clear();

                for(Map.Entry<String, TreeMap<String, Object>> entry : productMap.entrySet()) {

                    Map<String, Object> productParams = entry.getValue();
                    int id = Integer.parseInt(entry.getKey());
                    float costPrice = (float) (long) productParams.get("costPrice");
                    int criticalQty = (int) (long) productParams.get("criticalQty");
                    int currentQty = (int) (long) productParams.get("currentQty");
                    boolean hasDiscount = (boolean) productParams.get("hasDiscount");
                    boolean isAvailable = (boolean) productParams.get("isAvailable");
                    String name = (String) productParams.get("name");
                    float price = (float) (long) productParams.get("price");
                    float priceAtl = (float) (long) productParams.get("priceAtl");
                    float priceSoc = (float) (long) productParams.get("priceSoc");
                    int soldQty = (int) (long) productParams.get("soldQty");

                    Log.i("ID",String.valueOf(id));
                    Product p = new Product(id, costPrice, criticalQty, currentQty, hasDiscount, isAvailable, name, price, priceAtl, priceSoc, soldQty);
                    Log.i("produto",String.valueOf(p));
                    Log.i("produtoID",String.valueOf(p.getId()));
                    products.add(p);
                }

                Log.i("product", String.valueOf(products));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public ArrayList<Product> getProductList() {
        return products;
    }
}

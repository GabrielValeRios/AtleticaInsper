package br.edu.insper.atleticainsper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseFiller {

    private DatabaseReference database;

    public FirebaseFiller() {
        database = FirebaseDatabase.getInstance().getReference();
        fillDatabase();
    }

    public void addProduct(String productID, String name, String price, String priceAtl, String priceSoc, String hasDiscount, String critical_qty) {
        database.child("products").child(productID).child("available").setValue("false");
        database.child("products").child(productID).child("critical_qty").setValue(critical_qty);
        database.child("products").child(productID).child("current_qty").setValue("0");
        database.child("products").child(productID).child("hasDiscount").setValue(hasDiscount);
        database.child("products").child(productID).child("name").setValue(name);
        database.child("products").child(productID).child("price").setValue(price);
        database.child("products").child(productID).child("priceAtl").setValue(priceAtl);
        database.child("products").child(productID).child("priceSoc").setValue(priceSoc);
        database.child("products").child(productID).child("sold_qty").setValue("0");
    }

    public void fillDatabase() {
        addProduct("product001","Adesivo","5,00","5,00","5,00","true","5");
        addProduct("product002","Boné","70,00","60,00","50,00","false","5");
        addProduct("product003","Caderno","20,00","15,00","10,00","false","5");
        addProduct("product004","Camiseta Fem Atlética","30,00","25,00","20,00","true","5");
        addProduct("product005","Camiseta Fem Insper","30,00","25,00","20,00","true","5");
        addProduct("product006","Camiseta Fem Vinho","30,00","25,00","20,00","true","5");
        addProduct("product007","Camiseta Masc Atlética","30,00","25,00","20,00","true","5");
        addProduct("product008","Camiseta Masc Branca","30,00","25,00","20,00","true","5");
        addProduct("product009","Camiseta Masc Cinza","30,00","25,00","20,00","true","5");
        addProduct("product010","Camiseta Masc Insper","30,00","25,00","20,00","true","5");
        addProduct("product011","Caneca","10,00","10,00","10,00","true","5");
        addProduct("product012","Caneta","2,00","2,00","2,00","true","5");
        addProduct("product013","Chaveiro 15 Anos","5,00","5,00","5,00","true","5");
        addProduct("product014","Estojo","5,00","5,00","5,00","false","5");
        addProduct("product015","Lápis","2,00","2,00","2,00","true","5");
        addProduct("product016","Mala","80,00","70,00","60,00","false","5");
        addProduct("product017","Paleta Mexicana","8,00","8,00","8,00","true","5");
        addProduct("product018","Regata Fem Preta","30,00","25,00","20,00","true","5");
        addProduct("product019","Regata Fem Verm","30,00","25,00","20,00","true","5");
        addProduct("product020","Régua","2,00","2,00","2,00","false","5");
        addProduct("product021","Sacochila","10,00","10,00","10,00","true","5");
        addProduct("product022","Samba Canção Fem","30,00","25,00","20,00","true","5");
        addProduct("product023","Samba Canção Masc","30,00","25,00","20,00","true","5");
        addProduct("product024","Sorvete Fruta Nua","6,00","6,00","6,00","true","5");
        addProduct("product025","Squeeze Metálico","30,00","25,00","20,00","true","5");
    }
}

package br.edu.insper.atleticainsper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseFiller {

    private DatabaseReference database;

    public FirebaseFiller() {
        database = FirebaseDatabase.getInstance().getReference();
        fillDatabase();
    }

    public void addProduct(String productID, String name, String price, String priceAtl, String priceSoc, String hasDiscount) {
        database.child("products").child(productID).child("available").setValue("false");
        database.child("products").child(productID).child("current_qty").setValue("0");
        database.child("products").child(productID).child("name").setValue(name);
        database.child("products").child(productID).child("price").setValue(price);
        database.child("products").child(productID).child("priceAtl").setValue(priceAtl);
        database.child("products").child(productID).child("priceSoc").setValue(priceSoc);
        database.child("products").child(productID).child("sold_qty").setValue("0");
        database.child("products").child(productID).child("hasDiscount").setValue(hasDiscount);
    }

    public void fillDatabase() {
        addProduct("product001","Abadá","10,00","10,00","10,00","false");
        addProduct("product002","Adesivo","5,00","5,00","5,00","true");
        addProduct("product003","Bandoleira","10,00","10,00","10,00","false");
        addProduct("product004","Boné","70,00","60,00","50,00","false");
        addProduct("product005","Caderno","20,00","15,00","10,00","false");
        addProduct("product006","Calça","110,00","100,00","90,00","false");
        addProduct("product007","Camiseta Fem Atlética","30,00","25,00","20,00","true");
        addProduct("product008","Camiseta Fem Insper","30,00","25,00","20,00","true");
        addProduct("product009","Camiseta Fem Vinho","30,00","25,00","20,00","true");
        addProduct("product010","Camiseta Masc Atlética","30,00","25,00","20,00","true");
        addProduct("product011","Camiseta Masc Branca","30,00","25,00","20,00","true");
        addProduct("product012","Camiseta Masc Cinza","30,00","25,00","20,00","true");
        addProduct("product013","Camiseta Masc Insper","30,00","25,00","20,00","true");
        addProduct("product014","Caneca","10,00","10,00","10,00","true");
        addProduct("product015","Caneta","2,00","2,00","2,00","true");
        addProduct("product016","Chaveiro 15 Anos","5,00","5,00","5,00","true");
        addProduct("product017","Estojo","5,00","5,00","5,00","false");
        addProduct("product018","Lápis","2,00","2,00","2,00","true");
        addProduct("product019","Mala","80,00","70,00","60,00","false");
        addProduct("product020","Moletom","130,00","120,00","110,00","false");
        addProduct("product021","Paleta Mexicana","8,00","8,00","8,00","true");
        addProduct("product022","Regata Fem Preta","30,00","25,00","20,00","true");
        addProduct("product023","Regata Fem Verm","30,00","25,00","20,00","true");
        addProduct("product024","Régua","2,00","2,00","2,00","false");
        addProduct("product025","Sacochila","10,00","10,00","10,00","true");
        addProduct("product026","Samba Canção Fem","30,00","25,00","20,00","true");
        addProduct("product027","Samba Canção Masc","30,00","25,00","20,00","true");
        addProduct("product028","Sorvete Fruta Nua","6,00","6,00","6,00","true");
        addProduct("product029","Squeeze Metálico","30,00","25,00","20,00","true");
    }
}

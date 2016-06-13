package br.edu.insper.atleticainsper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    //Database reference (get database from URL defined in google-services.json)
    DatabaseReference database;

    //Screen elements
    Button cancel;
    Button add;
    EditText fieldName, fieldCriticalQty, fieldCurrentQty, fieldCostPrice, fieldPrice, fieldPriceAtl, fieldPriceSoc;

    //Product parameters to be added
    String name;
    boolean hasDiscount;
    String criticalQty;
    String currentQty;
    String costPrice;
    String price;
    String priceAtl;
    String priceSoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        database = FirebaseDatabase.getInstance().getReference();

        cancel = (Button) findViewById(R.id.cancel);
        add = (Button) findViewById(R.id.add);
        fieldName = (EditText) findViewById(R.id.addProductFormField1);
        fieldCriticalQty = (EditText) findViewById(R.id.addProductFormField2);
        fieldCurrentQty = (EditText) findViewById(R.id.addProductFormField3);
        fieldCostPrice = (EditText) findViewById(R.id.addProductFormField4);
        fieldPrice = (EditText) findViewById(R.id.addProductFormField5);
        fieldPriceAtl = (EditText) findViewById(R.id.addProductFormField6);
        fieldPriceSoc = (EditText) findViewById(R.id.addProductFormField7);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ManageProducts = new Intent(AddProductActivity.this, ManageProductsActivity.class);
                startActivity(ManageProducts);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel.setEnabled(false);
                add.setEnabled(false);

                name = fieldName.getText().toString();
                criticalQty = fieldCriticalQty.getText().toString();
                currentQty = fieldCurrentQty.getText().toString();
                costPrice = fieldCostPrice.getText().toString();
                price = fieldPrice.getText().toString();
                priceAtl = fieldPriceAtl.getText().toString();
                priceSoc = fieldPriceSoc.getText().toString();

                if (validateForm()) {

                    if (price == priceAtl) {
                        hasDiscount = false;
                    } else {
                        hasDiscount = true;
                    }

                    final String field1 = fieldName.getText().toString();
                    final float field2 = Integer.parseInt(fieldCriticalQty.getText().toString());
                    final float field3 = Integer.parseInt(fieldCurrentQty.getText().toString());
                    final float field4 = Float.parseFloat(fieldCostPrice.getText().toString());
                    final float field5 = Float.parseFloat(fieldPrice.getText().toString());
                    final float field6 = Float.parseFloat(fieldPriceAtl.getText().toString());
                    final float field7 = Float.parseFloat(fieldPriceSoc.getText().toString());

                    database.child("status").child("nextProductID").child("value").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String nextProductID = (String) snapshot.getValue();
                            int nextProductIdAsInteger = Integer.parseInt((String) snapshot.getValue());

                            DatabaseReference product = database.child("products").child(nextProductID);
                            product.child("costPrice").setValue(field4);
                            product.child("criticalQty").setValue(field2);
                            product.child("currentQty").setValue(field3);
                            product.child("hasDiscount").setValue(hasDiscount);
                            product.child("isAvailable").setValue(true);
                            product.child("name").setValue(field1);
                            product.child("price").setValue(field5);
                            product.child("priceAtl").setValue(field6);
                            product.child("priceSoc").setValue(field7);
                            product.child("soldQty").setValue(0);

                            if (nextProductIdAsInteger < 10) {
                                database.child("status").child("nextProductID").child("value").setValue("00" + (nextProductIdAsInteger + 1));
                            } else if (nextProductIdAsInteger > 10) {
                                database.child("status").child("nextProductID").child("value").setValue("0" + (nextProductIdAsInteger + 1));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            new AlertDialog.Builder(AddProductActivity.this)
                                    .setTitle("Produto adicionado!")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent ManageProducts = new Intent(AddProductActivity.this, ManageProductsActivity.class);
                                            startActivity(ManageProducts);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    }).show();
                        }
                    }.start();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public boolean validateForm() {

        Toast toast = Toast.makeText(AddProductActivity.this, "Por favor preencha os campos corretamente!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);

        if(name == null) {
            toast.setText("O nome do produto não pode estar em branco!");
            toast.show();
            return false;
        } else if(Integer.parseInt(criticalQty) <= 0) {
            toast.setText("A quantidade crítica não pode ser 0!");
            toast.show();
            return false;
        } else if(Integer.parseInt(currentQty) <= 0) {
            toast.setText("Um novo produto não pode ter quantidade 0!");
            toast.show();
            return false;
        } else if(Float.parseFloat(costPrice) <= 0) {
            toast.setText("O preço de custo não pode ser 0!");
            toast.show();
            return false;
        } else if(Float.parseFloat(price) <= 0) {
            toast.setText("O preço do produto não pode ser 0!");
            toast.show();
            return false;
        } else if(Float.parseFloat(priceAtl) <= 0) {
            toast.setText("O preço de atleta não poder 0! Se não há diferença para o preço normal, coloque o mesmo.");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return false;
        } else if(Float.parseFloat(priceSoc) <= 0) {
            toast.setText("O preço de sócio não pode ser 0! Se não há diferença para o preço normal, coloque o mesmo!");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
    }
}

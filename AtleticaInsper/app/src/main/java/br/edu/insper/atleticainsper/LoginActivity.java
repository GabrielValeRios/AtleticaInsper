package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LoginActivity extends AppCompatActivity {

    //Database reference (get database from URL defined in google-services.json)
    DatabaseReference database;

    //Product inventory
    Inventory inv;

    //Map for each user entry in database
    Map<String, HashMap<String, Object>> usersDB;

    //Screen elements
    LinearLayout loadingStatus;
    ImageView logo;
    EditText username;
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingStatus = (LinearLayout) findViewById(R.id.loadingStatus);
        logo = (ImageView) findViewById(R.id.logoAtletica);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        inv = new Inventory();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingStatus.setVisibility(View.VISIBLE);
                username.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                login.setVisibility(View.GONE);

                database = FirebaseDatabase.getInstance().getReference();

                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Map<String, HashMap<String, HashMap<String, Object>>> db = (HashMap) snapshot.getValue();
                        usersDB = new TreeMap<>((HashMap) db.get("users"));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //
                    }
                });

                CountDownTimer timer = new CountDownTimer(4000,4000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        String user = username.getText().toString();
                        String pass = password.getText().toString();
                        userLogin(user, pass);
                    }
                }.start();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
    }

    private int userLogin(String user, String pass) {

        for(Map.Entry<String, HashMap<String, Object>> entry : usersDB.entrySet()) {

            Map<String, Object> userParams = entry.getValue();

            boolean isAdmin = (boolean) userParams.get("isAdmin"); //boolean "true" or "false"
            String username = (String) userParams.get("user"); //string "xyz"
            String password = (String) userParams.get("pass").toString(); //string "xyz"
            String name = (String) userParams.get("name");

            if(username.equals(user) && password.equals(pass)) {
                if(isAdmin) {
                    Intent AdminMenu = new Intent(LoginActivity.this, AdminMenuActivity.class);
                    AdminMenu.putExtra("loggedUsername", name);
                    startActivity(AdminMenu);
                    return 0;
                } else {
                    Intent ProductsNormal = new Intent(LoginActivity.this, ProductsNormalActivity.class);
                    startActivity(ProductsNormal);
                    return 0;
                }
            }
        }

        Toast toast = Toast.makeText(LoginActivity.this, "Combinação incorreta de usuário e senha!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
        loadingStatus.setVisibility(View.GONE);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);


        return -1;
    }
}

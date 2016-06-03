package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //
    DatabaseReference database;
    //ImageView logoAtletica;
    EditText username;
    EditText password;
    Button login;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //logoAtletica = (ImageView) findViewById(R.id.logoAtletica);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);

        // [START Configure screen elements]
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        /*
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("okpressed","Ok pressed");
                }
                return false;
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("gustavo") && password.getText().toString().equals("1234")) {
                    Intent ProductsNormal = new Intent(LoginActivity.this, ProductsNormalActivity.class);
                    startActivity(ProductsNormal);
                } else if(username.getText().toString().equals("marcelo") && password.getText().toString().equals("1234")) {
                    Intent ManageMenu = new Intent(LoginActivity.this, ManageMenuActivity.class);
                    startActivity(ManageMenu);
                } else {
                    Toast.makeText(LoginActivity.this, "Combinação incorreta de usuário e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // [END Configure screen elements]
    }

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference();

        database.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                HashMap<String, HashMap<String, String>> users = (HashMap<String, HashMap<String, String>>) snapshot.getValue();
                Log.i("HASH_MAP", String.valueOf(users));

                for(Map.Entry<String, HashMap<String, String>> entry : users.entrySet()) {

                    Map<String, String> userParams = entry.getValue();

                    String isAdmin = userParams.get("admin"); //boolean "true" or "false"
                    String isMaster = userParams.get("master"); //boolean "true" or "false"
                    String username = userParams.get("user"); //string "xyz"
                    String password = userParams.get("pass"); //string "xyz"

                    Log.i("ACCOUNT_PARAM_IS_ADMIN",isAdmin);
                    Log.i("ACCOUNT_PARAM_IS_MASTER",isMaster);
                    Log.i("ACCOUNT_PARAM_USERNAME",username);
                    Log.i("ACCOUNT_PARAM_PASSWORD",password);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void userLogin(String username, String password) {

    }
}

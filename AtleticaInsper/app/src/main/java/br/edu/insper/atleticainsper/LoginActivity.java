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
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    //
    DatabaseReference database;
    //ImageView logoAtletica;
    EditText username;
    EditText password;
    Button login;
    TextView forgotPassword;
    HashMap<String, HashMap<String, Object>> users;

    //FirebaseFiller filler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //filler = new FirebaseFiller();

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

                String user = username.getText().toString();
                String pass = password.getText().toString();
                userLogin(user, pass);
            }
        });
        // [END Configure screen elements]
    }

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference();

        database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                users = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                //Log.i("HASH_MAP", String.valueOf(users));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private int userLogin(String user, String pass) {

        for(Map.Entry<String, HashMap<String, Object>> entry : users.entrySet()) {

            Map<String, Object> userParams = entry.getValue();

            boolean isAdmin = (boolean) userParams.get("admin"); //boolean "true" or "false"
            boolean isMaster = (boolean) userParams.get("master"); //boolean "true" or "false"
            String username = (String) userParams.get("user"); //string "xyz"
            String password = (String) userParams.get("pass").toString(); //string "xyz"

            if(username.equals(user) && password.equals(pass)) {
                if(isAdmin) {
                    Intent ManageMenu = new Intent(LoginActivity.this, ManageMenuActivity.class);
                    startActivity(ManageMenu);
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

        return -1;
    }
}

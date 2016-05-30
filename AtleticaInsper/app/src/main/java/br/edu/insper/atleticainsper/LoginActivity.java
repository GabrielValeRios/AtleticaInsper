package br.edu.insper.atleticainsper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        database = FirebaseDatabase.getInstance().getReference();
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
    public void onBackPressed() {
    }
}

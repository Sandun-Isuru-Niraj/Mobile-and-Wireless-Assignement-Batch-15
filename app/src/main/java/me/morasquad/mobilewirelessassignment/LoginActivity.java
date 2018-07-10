package me.morasquad.mobilewirelessassignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText indexText, passText;
    Button Login, Register;
    private SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        indexText = (EditText) findViewById(R.id.log_index);
        passText = (EditText) findViewById(R.id.log_password);
        Login = (Button) findViewById(R.id.log_login);
        Register = (Button) findViewById(R.id.log_register);

        sqLiteHelper = new SQLiteHelper(LoginActivity.this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String index = indexText.getText().toString();
                String password = passText.getText().toString();

                if(TextUtils.isEmpty(index)){
                    Toast.makeText(LoginActivity.this, "Index No needed!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Password needed!", Toast.LENGTH_SHORT).show();
                }else {
                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging User");
                    progressDialog.show();

                    Cursor cursor = sqLiteHelper.loginUser(index,password);

                    if(cursor.getCount() != 0){

                        progressDialog.dismiss();
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        login.putExtra("index", index);
                        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);

                        Toast.makeText(LoginActivity.this, "You are successfully Logged in!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}

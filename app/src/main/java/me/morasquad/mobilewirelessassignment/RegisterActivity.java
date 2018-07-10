package me.morasquad.mobilewirelessassignment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameText, indexText, emailText, mobileText, gpaText, passwordText, repassText;
    private Button Register, LoginReg;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sqLiteHelper = new SQLiteHelper(this);

        nameText = (EditText) findViewById(R.id.reg_name);
        indexText = (EditText) findViewById(R.id.reg_index);
        emailText = (EditText) findViewById(R.id.reg_email);
        mobileText = (EditText) findViewById(R.id.reg_mobile);
        gpaText = (EditText) findViewById(R.id.reg_gpa);
        passwordText = (EditText) findViewById(R.id.reg_password);
        repassText = (EditText) findViewById(R.id.reg_repassword);

        Register = (Button) findViewById(R.id.reg_signup);
        LoginReg = (Button) findViewById(R.id.reg_login);


        LoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                final String index = indexText.getText().toString();
                String email = emailText.getText().toString();
                String mobile = mobileText.getText().toString();
                String gpa = gpaText.getText().toString();
                String password = passwordText.getText().toString();
                String repassword = repassText.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this, "Name must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(index)){
                    Toast.makeText(RegisterActivity.this, "Index No must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Email Address must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(RegisterActivity.this, "Mobile Number must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(gpa)){
                    Toast.makeText(RegisterActivity.this, "GPA must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Password must be entered!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(repassword)){
                    Toast.makeText(RegisterActivity.this, "Password need to Confirmed!", Toast.LENGTH_SHORT).show();
                }else if(!validateIndex(index)){

                    Toast.makeText(RegisterActivity.this, "Invalid Index Number", Toast.LENGTH_SHORT).show();
                }else if(!validateGPA(gpa)){
                    Toast.makeText(RegisterActivity.this, "GPA is invalid enter value between 0 and 4.2", Toast.LENGTH_SHORT).show();
                }else if(!validateEmail(email)){

                    Toast.makeText(RegisterActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();

                }else if(validatePassword(password)){
                    Toast.makeText(RegisterActivity.this, "Password need to Minimum 8 Character Length", Toast.LENGTH_SHORT).show();
                }

                else if(!password.equals(repassword)){

                    Toast.makeText(RegisterActivity.this, "Passwords needed to Match!", Toast.LENGTH_SHORT).show();
                }else{

                    ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Registering User");
                    progressDialog.show();
                    boolean result = sqLiteHelper.RegisterUser(index, name, email, gpa, mobile, password);

                    if(result){
                        progressDialog.dismiss();
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choice) {
                                switch (choice) {
                                    case DialogInterface.BUTTON_POSITIVE:

                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        i.putExtra("index", index);
                                        startActivity(i);

                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("You have Successfully Registered!")
                                .setPositiveButton("OK", dialogClickListener).show();
                    }else {

                        Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

    private boolean validatePassword(String password) {

        if(password.length() < 8){
            return true;
        }else {
            return false;
        }
    }

    private boolean validateGPA(String gpa) {
        float value = Float.parseFloat(gpa);

        if(value >= 0 && value <= 4.2){
            return true;
        }else {
            return false;
        }

    }

    private boolean validateEmail(String email) {

        if(email.contains("@")){
            if(email.contains(".")){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    private boolean validateIndex(String index) {

        char[] array = index.toCharArray();

        if(array.length == 7){
            if(index.toUpperCase().startsWith("15")){
                if(Character.isLetter(array[6])){
                    if(Character.isDigit(array[2]) && Character.isDigit(array[3]) && Character.isDigit(array[4]) && Character.isDigit(array[5])){
                        return true;
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }

            }
            else {
                return false;
            }
        }else {
            return false;
        }

    }
}

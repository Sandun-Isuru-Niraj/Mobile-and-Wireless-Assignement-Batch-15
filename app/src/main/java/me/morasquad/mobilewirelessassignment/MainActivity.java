package me.morasquad.mobilewirelessassignment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView indexText,emailText,nameText,mobileText,gpaText;
    private Button emailMe, logOut;
    private SQLiteHelper sqLiteHelper;

    private String name,email,mobileNo,gpa,indexNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(MainActivity.this);


        indexText = (TextView) findViewById(R.id.main_index);
        emailText = (TextView) findViewById(R.id.main_email);
        nameText = (TextView) findViewById(R.id.main_name);
        mobileText = (TextView) findViewById(R.id.main_mobile);
        gpaText = (TextView) findViewById(R.id.main_gpa);

        emailMe = (Button) findViewById(R.id.main_emailme);
        logOut = (Button) findViewById(R.id.main_logout);


        final Intent intent = getIntent();
        indexNo = intent.getStringExtra("index");

        indexText.setText(indexNo);

        Cursor  cursor = sqLiteHelper.getUser(indexNo);

        if(cursor.moveToFirst()){
            do{

                email = cursor.getString(cursor.getColumnIndex("email"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                mobileNo = cursor.getString(cursor.getColumnIndex("mobile"));
                gpa = cursor.getString(cursor.getColumnIndex("GPA"));

            }while (cursor.moveToNext());
        }

        emailText.setText(email);
        nameText.setText(name);
        mobileText.setText(mobileNo);
        gpaText.setText(gpa);

        emailMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] emails = {""};
                emails[0] = "sandun@morasquad.me";

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Index Number : "+indexNo+"\nName : "+name+"\nMobile Number : "+mobileNo+"\nGPA : "+gpa);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Contact Details");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Toast.makeText(MainActivity.this, "Successfully Sent", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Toast.makeText(MainActivity.this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

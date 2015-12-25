package com.example.mohit.designtestapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.email_wrapper);
                String email = emailWrapper.getEditText().getText().toString();

                TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.password_wrapper);
                String password = passwordWrapper.getEditText().getText().toString();

                if (!validateEmail(email)) {
                    if(email.length() == 0) {
                        emailWrapper.setError("Required!");
                    } else {
                        emailWrapper.setError("Not a valid email address!");
                    }
                } else if(!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);

                    Snackbar.make(findViewById(R.id.main_layout), "Logged in!", Snackbar.LENGTH_LONG)
                            .setAction("Next", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(getApplicationContext(),NextActivity.class));
                                }
                            })
                            .show();
                }
            }
        });
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

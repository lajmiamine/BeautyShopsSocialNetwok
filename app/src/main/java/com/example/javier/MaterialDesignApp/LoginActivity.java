package com.example.javier.MaterialDesignApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters.StoresAdapter;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Stores;
import com.example.javier.MaterialDesignApp.Utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    String urlPost;
    int check;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        urlPost = "http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/login.php?pass="+password+"&email="+email;
        Log.d("url",urlPost);
        new AsyncTaskNewsParseJson().execute(urlPost);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public class AsyncTaskNewsParseJson extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost = url[0];
            try {
                JSONObject jsonObjectDesignPosts = JsonParser.readJsonFromUrl(urlPost);
                check = jsonObjectDesignPosts.getInt("result");
                if (check==0){

                }else {
                    JSONObject user=jsonObjectDesignPosts.getJSONObject("1");
                    int id=user.getInt("usr_id");
                    String name=user.getString("usr_name");
                    String username=user.getString("usr_email");
                    sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putInt("USERID", id);
                    editor.putString("NAME",name);
                    editor.putString("USERNAME",username);
                    editor.commit();
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            if (check==1){
                Toast.makeText(LoginActivity.this, "Login successful",
                        Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
            } else {
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                //this.finish();
            }
        }
    }

}

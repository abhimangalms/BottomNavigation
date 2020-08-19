package info.project.orion.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import info.project.orion.R;
import info.project.orion.url.Ip;

public class RegistrationActivity extends AppCompatActivity {

    TextView mLoginTextView;
    EditText muserameEditText, mEmailIdEditText, mPhoneNumberEditText, mCollegeNameEditText, mPasswordEditText;
    Button mRegisterButton;
    String userName, emailId, phoneNumber, accountNumber, collegeName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegisterButton = findViewById(R.id.registerButton);
        muserameEditText = findViewById(R.id.userameEditText);
        mEmailIdEditText = findViewById(R.id.emailIdEditText);
        mPhoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        mCollegeNameEditText = findViewById(R.id.collegeNameEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mLoginTextView = findViewById(R.id.loginTextView);

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = muserameEditText.getText().toString();
                emailId = mEmailIdEditText.getText().toString();
                phoneNumber = mPhoneNumberEditText.getText().toString();
                collegeName = mCollegeNameEditText.getText().toString();
                password = mPasswordEditText.getText().toString();


//              Calling registration api
                new SendRequest().execute();

            }
        });
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {
                SharedPreferences MyPref;
                SharedPreferences dta =getSharedPreferences("MyPref", MODE_PRIVATE);
                String id1=dta.getString("idd2",null);

                Ip i = new Ip();
                String ip = i.getIp();
                URL url = new URL( ip + "/register.php");
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("full_name_key", userName);
                postDataParams.put("email_id_key",emailId);
                postDataParams.put("phone_number_key",phoneNumber);
                postDataParams.put("account_number_key","000");
                postDataParams.put("branch_name_key", collegeName);
                postDataParams.put("password_key",password);

                Log.e("params", postDataParams.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                // connection.setRequestProperty("Content-Length", "" +
                //Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

//Send request
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                // wr.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();

//Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');

                }
                return response.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            //o=result;
            //   Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            int id=0;
            String str = result.trim();

            Toast.makeText(getApplicationContext(), "result is "+ str, Toast.LENGTH_SHORT).show();
            if(str.equals("1")) {
                Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
            //username.setText("");
            // passwordString.setText("");

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();

            //Toast.makeText(getApplicationContext(),key, Toast.LENGTH_LONG).show();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }

        return result.toString();
    }
}

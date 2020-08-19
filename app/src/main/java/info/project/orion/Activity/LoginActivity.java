package info.project.orion.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton, mRegisterButton;
    String emailIdString, passwordString;
    EditText mEmailIdEditText, mPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailIdEditText = findViewById(R.id.emailIdEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);

        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest().execute();
            }
        });
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                Ip i = new Ip();
                String ip = i.getIp();
                URL url = new URL(ip + "/login.php");
                JSONObject postDataParams = new JSONObject();

                emailIdString = mEmailIdEditText.getText().toString();
                passwordString = mPasswordEditText.getText().toString();


                postDataParams.put("emailId", emailIdString);
                postDataParams.put("password", passwordString);


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
            int id=0;
            String str = result.trim();
            //Toast.makeText(LoginPage.this,result,Toast.LENGTH_SHORT).show();
            if(!str.equals("0"))
            {
                id= Integer.parseInt(str);
            }
            Toast.makeText(getApplicationContext(),"id:"+ id, Toast.LENGTH_SHORT).show();
            if (id>0) {

                //storing user id
                SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editr = pref.edit();
                editr.putString("userIdAfterLogin", str);
                editr.commit();


//                startActivity(intent);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            else {

                Toast.makeText(getApplicationContext(),"Invalid username or passwordString", Toast.LENGTH_SHORT).show();
            }

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

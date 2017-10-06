package saud.abdulrhman;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saud.abdulrhman.myapp.JSONParser;
import saud.abdulrhman.myapp.R;

public class AddStudintActivity extends AppCompatActivity {

    Button button;
    EditText name,email,mobile;
    JSONParser jsonParser;
    ProgressDialog progressDialog ;
    int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_studint);
        button=(Button)findViewById(R.id.addButton);
        name=(EditText)findViewById(R.id.NameEditText);
        email=(EditText)findViewById(R.id.EmailEditText);
        mobile=(EditText)findViewById(R.id.MoblieEditText);
        jsonParser = new JSONParser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddStudentTask().execute();
            }
        });

    }

    @SuppressWarnings("deprecation")
    class AddStudentTask extends AsyncTask<String,String,String>{




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String...strings) {
            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("Name",name.getText().toString()));
            list.add(new BasicNameValuePair("Email",email.getText().toString()));
            list.add(new BasicNameValuePair("Mobile",mobile.getText().toString()));

            JSONObject jsonObject=jsonParser.makeHttpRequest("http://192.168.56.1/myapp/add_studen.php","POST",list);

            try{
               if(jsonObject !=null && ! jsonObject.isNull("value") ){
                       value =jsonObject.getInt("value");

               }else{
                   value=0;
               }

            }catch (JSONException e) {
               Log.d("error",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            if(value==1){
                Toast.makeText(getApplicationContext(),"done...",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),"Erorr...",Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
        }


    }
}

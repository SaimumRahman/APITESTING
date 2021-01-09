package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Model_Class.DefaultResponse;
import com.example.apitesting.R;
import API.RetrofitClient;

import Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private EditText  editTextEmails, editTextPasswords,editTextNames,editTextSchools;
private TextView  textViewLogins;
private Button buttonSignUps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmails=findViewById(R.id.editTextEmail);
        editTextPasswords=findViewById(R.id.editTextPassword);
        editTextNames=findViewById(R.id.editTextName);
        editTextSchools=findViewById(R.id.editTextSchool);
      buttonSignUps=findViewById(R.id.buttonSignUpus);
        textViewLogins=findViewById(R.id.textViewLogin);

      buttonSignUps.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            SignUp();
              //Toast.makeText(MainActivity.this,"Please enter the email",Toast.LENGTH_LONG).show();
          }
      });

        textViewLogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).IsLoggedIn()){
            Intent  intent=new Intent(this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void SignUp() {

        String email=editTextEmails.getText().toString();
        String password=editTextPasswords.getText().toString();
        String name=editTextNames.getText().toString();
        String school=editTextSchools.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
        }
      else   if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(MainActivity.this,"Please enter correct email",Toast.LENGTH_SHORT).show();
        }
      else   if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
        }
//      else   if(password.length() < 6){
//            Toast.makeText(MainActivity.this,"Password must be greater than 6",Toast.LENGTH_SHORT).show();
//        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(name)){
            Toast.makeText(MainActivity.this,"Please enter the name",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(school)){
            Toast.makeText(MainActivity.this,"Please enter the school",Toast.LENGTH_SHORT).show();
        }

        else {

            Call<DefaultResponse>call= RetrofitClient.getInstance()
                    .getApi()
                    .createUsers(email,password,name,school);

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if(response.code() == 201){
                        DefaultResponse dr=response.body();
                        Toast.makeText(MainActivity.this,dr.getMsg(),Toast.LENGTH_SHORT).show();
                    }else if(response.code()==422){
                        Toast.makeText(MainActivity.this,"User Already Exists",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });



        }


    }
}
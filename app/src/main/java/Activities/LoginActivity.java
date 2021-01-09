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

import com.example.apitesting.R;

import API.RetrofitClient;
import Model_Class.LoginResponse;
import Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmaillogins, editTextPasswordlogins;
    private TextView textViewRegisters;
    private Button buttonLogins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmaillogins=findViewById(R.id.editTextEmaillogin);
        editTextPasswordlogins=findViewById(R.id.editTextPasswordlogin);
        textViewRegisters=findViewById(R.id.textViewRegister);
        buttonLogins=findViewById(R.id.buttonLogin);
        textViewRegisters=findViewById(R.id.textViewRegister);

        buttonLogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        textViewRegisters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).IsLoggedIn()){
            Intent  intent=new Intent(LoginActivity.this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void Login() {

        String  emailLog=editTextEmaillogins.getText().toString();
        String passwordLog=editTextPasswordlogins.getText().toString();

        if(TextUtils.isEmpty(emailLog)){
            Toast.makeText(LoginActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
        }
        else   if(!Patterns.EMAIL_ADDRESS.matcher(emailLog).matches()){
            Toast.makeText(LoginActivity.this,"Please enter correct email",Toast.LENGTH_SHORT).show();
        }
        else   if(TextUtils.isEmpty(passwordLog)){
            Toast.makeText(LoginActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
        }
        else {

            Call<LoginResponse>loginResponseCall= RetrofitClient.getInstance()
                    .getApi().userLogins(emailLog,passwordLog);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse=response.body();
                    if(!loginResponse.isError()){

                        SharedPrefManager.getInstance(LoginActivity.this)
                                .saveUser(loginResponse.getUser());

                        Intent  intent=new Intent(LoginActivity.this,ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else {
                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });

        }

    }
}
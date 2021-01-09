package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apitesting.R;

import Model_Class.User;
import Storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
private TextView welcometxts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        welcometxts=findViewById(R.id.welcometxt);
        User  users= SharedPrefManager.getInstance(this).getUser();
        welcometxts.setText("Welcome Back"+users.getName());
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).IsLoggedIn()){
            Intent intent=new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
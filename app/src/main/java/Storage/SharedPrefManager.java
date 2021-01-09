package Storage;

import android.content.Context;
import android.content.SharedPreferences;

import Model_Class.User;

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES="MY_PREF";
    private static SharedPrefManager mInstance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){

        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
     }
     public void saveUser(User user){

        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt("id",user.getId());
        editor.putString("email",user.getEmail());
        editor.putString("name",user.getName());
        editor.putString("school",user.getSchool());

        editor.apply();

     }

     public boolean IsLoggedIn(){
         SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
         return sharedPreferences.getInt("id",-1)!=-1;
     }

     public User getUser(){

         SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
         User users=new User(

                 sharedPreferences.getInt("id",-1),
                 sharedPreferences.getString("email",null),
                 sharedPreferences.getString("name",null),
                 sharedPreferences.getString("school",null)
         );
         return users;
     }

     public void clear(){

         SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
         editor.clear();
         editor.apply();

     }

}

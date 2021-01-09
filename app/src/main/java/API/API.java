package API;

import Model_Class.DefaultResponse;

import Model_Class.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUsers(

        @Field("email") String email,
        @Field("password") String password,
        @Field("name") String name,
        @Field("school") String school

    );

    @FormUrlEncoded
    @POST("userlogin")

    Call<LoginResponse>userLogins(
            @Field("email") String email,
            @Field("password") String password
    );

}

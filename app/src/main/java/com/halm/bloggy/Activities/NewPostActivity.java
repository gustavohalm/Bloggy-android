package com.halm.bloggy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.Models.Profile;
import com.halm.bloggy.Models.ProfileToken;
import com.halm.bloggy.Models.Token;
import com.halm.bloggy.Models.User;
import com.halm.bloggy.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Calendar;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity {
    private EditText editTitle, editContent;
    private String title, content;
    private User user;
    private Profile profile;
    private Token token;
    private SharedPreferences preferences;
    private Post post, postResp;
    private String username, first_name, last_name, email;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        editTitle  = findViewById(R.id.editNewTitulo);
        editContent = findViewById(R.id.editNewContent);


        preferences = getSharedPreferences("auth_token_bloggy_api", getApplicationContext().MODE_PRIVATE);
        token = new Token();
        token.setToken( preferences.getString("the_token_user", "0") );
        username = preferences.getString("the_username_user", "");
        first_name = preferences.getString("the_firstname_user", "");
        last_name = preferences.getString("the_lastname_user", "");
        email = preferences.getString("the_email_use", "");
        id= preferences.getInt("the_id_user", 0);

        user = new User(id, username, first_name, last_name, email);
        profile = new Profile();
        profile.setAge(preferences.getString("the_age_user", ""));
        profile.setGender(preferences.getInt("the_gender_user", 9));
        profile.setUser(user.getId());
        profile.setId(preferences.getInt("the_profile_user", 0));
    }

    public void postar(View view)
    {
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        Date currentTime = Calendar.getInstance().getTime();
        String create_date = currentTime.toString();

        post = new Post(title, content, create_date   );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DATAService service = retrofit.create(DATAService.class);

        Call<Post> call = service.adicionarPost( "Token " + token.getToken(), post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful())
                {
                    postResp = response.body();
                    Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    try {
                        Toast.makeText(NewPostActivity.this, "Favor, tente novamente" + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(NewPostActivity.this, "Erro ao Postar: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}

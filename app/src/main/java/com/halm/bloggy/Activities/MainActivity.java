package com.halm.bloggy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.halm.bloggy.Adapters.AdpPost;
import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.Models.Profile;
import com.halm.bloggy.Models.ProfileToken;
import com.halm.bloggy.R;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvPosts;
    private List<Post> listPosts;
    private AdpPost adpPost;
    private String token_user;
    private SharedPreferences preferences;
    private List<Profile> profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvPosts = findViewById(R.id.rcvPosts);

        preferences = getSharedPreferences("auth_token_bloggy_api", getApplicationContext().MODE_PRIVATE);
        token_user = preferences.getString("the_token_user", "null");
        //region Realizer requisição de posts e logged user

        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DATAService service = retrofit.create(DATAService.class);
        Call<List<Profile>> callProfile = service.recuperarProfileToken("Token " +token_user);

        callProfile.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if(response.isSuccessful())
                {
                    profile = response.body();
                    Toast.makeText(MainActivity.this, " Profile: "+ profile.get(0).getId(), Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("the_profile_user", profile.get(0).getId());
                    editor.apply();

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Erro ao recuperar o Profile: "+ response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Falha ao recuperar PRofile: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        Call<List<Post>> call  = service.recuperarPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                listPosts = response.body();

                RecyclerView.LayoutManager manager = new LinearLayoutManager( MainActivity.this );
                adpPost = new AdpPost(listPosts);
                rcvPosts.setHasFixedSize(true);

                rcvPosts.setLayoutManager( manager );
                rcvPosts.setAdapter( adpPost );

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


        //endregion


    }


    public void novoPost(View view)
    {
        Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
        startActivity(intent);
    }

}

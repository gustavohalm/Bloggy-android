package com.halm.bloggy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Profile;
import com.halm.bloggy.Models.ProfileToken;
import com.halm.bloggy.Models.Token;
import com.halm.bloggy.Models.User;
import com.halm.bloggy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPaswword;
    private User user;
    private Token token;
    private SharedPreferences preferences;
    private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editLoginUsername);
        editPaswword = findViewById(R.id.editLoginPassword);
        preferences = getSharedPreferences("auth_token_bloggy_api", MODE_PRIVATE);
        String auth_test = preferences.getString("the_token_user", "0");

        if( auth_test != "0")
        {
            //testar se o token é verdadeiro

            //..
            if(false)
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    public void logar(View view)
    {
        String username;
        String password;

        username = editUsername.getText().toString();
        password = editPaswword.getText().toString();

        user = new User(username, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DATAService service = retrofit.create(DATAService.class);

        Call<Token> call = service.autenticacao(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call,  Response<Token> response) {
                if(response.isSuccessful())
                {
                    token = response.body();
                    //Toast.makeText(LoginActivity.this, "Token: " + token.getToken(), Toast.LENGTH_SHORT).show();


                    preferences = getSharedPreferences("auth_token_bloggy_api", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("the_token_user", token.getToken());
                    editor.putString("the_username_user", user.getUsername());
                    editor.commit();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Deu Ruim" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Falhou, olha só o erro" + t.getMessage() ,Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cadastre(View view)
    {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);

    }
}

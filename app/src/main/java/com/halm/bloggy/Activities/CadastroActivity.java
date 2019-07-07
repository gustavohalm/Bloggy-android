package com.halm.bloggy.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Profile;
import com.halm.bloggy.Models.User;
import com.halm.bloggy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editLastName;
    private EditText editUsername;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editNascimento;
    private RadioGroup radioSexo;
    private int sexo;
    private String nascimento;
    private User user, userR;
    private Profile profile, profileR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editEmail = findViewById(R.id.editCadastroEmail);
        editName = findViewById(R.id.editCadastroName);
        editLastName = findViewById(R.id.editCadastroLastName);
        editUsername = findViewById(R.id.editCadastroUsername);
        editNascimento = findViewById(R.id.editCadastroNasc);
        editPassword = findViewById(R.id.editCadastroPassword);
        radioSexo = findViewById(R.id.radioSexo);
    }

    public void cadastrar(View view)
    {
        //region ATRIBUTOS User & Profile
        String username = editUsername.getText().toString();
        String name = editName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        nascimento = editNascimento.getText().toString();
        String[] nasc= nascimento.split("/");
        nascimento = nasc[2] + "-" + nasc[1] + "-" + nasc[0];

        if(radioSexo.getCheckedRadioButtonId() == R.id.radioCadastroMasc )
            sexo = 1;
        else
            sexo = 0;



        //endregion


        user = new User( username, name, lastName, email, password );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final DATAService service = retrofit.create(DATAService.class);


        //region CADASTRAR User
        Call<User> callUser = service.cadastrarUser(user);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> callUser, final Response<User> response) {
                if (response.isSuccessful())
                {

                    userR = response.body();

                    //region CADASTRAR Profile
                    profile = new Profile( nascimento, sexo, userR.getId());
                    Call<Profile> callProfile = service.cadastrarProfile(profile);
                    callProfile.enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> callProfile, Response<Profile> responseP) {
                            if(responseP.isSuccessful())
                            {
                                profileR = responseP.body();

                                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(CadastroActivity.this, "Erro(profile), código: " + responseP.message(), Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<Profile> call, Throwable t) {
                                 Toast.makeText(CadastroActivity.this, "Erro :"+ t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    //endregion

                }
                else
                {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar, tente novamente" + response.message()+ " / " + response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        //endregion





    }
}

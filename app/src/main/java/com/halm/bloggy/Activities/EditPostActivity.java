package com.halm.bloggy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPostActivity extends AppCompatActivity {
    private int postId;
    private EditText editTitle;
    private EditText editContent;
    private Post post;
    private SharedPreferences preferences;
    private String user_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        editTitle = findViewById(R.id.editUpdateTitle);
        editContent = findViewById(R.id.editUpdateContent);
        preferences = getSharedPreferences("post_update", getApplicationContext().MODE_PRIVATE);
        user_token = preferences.getString("the_token_user", "");
        postId = preferences.getInt("the_post_id_update", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DATAService service = retrofit.create(DATAService.class);

        Call<Post> call = service.recuperarPost(postId);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful())
                {
                    post = response.body();
                    editTitle.setText(post.getTitle(), EditText.BufferType.EDITABLE);
                    editContent.setText(post.getContent(), EditText.BufferType.EDITABLE );
                }
                else
                {
                    Toast.makeText(EditPostActivity.this, "Erro, tente novamente.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditPostActivity.this, PostActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void atualizar(View view)
    {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        DATAService service = retrofit.create(DATAService.class);

        post.setContent( editContent.getText().toString());
        post.setTitle(editTitle.getText().toString());

        Call<Post> call = service.alterarPost("Token "+user_token, postId, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful())
                {
                    post= new Post();
                    post = response.body();
                    Toast.makeText(EditPostActivity.this, "Post atualizado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditPostActivity.this, PostActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(EditPostActivity.this, "Erro ao atualizar, código: " + response.code()+" / " + user_token, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(EditPostActivity.this, "Falha ao atualizar: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}

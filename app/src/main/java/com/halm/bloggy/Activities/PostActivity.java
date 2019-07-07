package com.halm.bloggy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.halm.bloggy.Adapters.AdpComment;
import com.halm.bloggy.Apis.DATAService;
import com.halm.bloggy.Models.Comment;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class PostActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private TextView txtTitle;
    private TextView txtData;
    private TextView txtContent;
    private  TextView editPost;
    private RecyclerView rcvComments;
    private AdpComment adpComment;
    private Post post;
    int id;
    int userid;
    private List<Comment> commentList;
    private EditText editname;
    private EditText editContent;
    private  SharedPreferences preferences;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        txtTitle = findViewById(R.id.postTitle);
        txtContent = findViewById(R.id.postContent);
        txtData = findViewById(R.id.postData);
        editPost = findViewById(R.id.editPost);
        rcvComments = findViewById(R.id.rcvComments);
        editname = findViewById(R.id.editNameComment);
        editContent= findViewById(R.id.editComment);

        //recuperar id do post;
        prefs = getSharedPreferences("sharedIdPost", getApplicationContext().MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        preferences = getSharedPreferences("auth_token_bloggy_api", getApplicationContext().MODE_PRIVATE);
        userid = preferences.getInt("the_profile_user", 0);
        token = preferences.getString("the_token_user", "");
        if(id != 0)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DATAService service = retrofit.create(DATAService.class);

            Call<Post> call = service.recuperarPost(id);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                     if(response.isSuccessful())
                     {
                         post = response.body();
                         txtTitle.setText( post.getTitle() );
                         txtContent.setText( post.getContent() );
                         if( post.getAuthor() == userid )
                             editPost.setVisibility(View.VISIBLE);

                     }
                     else
                     {
                         Toast.makeText(PostActivity.this, "Não foi possivel carregar esse Post, tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(PostActivity.this, MainActivity.class);
                         startActivity(intent);
                     }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Toast.makeText(PostActivity.this, "Não foi possivel carregar esse Post, tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            //Recuperar comentário

            Call<List<Comment>> callCometarios =service.recuperarCometarios(id);

            callCometarios.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    commentList = response.body();
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(PostActivity.this);
                    rcvComments.setHasFixedSize(true);

                    rcvComments.setLayoutManager(manager);
                    adpComment = new AdpComment(commentList);
                    rcvComments.setAdapter(adpComment);
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {

                }
            });

        }



        }
        public void enviarComentario(View v)
        {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://bloggy.gustavohalm.com/api/v0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DATAService service = retrofit.create(DATAService.class);
            String nome = editname.getText().toString();
            String comentario = editContent.getText().toString();
            Comment comment = new Comment( nome,id, comentario);

            Call<Comment> addComment = service.adicionarComentario(comment, id);
            addComment.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {

                    if(response.isSuccessful())
                    {
                        Toast.makeText( PostActivity.this, "Comentario   Adcionado" , Toast.LENGTH_SHORT ).show();
                        Intent intent =  PostActivity.this.getIntent();
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText( PostActivity.this, "Comentario  not Adcionado" + response.code() , Toast.LENGTH_SHORT ).show();

                    }
                }


                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText( PostActivity.this, "Não foi possivel adicionar comentario" + t.toString(), Toast.LENGTH_SHORT ).show();

                }
            });
        }

        public void updatePost(View view)
        {
            preferences = getSharedPreferences("post_update", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("the_post_id_update", id);
            editor.putString("the_token_user", token);
            editor.commit();
            Intent intent = new Intent(PostActivity.this, EditPostActivity.class);
            startActivity(intent);

        }

}


package com.halm.bloggy.Apis;

import android.provider.ContactsContract;
import android.widget.ProgressBar;

import  com.halm.bloggy.Models.Comment;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.Models.Profile;
import com.halm.bloggy.Models.ProfileToken;
import com.halm.bloggy.Models.Token;
import com.halm.bloggy.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DATAService {

    @GET("posts/")
    Call<List<Post>> recuperarPosts();

    @GET("posts/{id}")
    Call<Post> recuperarPost(@Path("id") int id);

    @POST("posts/")
    Call<Post> adicionarPost(@Header("authorization") String token , @Body Post post);

    @PUT("posts/{id}/")
    Call<Post> alterarPost(@Header("authorization") String token,@Path("id") int id, @Body Post post );

    @GET("post/comments")
    Call<List<Comment>> recuperarCometarios(@Query("p") int id);

    @POST("post/comments/")
    Call<Comment> adicionarComentario(@Body Comment comment, @Query("p") int id);


    @POST("api-auth/")
    Call<Token> autenticacao(@Body User user);

    @POST("users/")
    Call<User> cadastrarUser(@Body User user);

    @POST("cadastro/profile/")
    Call<Profile> cadastrarProfile(@Body Profile profile);


    @GET("user-token/")
    Call<List<Profile>> recuperarProfileToken(@Header("authorization") String token);

}

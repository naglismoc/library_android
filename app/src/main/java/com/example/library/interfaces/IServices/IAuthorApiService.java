package com.example.library.interfaces.IServices;


import com.example.library.entities.Author;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAuthorApiService {
    @GET("api/authors")
    Call<List<Author>> getAuthors();

    // New API call to get an author
    @GET("api/authors/{id}")
    Call<Author> getAuthor();

    // New API call to create an author
    @POST("api/authors")
    Call<Author> createAuthor(@Body Author author);

    // New API call to update an author
    @PUT("api/authors/{id}")
    Call<Author> updateAuthor(@Path("id") long id, @Body Author author);

    // New API call to delete an author
    @DELETE("api/authors/{id}")
    Call<Void> deleteAuthor(@Path("id") long id);
}

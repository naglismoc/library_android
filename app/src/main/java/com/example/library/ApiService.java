package com.example.library;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/authors")
    Call<List<Author>> getAuthors();
}

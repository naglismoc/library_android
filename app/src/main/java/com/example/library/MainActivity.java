package com.example.library;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.adapters.AuthorAdapter;
import com.example.library.databinding.ActivityMainBinding;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.interfaces.IServices.IAuthorApiService;
import com.example.library.interfaces.AuthorActionListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AuthorActionListener {


    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private AuthorAdapter adapter;
    private IAuthorApiService IAuthorApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://127.0.0.1:8070/")  // Use correct base URL for emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IAuthorApiService = retrofit.create(IAuthorApiService.class);
            adapter = new AuthorAdapter(this,new ArrayList<>(),this);
            binding.listViewAuthors.setAdapter(adapter);


        // Set click listener for the FAB
        binding.fabAddAuthor.setOnClickListener(v -> showEditDialog(null));

        fetchAuthors();
    }

    private void fetchAuthors() {
        IAuthorApiService.getAuthors().enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update the adapter with the fetched authors
                    adapter.clear();
                    adapter.addAll(response.body());
                } else {
                    showError("Failed to load data. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                showError("Error: " + t.getMessage());
                Log.e(TAG, "Fetch authors failed", t);
            }
        });
    }


    private void createAuthor(Author author) {
        IAuthorApiService.createAuthor(author).enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchAuthors();  // Refresh the list to show the new author
                } else {
                    showError("Failed to create author. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                showError("Error: " + t.getMessage());
                Log.e(TAG, "Create author failed", t);
            }
        });
    }

    private void deleteAuthor(long id) {
        IAuthorApiService.deleteAuthor(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchAuthors();  // Refresh the list to remove the deleted author
                } else {
                    showError("Failed to delete author. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showError("Error: " + t.getMessage());
                Log.e(TAG, "Delete author failed", t);
            }
        });
    }


    private void updateAuthor(long id, Author author) {
        IAuthorApiService.updateAuthor(id, author).enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchAuthors();  // Refresh the list to show the updated author
                } else {
                    showError("Failed to update author. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                showError("Error: " + t.getMessage());
                Log.e(TAG, "Update author failed", t);
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Clean up binding when the activity is destroyed
    }


    @Override
    public void onEdit(Author author) {
        showEditDialog(author);
    }

    @Override
    public void onDelete(long authorId) {
        deleteAuthor(authorId);
    }
    private void showEditDialog(Author author) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_author, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextSurname = dialogView.findViewById(R.id.editTextSurname);

        if (author != null) {
            editTextName.setText(author.getName());
            editTextSurname.setText(author.getSurname());
        }

        builder.setTitle(author == null ? "Create Author" : "Edit Author");

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String surname = editTextSurname.getText().toString();
            if (author == null) {
                createAuthor(new Author(0, name, surname,new ArrayList<Book>()));
            } else {
                author.setName(name);
                author.setSurname(surname);
                updateAuthor(author.getId(), author);
            }
            dialog.dismiss();
        });

        dialog.show();
    }
}

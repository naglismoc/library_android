package com.example.library.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.entities.Author;
import com.example.library.interfaces.AuthorActionListener;

import java.util.ArrayList;

public class AuthorAdapter extends ArrayAdapter<Author> {

    private final AuthorActionListener listener;
    public AuthorAdapter(Context context, ArrayList<Author> authors, AuthorActionListener listener) {
        super(context, 0, authors);
        this.listener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Author author = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_author, parent, false);
        }
        // Lookup view for data population
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewSurname = convertView.findViewById(R.id.textViewSurname);
        Button buttonEdit = convertView.findViewById(R.id.buttonEdit);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

        // Populate the data into the template view using the data object
        textViewName.setText(author.getName());
        textViewSurname.setText(author.getSurname());

        buttonEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(author);
            }
        });

        buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(author.getId());
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
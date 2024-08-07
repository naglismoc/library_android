package com.example.library;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AuthorAdapter extends ArrayAdapter<Author> {

    public AuthorAdapter(Context context, ArrayList<Author> authors) {
        super(context, 0, authors);
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

        // Populate the data into the template view using the data object
        textViewName.setText(author.getName());
        textViewSurname.setText(author.getSurname());

        // Return the completed view to render on screen
        return convertView;
    }
}
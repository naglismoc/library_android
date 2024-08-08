package com.example.library.interfaces;

import com.example.library.entities.Author;

public interface AuthorActionListener {
    void onEdit(Author author);
    void onDelete(long authorId);
}
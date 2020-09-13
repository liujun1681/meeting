package com.kuang.service;

import com.kuang.dao.BookMapper;
import com.kuang.pojo.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookMapper bookMapper;
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }



    public int addBook(Book book) {
        return bookMapper.addBook( book);
    }

    public int deleteBook(int id) {
        return bookMapper.deleteBook(id);
    }

    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    public List<Book> seleteAllBook() {
        return bookMapper.seleteAllBook();
    }

    public Book selectBookById(int id) {
        return bookMapper.selectBookById(id);
    }
}

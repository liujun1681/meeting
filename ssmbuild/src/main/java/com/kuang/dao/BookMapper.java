package com.kuang.dao;

import com.kuang.pojo.Book;

import java.util.List;

public interface BookMapper {
//    增加一本书
    int addBook(Book book);
    //    删除一本书
    int deleteBook(int id);
    //    增加一本书
    int updateBook(Book book);
    //    增加一本书
    List<Book> seleteAllBook();

    Book selectBookById(int id);

}

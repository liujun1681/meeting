package com.kuang.controller;

import com.kuang.pojo.Book;
import com.kuang.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

    @Controller
    @RequestMapping("/book")
    public class BookController {
        @Autowired
        @Qualifier("BookServiceImpl")
        private BookService bookService;

        @RequestMapping("/allBook")
        public String list(Model model) {
            List<Book> list = bookService.seleteAllBook();
            model.addAttribute("list", list);
            return "allBook";
        }
        @RequestMapping("/toAddBook")
        public String toAddPaper() {
            return "addBook";
        }

        @RequestMapping("/addBook")
        public String addPaper(Book books) {
            System.out.println(books);
            bookService.addBook(books);
            return "redirect:/book/allBook";
        }
        @RequestMapping("/toUpdateBook")
        public String toUpdateBook(Model model, int id) {
            Book books = bookService.selectBookById(id);
            System.out.println(books);
            model.addAttribute("book",books );
            return "updateBook";
        }

        @RequestMapping("/updateBook")
        public String updateBook(Model model, Book book) {
            System.out.println(book);
            bookService.updateBook(book);
            Book books = bookService.selectBookById(book.getBookId());
            model.addAttribute("books", books);
            return "redirect:/book/allBook";
        }

        @RequestMapping("/del/{bookId}")
        public String deleteBook(@PathVariable("bookId") int id) {
            bookService.deleteBook(id);
            return "redirect:/book/allBook";
        }
    }


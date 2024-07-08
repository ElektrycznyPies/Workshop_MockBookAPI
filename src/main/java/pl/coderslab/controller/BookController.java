package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.Book;

import java.util.List;
import java.util.Optional;

@RestController // równoznaczna z ResponseBody
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

// LISTA Z UŻYCIEM RESPONSEENTITY, POZWALA ZOBACZYĆ ODP. SERWERA 200 OK
    @RequestMapping("")
    @ResponseBody
    public ResponseEntity<List<Book>> getList() {
        List<Book> books = bookService.getBooks();
        return ResponseEntity.ok(books);
   }

// KLASYCZNA LISTA
//    @RequestMapping("")
//    @ResponseBody
//    public List<Book> getList() {
//        List<Book> books = bookService.getBooks();
//        return books;
//    }

    @RequestMapping("/books/{id}")
    @ResponseBody
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.get(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return ResponseEntity.ok(book); // zwraca książkę ze statusem 200 OK
        } else {
            return ResponseEntity.notFound().build(); // zwraca status 404 Not Found
        }
    }

    @PostMapping("")
    public void addBook(@RequestBody Book book) { // @RB potrzebne, by przerobić json na obiekt. Dodajemy json curlem
        bookService.add(book);
    }
//SKŁADNIA DLA CURLA:
// curl -X POST -i -H "Content-Type: application/json" -d '{"isbn":"34321","title":"Thinking in Java", "publisher":"Helion","type":"programming", "author":"Bruce Eckel"}' http://localhost:8080/books

    @RequestMapping("/hello")
    @ResponseBody
    public Book helloBook() {
        return new Book(1L, "9788324631766", "Thinking in Java",
                "Bruce Eckel", "Helion", "programming");
    }
}

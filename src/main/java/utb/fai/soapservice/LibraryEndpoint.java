package utb.fai.soapservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.book_web_service.Author;
import com.example.book_web_service.Book;
import com.example.book_web_service.GetBookRequest;
import com.example.book_web_service.GetBookResponse;
import com.example.book_web_service.CreateBookRequest;
import com.example.book_web_service.CreateBookResponse;
import com.example.book_web_service.UpdateBookRequest;
import com.example.book_web_service.UpdateBookResponse;

import utb.fai.soapservice.Model.AuthorPersistent;
import utb.fai.soapservice.Model.BookPersistent;

import com.example.book_web_service.DeleteBookRequest;
import com.example.book_web_service.DeleteBookResponse;
import com.example.book_web_service.GetAuthorRequest;
import com.example.book_web_service.GetAuthorResponse;
import com.example.book_web_service.CreateAuthorRequest;
import com.example.book_web_service.CreateAuthorResponse;
import com.example.book_web_service.DeleteAuthorRequest;
import com.example.book_web_service.DeleteAuthorResponse;

@Endpoint
public class LibraryEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/book-web-service";

    @Autowired
    private LibraryService libraryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) {
        GetBookResponse response = new GetBookResponse();
        BookPersistent bookPersistent = libraryService.getBook(request.getBookId());
        if (bookPersistent == null) {
            response.setMessage("ERROR: Not found");
            return response;
        }
        Book book = new Book();
        book.setAuthorID(bookPersistent.getAuthor().getId());
        book.setId(bookPersistent.getId());
        book.setTitle(bookPersistent.getTitle());
        response.setBook(book);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        CreateBookResponse response = new CreateBookResponse();
        Book book = request.getBook();
        if (book.getTitle().isEmpty()) {
            response.setMessage("ERROR: Not found");
            return response;
        }

        BookPersistent bookPersistent = new BookPersistent();
        AuthorPersistent author = libraryService.getAuthor(book.getAuthorID());
        if (author == null) {
            response.setMessage("ERROR: Not found");
            return response;
        }
        bookPersistent.setAuthor(author);
        bookPersistent.setId(book.getId());
        bookPersistent.setTitle(book.getTitle());
        libraryService.createBook(bookPersistent);
        response.setBook(book);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBookRequest")
    @ResponsePayload
    public UpdateBookResponse updateBook(@RequestPayload UpdateBookRequest request) {
        Book book = request.getBook();
        if (book.getTitle().isEmpty())
            return null;
        BookPersistent bookPersistent = libraryService.getBook(book.getId());
        if (bookPersistent == null)
            return null;
        AuthorPersistent author = libraryService.getAuthor(book.getAuthorID());
        if (author == null || book.getTitle().isEmpty())
            return null;
        bookPersistent.setAuthor(author);
        bookPersistent.setTitle(book.getTitle());
        libraryService.createBook(bookPersistent);
        UpdateBookResponse response = new UpdateBookResponse();
        response.setBook(book);
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) {
        DeleteBookResponse response = new DeleteBookResponse();
        if (libraryService.getBook(request.getBookId()) == null) {
            response.setMessage("ERROR: Not found");
            return response;
        }
        libraryService.deleteBook(request.getBookId());
        response.setMessage("DELETED");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthorRequest")
    @ResponsePayload
    public GetAuthorResponse getAuthor(@RequestPayload GetAuthorRequest request) {
        GetAuthorResponse response = new GetAuthorResponse();
        AuthorPersistent authorPersistent = libraryService.getAuthor(request.getAuthorId());
        if (authorPersistent == null) {
            response.setMessage("ERROR: Not found");
            return response;
        }
        Author author = new Author();
        author.setId(authorPersistent.getId());
        author.setName(authorPersistent.getName());
        author.setSurname(authorPersistent.getSurname());
        response.setAuthor(author);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAuthorRequest")
    @ResponsePayload
    public CreateAuthorResponse createAuthor(@RequestPayload CreateAuthorRequest request) {
        CreateAuthorResponse response = new CreateAuthorResponse();
        Author author = request.getAuthor();
        if (author.getName() == null || author.getName().isEmpty() ||
                author.getSurname() == null || author.getSurname().isEmpty() ||
                author.getName().contains(" ") || author.getSurname().contains(" ")) {
            response.setMessage("ERROR: Validation");
            return response;
        }
        AuthorPersistent authorPersistent = new AuthorPersistent();
        authorPersistent.setId(author.getId());
        authorPersistent.setName(author.getName());
        authorPersistent.setSurname(author.getSurname());
        libraryService.createAuthor(authorPersistent);
        response.setAuthor(author);
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAuthorRequest")
    @ResponsePayload
    public DeleteAuthorResponse deleteAuthor(@RequestPayload DeleteAuthorRequest request) {
        DeleteAuthorResponse response = new DeleteAuthorResponse();
        if (libraryService.getAuthor(request.getAuthorId()) == null) {
            response.setMessage("ERROR: Not found");
            return response;
        }
        libraryService.deleteAuthor(request.getAuthorId());
        response.setMessage("DELETED");
        return response;
    }

    // TODO: define this requests updateBookRequest, deleteBookRequest,
    // getAuthorRequest, createAuthorRequest, deleteAuthorRequest

}

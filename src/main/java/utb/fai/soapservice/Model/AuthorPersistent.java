package utb.fai.soapservice.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Entita autora, ktera je ukladana do databaze
 */
@Entity
public class AuthorPersistent {

    @Id
    private Long id;

    private String name;

    private String surname;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookPersistent> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BookPersistent> getBooks() {
        return books;
    }

    public void setBooks(List<BookPersistent> books) {
        this.books = books;
    }

}

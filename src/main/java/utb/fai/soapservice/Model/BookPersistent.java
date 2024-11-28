package utb.fai.soapservice.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;

/**
 * Entita knihy, ktera je ukladana do databaze
 */
@Entity
public class BookPersistent {

    @Id
    private Long id;

    private String title;
    @ManyToOne
    private AuthorPersistent author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorPersistent getAuthor() {
        return author;
    }

    public void setAuthor(AuthorPersistent author) {
        this.author = author;
    }

}

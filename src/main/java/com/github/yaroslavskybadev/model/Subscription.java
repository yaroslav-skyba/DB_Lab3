package com.github.yaroslavskybadev.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_subscription",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private final List<Book> bookList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id.equals(that.id)
                && reader.equals(that.reader)
                && registrationDate.equals(that.registrationDate)
                && expirationDate.equals(that.expirationDate)
                && bookList.equals(that.bookList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reader, registrationDate, expirationDate, bookList);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", reader=" + reader +
                ", registrationDate=" + registrationDate +
                ", expirationDate=" + expirationDate +
                ", bookList=" + bookList +
                '}';
    }
}

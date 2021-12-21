package com.github.yaroslavskybadev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Min(1)
    @Column(name = "page_count")
    private Integer pageCount;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private final List<Author> authorList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "book_subscription",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private final List<Subscription> subscriptionList = new ArrayList<>();

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

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void addAuthor(Author author) {
        authorList.add(author);
    }

    public void removeAuthor(Author author) {
        authorList.remove(author);
    }

    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void addSubscription(Subscription subscription) {
        subscriptionList.add(subscription);
    }

    public void removeSubscription(Subscription subscription) {
        subscriptionList.remove(subscription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Book book = (Book) o;

        return id.equals(book.id)
                && name.equals(book.name)
                && Objects.equals(pageCount, book.pageCount)
                && authorList.equals(book.authorList)
                && subscriptionList.equals(book.subscriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pageCount, authorList, subscriptionList);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pageCount=" + pageCount +
                ", authorList=" + authorList +
                ", subscriptionList=" + subscriptionList +
                '}';
    }
}

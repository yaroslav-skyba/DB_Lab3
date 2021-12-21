package com.github.yaroslavskybadev.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "second_name", nullable = false, length = 50)
    private String secondName;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Subscription> subscriptionList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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

        final Reader reader = (Reader) o;

        return id.equals(reader.id)
                && firstName.equals(reader.firstName)
                && secondName.equals(reader.secondName)
                && subscriptionList.equals(reader.subscriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, subscriptionList);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", subscriptionList=" + subscriptionList +
                '}';
    }
}

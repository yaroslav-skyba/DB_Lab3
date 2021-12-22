package com.github.yaroslavskybadev;

import com.github.yaroslavskybadev.model.Author;
import com.github.yaroslavskybadev.model.Book;
import com.github.yaroslavskybadev.model.Reader;
import com.github.yaroslavskybadev.model.Subscription;
import com.github.yaroslavskybadev.repository.AuthorRepository;
import com.github.yaroslavskybadev.repository.BookRepository;
import com.github.yaroslavskybadev.repository.ReaderRepository;
import com.github.yaroslavskybadev.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class View {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String BOOK_NAMES_SEPARATOR = ";";
    private static final String KEY_TO_STOP_ADDING_ENTITIES = "s";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public View(AuthorRepository authorRepository,
                BookRepository bookRepository,
                ReaderRepository readerRepository,
                SubscriptionRepository subscriptionRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void runMenu() {
        SCANNER.useDelimiter("\n");

        while (true) {
            System.out.println();
            System.out.println("Press c - to create an entity");
            System.out.println("Press ri - to read an entity by id");
            System.out.println("Press ra - to read all entities");
            System.out.println("Press u - to update an entity");
            System.out.println("Press d - to delete an entity");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an action: ");

            switch (SCANNER.next()) {
                case "c":
                    createEntity();
                    break;
                case "ri":
                    readEntityById();
                    break;
                case "ra":
                    readAllEntities();
                    break;
                case "u":
                    updateEntity();
                    break;
                case "d":
                    deleteEntity();
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("ERROR. An incorrect action");
            }
        }
    }

    @Transactional
    void createEntity() {
        while (true) {
            System.out.println();
            System.out.println("Press a - to create an author");
            System.out.println("Press b - to create a book");
            System.out.println("Press r - to create a reader");
            System.out.println("Press s - to create a subscription");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an entity: ");

            switch (SCANNER.next()) {
                case "a":
                    authorRepository.saveAndFlush(readAuthor());
                    break;
                case "b":
                    bookRepository.saveAndFlush(readBook());
                    break;
                case "r":
                    readerRepository.saveAndFlush(readReader());
                    break;
                case "s":
                    subscriptionRepository.saveAndFlush(readSubscription());
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("ERROR. An incorrect entity type");
                    continue;
            }

            System.out.println("An entity has been successfully created");

            break;
        }
    }

    @Transactional
    void readEntityById() {
        while (true) {
            System.out.println();
            System.out.println("Press a - to read an author");
            System.out.println("Press b - to read a book");
            System.out.println("Press r - to read a reader");
            System.out.println("Press s - to read a subscription");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an entity: ");

            switch (SCANNER.next()) {
                case "a":
                    final long authorId = readId("An author");

                    try {
                        printAuthor(authorRepository.findById(authorId).orElseThrow());
                    } catch (NoSuchElementException exception) {
                        System.out.println("ERROR. An author with id=" + authorId + " does not exist");
                        continue;
                    }

                    break;
                case "b":
                    final long bookId = readId("A book");

                    try {
                        printBook(bookRepository.findById(bookId).orElseThrow());
                    } catch (NoSuchElementException exception) {
                        System.out.println("ERROR. A book with id=" + bookId + " does not exist");
                        continue;
                    }

                    break;
                case "r":
                    final long readerId = readId("A reader");

                    try {
                        printReader(readerRepository.findById(readerId).orElseThrow());
                    } catch (NoSuchElementException exception) {
                        System.out.println("ERROR. A reader with id=" + readerId + " does not exist");
                        continue;
                    }

                    break;
                case "s":
                    final long subscriptionId = readId("A subscription");

                    try {
                        printSubscription(subscriptionRepository.findById(subscriptionId).orElseThrow());
                    } catch (NoSuchElementException exception) {
                        System.out.println("ERROR. A subscription with id=" + subscriptionId + " does not exist");
                        continue;
                    }

                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("ERROR. An incorrect entity type");
                    continue;
            }

            break;
        }
    }

    @Transactional
    void readAllEntities() {
        while (true) {
            System.out.println();
            System.out.println("Press a - to read authors");
            System.out.println("Press b - to read books");
            System.out.println("Press r - to read readers");
            System.out.println("Press s - to read subscriptions");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an entity: ");

            switch (SCANNER.next()) {
                case "a":
                    for (Author author : authorRepository.findAll()) {
                        System.out.println("Id: " + author.getId());
                        printAuthor(author);
                        System.out.println();
                    }

                    break;
                case "b":
                    for (Book book : bookRepository.findAll()) {
                        System.out.println("Id: " + book.getId());
                        printBook(book);
                        System.out.println();
                    }

                    break;
                case "r":
                    for (Reader reader : readerRepository.findAll()) {
                        System.out.println("Id: " + reader.getId());
                        printReader(reader);
                        System.out.println();
                    }

                    break;
                case "s":
                    for (Subscription subscription : subscriptionRepository.findAll()) {
                        System.out.println("Id: " + subscription.getId());
                        printSubscription(subscription);
                        System.out.println();
                    }

                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("ERROR. Incorrect entity type");
                    continue;
            }

            break;
        }
    }

    @Transactional
    void updateEntity() {
        while (true) {
            System.out.println();
            System.out.println("Press a - to update an author");
            System.out.println("Press b - to update a book");
            System.out.println("Press r - to update a reader");
            System.out.println("Press s - to update a subscription");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an entity: ");

            switch (SCANNER.next()) {
                case "a":
                    final long authorId = readId("An author");

                    if (!authorRepository.existsById(authorId)) {
                        System.out.println("ERROR. An author with id=" + authorId + " does not exist");
                        continue;
                    }

                    final Author author = readAuthor();
                    author.setId(authorId);

                    authorRepository.saveAndFlush(author);

                    break;
                case "b":
                    final long bookId = readId("A book");

                    if (!bookRepository.existsById(bookId)) {
                        System.out.println("ERROR. A book with id=" + bookId + " does not exist");
                        continue;
                    }

                    final Book book = readBook();
                    book.setId(bookId);

                    bookRepository.saveAndFlush(book);

                    break;
                case "r":
                    final long readerId = readId("A reader");

                    if (!readerRepository.existsById(readerId)) {
                        System.out.println("ERROR. A reader with id=" + readerId + " does not exist");
                        continue;
                    }

                    final Reader reader = readReader();
                    reader.setId(readerId);

                    readerRepository.saveAndFlush(reader);

                    break;
                case "s":
                    final long subscriptionId = readId("A subscription");

                    if (!subscriptionRepository.existsById(subscriptionId)) {
                        System.out.println("ERROR. A subscription with id=" + subscriptionId + " does not exist");
                        continue;
                    }

                    final Subscription subscription = readSubscription();
                    subscription.setId(subscriptionId);

                    subscriptionRepository.saveAndFlush(subscription);

                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("ERROR. An incorrect entity type");
                    continue;
            }

            System.out.println("An entity has been successfully update");

            break;
        }
    }

    @Transactional
    void deleteEntity() {
        while (true) {
            System.out.println();
            System.out.println("Press a - to delete an author");
            System.out.println("Press b - to delete a book");
            System.out.println("Press r - to delete a reader");
            System.out.println("Press s - to delete a subscription");
            System.out.println("Press q - to quit the program");
            System.out.println();

            System.out.print("Choose an entity: ");

            switch (SCANNER.next()) {
                case "a":
                    authorRepository.deleteById(readId("An author"));
                    break;
                case "b":
                    bookRepository.deleteById(readId("A book"));
                    break;
                case "r":
                    readerRepository.deleteById(readId("A reader"));
                    break;
                case "s":
                    subscriptionRepository.deleteById(readId("A subscription"));
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("Incorrect entity type. Try again.");
                    continue;
            }

            System.out.println("An entity has been successfully deleted");

            break;
        }
    }

    private Author readAuthor() {
        final Author author = new Author();

        System.out.print("An author first name: ");
        author.setFirstName(SCANNER.next());

        System.out.print("An author second name: ");
        author.setSecondName(SCANNER.next());

        do {
            final long bookId = readId("A book");
            final Book book = readBook();
            book.setId(bookId);

            author.addBook(book);

            System.out.println("\nPress " + KEY_TO_STOP_ADDING_ENTITIES + " - to stop adding books");
            System.out.print("Continue: ");
        } while (!KEY_TO_STOP_ADDING_ENTITIES.equals(SCANNER.next()));

        return author;
    }

    private Book readBook() {
        final Book book = new Book();

        System.out.print("A book name: ");
        book.setName(SCANNER.next());

        while (true) {
            System.out.print("A book page count: ");

            final int pageCount;
            final String errorMessage = "This field should be a positive int";

            try {
                pageCount = SCANNER.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println(errorMessage);
                SCANNER.next();
                System.out.println();

                continue;
            }

            if (pageCount > 0) {
                book.setPageCount(pageCount);
                break;
            } else {
                System.out.println(errorMessage + "\n");
            }
        }

        return book;
    }

    private Reader readReader() {
        final Reader reader = new Reader();

        System.out.print("A reader first name: ");
        reader.setFirstName(SCANNER.next());

        System.out.print("A reader second name: ");
        reader.setSecondName(SCANNER.next());

        return reader;
    }

    private Subscription readSubscription() {
        final Subscription subscription = new Subscription();

        subscription.setReader(readReader());

        while (true) {
            System.out.print("A subscription registration date (yyyy-[m]m-[d]d): ");

            try {
                subscription.setRegistrationDate(Date.valueOf(SCANNER.next()));
                break;
            } catch (IllegalArgumentException exception) {
                System.out.println("This field should be a date\n");
            }
        }

        while (true) {
            System.out.print("A subscription expiration date (yyyy-[m]m-[d]d): ");

            try {
                subscription.setExpirationDate(Date.valueOf(SCANNER.next()));
                break;
            } catch (IllegalArgumentException exception) {
                System.out.println("This field should be a date\n");
            }
        }

        do {
            subscription.addBook(readBook());

            System.out.println("\nPress " + KEY_TO_STOP_ADDING_ENTITIES + " - to stop adding books");
            System.out.print("Continue: ");
        } while (!KEY_TO_STOP_ADDING_ENTITIES.equals(SCANNER.next()));

        return subscription;
    }

    private void printAuthor(Author author) {
        System.out.println("First name: " + author.getFirstName());
        System.out.println("Second name: " + author.getSecondName());
        System.out.println("Books: ");
        author.getBookList().forEach(this::printBook);
    }

    private void printBook(Book book) {
        System.out.println("Name: " + book.getName());
        System.out.println("Page count: " + book.getPageCount());
    }

    private void printReader(Reader reader) {
        System.out.println("First name: " + reader.getFirstName());
        System.out.println("Second name: " + reader.getSecondName());
    }

    private void printSubscription(Subscription subscription) {
        final Reader subscriptionReader = subscription.getReader();
        System.out.println("Reader first name: " + subscriptionReader.getFirstName());
        System.out.println("Reader second name: " + subscriptionReader.getSecondName());
        System.out.println("Registration date: " + subscription.getRegistrationDate());
        System.out.println("Expiration date: " + subscription.getExpirationDate());
        System.out.println("Books: ");
        subscription.getBookList().forEach(this::printBook);
    }

    private long readId(String entityName) {
        while (true) {
            System.out.print(entityName + " id: ");

            final long id;
            final String errorMessage = "This field should be a positive int";

            try {
                id = SCANNER.nextLong();
            } catch (InputMismatchException exception) {
                System.out.println(errorMessage);
                SCANNER.next();
                System.out.println();

                continue;
            }

            if (id > 0) {
                return id;
            } else {
                System.out.println(errorMessage + "\n");
            }
        }
    }
}

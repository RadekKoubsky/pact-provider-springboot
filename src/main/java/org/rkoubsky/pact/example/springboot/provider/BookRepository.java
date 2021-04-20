package org.rkoubsky.pact.example.springboot.provider;

import java.time.LocalDate;

import org.rkoubsky.pact.example.springboot.provider.rest.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookRepository {

    public BookDto getBook(String isbn) {
        return BookDto.builder()
                      .title("Designing Data-Intensive Applications")
                      .author("Martin Kleppmann")
                      .isbn("978-1449373320")
                      .published(LocalDate.of(2019, 4, 18))
                      .build();
    }
}

package org.rkoubsky.pact.example.springboot.provider.rest;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookDto {

    private final String title;
    private final String author;
    private final String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate published;
}

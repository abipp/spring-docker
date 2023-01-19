package com.winterhold.service;

import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {

    List<AuthorHeaderDTO> findAllAuthorName();

    Page<AuthorHeaderDTO> findAllAuthor(int page, String fullName);

    UpsertAuthorDTO getAuthorById(Long id);

    Long saveAuthor(UpsertAuthorDTO authorDto);

    Boolean deleteAuthorById(Long id);

    Long depedentBook(Long id);

    Page<Book> findAllBookByAuthor(Long id, int page);

}

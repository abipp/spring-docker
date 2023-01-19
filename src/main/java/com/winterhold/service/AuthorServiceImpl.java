package com.winterhold.service;

import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Author;
import com.winterhold.entity.Book;
import com.winterhold.repository.AuthorRepository;
import com.winterhold.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    private final int rowsInPage = 5;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AuthorHeaderDTO> findAllAuthorName() {
        return AuthorHeaderDTO.toList(authorRepository.findAll());
    }

    @Override
    public Page<AuthorHeaderDTO> findAllAuthor(int page, String fullName) {
        Pageable pagination = PageRequest.of((page - 1), rowsInPage, Sort.by("id"));
        return authorRepository.findAllAuthor(fullName, pagination);
    }

    @Override
    public UpsertAuthorDTO getAuthorById(Long id) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isPresent()){
            Author author = byId.get();
            return new UpsertAuthorDTO(author.getId(),
                    author.getTitle(),
                    author.getFirstName(),
                    author.getLastName(),
                    author.getBirthDate(),
                    author.getDeceasedDate(),
                    author.getEducation(),
                    author.getSummary());
        }
        return null;
    }

    @Override
    public Long saveAuthor(UpsertAuthorDTO authorDto) {
        Author author = new Author(authorDto.getId(),
                authorDto.getTitle(),
                authorDto.getFirstName(),
                authorDto.getLastName(),
                authorDto.getBirthDate(),
                authorDto.getDeceasedDate(),
                authorDto.getEducation(),
                authorDto.getSummary());
        Author response = authorRepository.save(author);
        return response.getId();
    }

    @Override
    public Boolean deleteAuthorById(Long id) {
        Long totalBooks = depedentBook(id);
        if (totalBooks == 0){
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long depedentBook(Long id) {
        return bookRepository.countByAuthorId(id);
    }

    @Override
    public Page<Book> findAllBookByAuthor(Long id, int page) {
        return bookRepository.findByAuthor_Id(id, PageRequest.of(page - 1, rowsInPage, Sort.by("code")));
    }
}

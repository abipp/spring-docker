package com.winterhold.service;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.UpsertBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    Page<Category> findAllCategory(Integer page, String name);

    UpsertCategoryDTO getCategoryById(String name);

    void saveCategory(UpsertCategoryDTO upsertCategoryDTO);

    Boolean deleteCategoryById(String name);

    Long countBookByCategoryId(String name);

    Page<Book> findAllBookByCategory(String category, String title, String fullName, int page);

    UpsertBookDTO getBookById(String code);

    void saveBook(UpsertBookDTO booksGridDto);

    Boolean deleteBook(String code);

    List<BookGridDTO> findAllBookAvailable();

    Long countLoanByBookId(String code);

    Boolean checkExistingBook(String code);

    Boolean checkExistingCategory(String name);
}

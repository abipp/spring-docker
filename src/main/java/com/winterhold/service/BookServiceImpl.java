package com.winterhold.service;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.UpsertBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Author;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import com.winterhold.repository.AuthorRepository;
import com.winterhold.repository.BookRepository;
import com.winterhold.repository.CategoryRepository;
import com.winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements  BookService{

    private BookRepository bookRepository;

    private CategoryRepository categoryRepository;

    private AuthorRepository authorRepository;

    private LoanRepository loanRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           AuthorRepository authorRepository,
                           LoanRepository loanRepository){
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.loanRepository = loanRepository;
    }

    private final int rowsInPage = 10;

    @Override
    public Page<Category> findAllCategory(Integer page, String name) {
        Pageable pageable = PageRequest.of(page - 1, rowsInPage, Sort.by("name"));
        return categoryRepository.findByNameContaining(name, pageable);
    }

    @Override
    public UpsertCategoryDTO getCategoryById(String name) {
        Optional<Category> byId = categoryRepository.findById(name);
        UpsertCategoryDTO upsertCategoryDTO = new UpsertCategoryDTO();
        if (byId.isPresent()){
            Category category = byId.get();
            return new UpsertCategoryDTO(
                    category.getName(),
                    category.getFloor(),
                    category.getIsle(),
                    category.getBay()
            );
        }
        return upsertCategoryDTO;
    }

    @Override
    public void saveCategory(UpsertCategoryDTO upsertCategoryDTO) {
        Category category = new Category(
                upsertCategoryDTO.getId(),
                upsertCategoryDTO.getFloor(),
                upsertCategoryDTO.getIsle(),
                upsertCategoryDTO.getBay()
        );
        categoryRepository.save(category);
    }

    @Override
    public Boolean deleteCategoryById(String name) {
        Long totalBook = countBookByCategoryId(name);
        if (totalBook == 0)  {
            categoryRepository.deleteById(name);
            return true;
        }
        return false;
    }

    @Override
    public Long countBookByCategoryId(String name) {
        return bookRepository.countByCategoryName(name);
    }

    @Override
    public Page<Book> findAllBookByCategory(String category, String title, String fullName, int page) {
        return bookRepository.
                findByCategoryNameAndTitlendAuthorName(category, title, fullName,
                        PageRequest.of(page - 1, rowsInPage, Sort.by("code")));
    }

    @Override
    public UpsertBookDTO getBookById(String code) {
        Optional<Book> byId = bookRepository.findById(code);
        if (byId.isPresent()){
            Book book = byId.get();
            return new UpsertBookDTO(
                    book.getCode(),
                    book.getTitle(),
                    book.getCategoryName(),
                    book.getAuthorId(),
                    book.getReleaseDate(),
                    book.getTotalPage(),
                    book.getSummary()
            );
        }
        return new UpsertBookDTO();
    }

    @Override
    public void saveBook(UpsertBookDTO booksGridDto) {
        Author author = authorRepository.findById(booksGridDto.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("Author dengan ID tersebut tidak ditemukan"));

        Category category = categoryRepository.findById(booksGridDto.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category dengan nama tersebut tidak ditemukan"));

        Book book = new Book(
                booksGridDto.getCode(),
                booksGridDto.getTitle(),
                category.getName(),
                author.getId(),
                booksGridDto.getSummary(),
                booksGridDto.getReleaseDate(),
                booksGridDto.getTotalPage());
        bookRepository.save(book);
    }

    @Override
    public Boolean deleteBook(String code) {
        Long totalLoan = countLoanByBookId(code);
        if (totalLoan == 0){
            bookRepository.deleteById(code);
            return true;
        }
        return false;
    }

    @Override
    public List<BookGridDTO> findAllBookAvailable() {
        return bookRepository.findAllBookAvailable();
    }

    @Override
    public Long countLoanByBookId(String code) {
        return loanRepository.countByBookCode(code);
    }

    @Override
    public Boolean checkExistingBook(String code) {
        return null;
    }

    @Override
    public Boolean checkExistingCategory(String name) {
        return null;
    }

}

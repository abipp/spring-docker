package com.winterhold.repository;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findByAuthor_Id(Long id, Pageable pageable);

//    Page<Book> findByCategoryName_idAndTitleContainingAndAuthor_fullNameContaining(String category, String title, String fullName, Pageable pageable);
    @Query("""
            SELECT b
            FROM Book AS b
            WHERE b.categoryName = :category
                AND b.title LIKE %:title%
                AND b.author.fullName LIKE %:fullName%
            """)
    Page<Book> findByCategoryNameAndTitlendAuthorName(@Param("category") String category,
                                                                       @Param("title") String title,
                                                                       @Param("fullName") String fullName, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(b.code, b.title, b.isBorrowed)
            FROM Book AS b
            WHERE b.isBorrowed = false""")
    List<BookGridDTO> findAllBookAvailable();

    @Query("""
            SELECT b
            FROM Book AS b
                INNER JOIN b.loans AS l
            WHERE l.id = ?1
            """)
    List<Book> findByLoansId(Long id);


    @Query("""
            SELECT COUNT(b.code)
            FROM Book AS b
            WHERE b.categoryName = :categoryName
            """)
    Long countByCategoryName(@Param("categoryName") String categoryName);

    @Query("""
            SELECt COUNT(b.code)
            FROM Book AS b
            WHERE b.authorId = :id
            """)
    Long countByAuthorId(@Param("id") Long id);

    @Query("""
            SELECT COUNT(b.code)
            FROM Book AS b
            WHERE b.code = :code
            """)
    Long counBookByCode(@Param("code") String code);
}

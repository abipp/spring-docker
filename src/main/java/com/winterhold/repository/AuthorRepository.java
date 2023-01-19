package com.winterhold.repository;

import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
            SELECT new com.winterhold.dto.author.AuthorHeaderDTO(
                a.id, a.title, CONCAT(a.title, ' ',a.firstName , ' ', a.lastName), 
                a.birthDate,
                a.deceasedDate,
                a.education, a.summary
            )
            FROM Author AS a
            WHERE CONCAT(a.firstName, ' ', a.lastName) LIKE %:fullName%
            """)
    Page<AuthorHeaderDTO> findAllAuthor(@Param("fullName") String fullName, Pageable pageable);
}

package com.winterhold.rest;

import com.winterhold.dto.author.AuthorBooksGridDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Book;
import com.winterhold.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping(value={
            "",
            "/page={page}",
            "/name={name}",
            "/page={page}&name={name}"
    })
    public ResponseEntity<?> get(@PathVariable(required = false) Integer page, @PathVariable(required = false) String name){
        page = (page == null) ? 1 : page;
        name = (name == null) ? "" : name;
        try{
            Page<AuthorHeaderDTO> allAuthors = authorService.findAllAuthor(page, name);
            List<AuthorGridDTO> authorGrid = AuthorGridDTO.convert(allAuthors.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(allAuthors);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
            "/books={id}",
            "/books={id}&page={page}"
    })
    public ResponseEntity<Object> getBook(@PathVariable(required = false) Integer page, @PathVariable(required = false) Long id){
        page = (page == null) ? 1 : page;
        try{
            Page<Book> allBooks = authorService.findAllBookByAuthor(id, page);
            List<AuthorBooksGridDTO> authorGrid = AuthorBooksGridDTO.convert(allBooks.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(authorGrid);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0L);
                Long respondId = authorService.saveAuthor(dto);
                dto.setId(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                authorService.saveAuthor(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable() Long id){
        try{
            authorService.deleteAuthorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}

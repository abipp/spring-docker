package com.winterhold.rest;

import com.winterhold.dto.author.AuthorBooksGridDTO;
import com.winterhold.dto.book.UpsertBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import com.winterhold.service.AuthorService;
import com.winterhold.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping(value={
            "",
            "/page={page}",
            "/title={title}",
            "/page={page}&title={title}"
    })
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page, @PathVariable(required = false) String title){
        page = (page == null) ? 1 : page;
        title = (title == null) ? "" : title;
        try{
            Page<Category> categories = bookService.findAllCategory(page, title);
            List<CategoryGridDTO> categoryDto = CategoryGridDTO.toList(categories.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                bookService.saveCategory(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                bookService.saveCategory(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable() String id){
        try{
            bookService.deleteCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
            "/detail/category={category}",
            "/detail/category={category}&page={page}",
            "/detail/category={category}&title={title}",
            "/detail/category={category}&fullName={fullName}",
            "/detail/category={category}&page={page}&title={title}",
            "/detail/category={category}&page={page}&fullName={fullName}",
            "/detail/category={category}&title={title}&fullName={fullName}",
            "/detail/category={category}&page={page}&title={title}&fullName={fullName}",
    })
    public ResponseEntity<Object> getDetail(
            @PathVariable() String category,
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) String title,
            @PathVariable(required = false) String fullName){
        page = (page == null) ? 1 : page;
        title = (title == null) ? "" : title;
        fullName = (fullName == null) ? "" : fullName;
        try{
            Page<Book> allBooks = bookService.findAllBookByCategory(category, title, fullName, page);
            List<AuthorBooksGridDTO> bookGrid = AuthorBooksGridDTO.convert(allBooks.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(bookGrid);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping("/detail/category={category}")
    public ResponseEntity<Object> post(
            @Valid @RequestBody UpsertBookDTO dto,
            BindingResult bindingResult,
            @PathVariable() String category){
        try{
            if(!bindingResult.hasErrors()){
                dto.setCategoryName(category);
                bookService.saveBook(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping("/detail/category={category}")
    public ResponseEntity<Object> put(
            @Valid @RequestBody UpsertBookDTO dto,
            BindingResult bindingResult,
            @PathVariable() String category){
        try{
            if(!bindingResult.hasErrors()){
                dto.setCategoryName(category);
                bookService.saveBook(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/detail/id={id}")
    public ResponseEntity<Object> deleteBook(
            @PathVariable() String id){
        try{
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}

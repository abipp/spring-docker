package com.winterhold.controller;

import com.winterhold.dto.author.AuthorBooksGridDTO;
import com.winterhold.dto.book.UpsertBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import com.winterhold.service.AuthorService;
import com.winterhold.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    private AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService){
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/index")
    public String getAllCategories(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "") String category,
                                   Model model) {

        Page<Category> categories = bookService.findAllCategory(page, category);
        List<CategoryGridDTO> categoryDto = CategoryGridDTO.toList(categories.getContent());

        model.addAttribute("categories", categoryDto);
        model.addAttribute("breadCrumbs", "Category / Index");
        model.addAttribute("totalPage", categories.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        return "/book/category-index";
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) String id, Model model) {
        if (id != null) {
            model.addAttribute("category", bookService.getCategoryById(id));
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Category / Update");
        } else {
            model.addAttribute("category", new UpsertCategoryDTO());
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Category / Insert");
        }
        return "/book/category-form";
    }

    @PostMapping("/upsert-category")
    public String saveCategory(@Valid @ModelAttribute("category") UpsertCategoryDTO upsertCategoryDTO,
                               BindingResult bindingResult,
                               Model model){

        if (bindingResult.hasErrors()){
            if(upsertCategoryDTO.getId() != null) {
                model.addAttribute("type", "update");
                model.addAttribute("breadCrumbs", "Product Index / Update Product");
            } else {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Product Index / Insert Product");
            }
            return "/book/category-form";
        } else {
            bookService.saveCategory(upsertCategoryDTO);
            return "redirect:/book/index";
        }

    }

    @GetMapping("/delete")
    public String delete(@RequestParam String id, Model model) {
        Long totalBook = bookService.countBookByCategoryId(id);
        if (totalBook > 0){
            model.addAttribute("dependencies", totalBook);
            model.addAttribute("breadCrumbs", "Category Index / Fail to Delete Category");
            return "/book/delete-category";
        }
        bookService.deleteCategoryById(id);
        return "redirect:/book/index";
    }

    @GetMapping("/detail")
    public String books(@RequestParam(defaultValue = "") String category,
                        @RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") String fullName, Model model,
                        @RequestParam(defaultValue = "1") int page){

        Page<Book> allBooks = bookService.findAllBookByCategory(category, title, fullName, page);
        List<AuthorBooksGridDTO> bookGrid = AuthorBooksGridDTO.convert(allBooks.getContent());

        model.addAttribute("books", bookGrid);
        model.addAttribute("breadCrumbs", "Category / Books");
        model.addAttribute("totalPage", allBooks.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        model.addAttribute("title", title);
        model.addAttribute("fullName", fullName);
        return "/book/category-books";
    }

    @GetMapping("/upsert-book-form")
    public String upsertBook(@RequestParam String category,
                             @RequestParam(required = false) String id,
                             Model model) {
        model.addAttribute("category", category);
        if (id != null) {
            model.addAttribute("books", bookService.getBookById(id));
            model.addAttribute("breadCrumbs", "Book / Update");
        } else {
            model.addAttribute("books", UpsertBookDTO.from(
                    new UpsertBookDTO(), category));
            model.addAttribute("breadCrumbs", "Book / Insert");
        }
        model.addAttribute("authors", authorService.findAllAuthorName());
        return "book/category-books-form";
    }

    @PostMapping("/upsert-book")
    public String insert(@Valid @ModelAttribute("books") UpsertBookDTO dto,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam String category) {
        dto.setCategoryName(category);
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAllAuthorName());
            model.addAttribute("category", category);
            model.addAttribute("books", dto);
            return "/book/category-books-form";
        }
        bookService.saveBook(dto);
        return "redirect:/book/detail?category=" +category;
    }

    @PostMapping("/delete-book")
    public String delete(@RequestParam String id,
                         @RequestParam String category,
                         Model model) {
        Long totalLoan = bookService.countLoanByBookId(id);
        if (totalLoan > 0){
            model.addAttribute("dependencies", totalLoan);
            model.addAttribute("breadCrumbs", "Category / Book / Fail to Delete Book");
            model.addAttribute("category", category);
            return "/book/delete-book";
        }
        bookService.deleteBook(id);
        return "redirect:/book/detail?category=" + category;
    }
}

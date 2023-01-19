package com.winterhold.controller;

import com.winterhold.dto.author.AuthorBooksGridDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Book;
import com.winterhold.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/index")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "") String fullName,
                       Model model) {
        Page<AuthorHeaderDTO> allAuthors = authorService.findAllAuthor(page, fullName);
        List<AuthorGridDTO> authorGrid = AuthorGridDTO.convert(allAuthors.getContent());

        model.addAttribute("authors", authorGrid);
        model.addAttribute("breadCrumbs", "Author / Index");
        model.addAttribute("totalPage", allAuthors.getTotalPages());
        model.addAttribute("fullName", fullName);
        model.addAttribute("page", page);
        return "/author/author-index";
    }

    @GetMapping("/upsert-form")
    public String upsertForm (@RequestParam(required = false) Long id, Model model){
        if(id != null){
            model.addAttribute("authors", authorService.getAuthorById(id));
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Author / Update");
        }else {
            model.addAttribute("authors", new UpsertAuthorDTO());
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Author / Insert");
        }
        return "/author/author-form";
    }

    @PostMapping("/upsert")
    public String upsertForm(@Valid @ModelAttribute("authors") UpsertAuthorDTO authorDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            if (authorDto.getId() != null){
                model.addAttribute("type", "update");
                model.addAttribute("breadCrumbs", "Author / Update");
            } else {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Author / Insert");
            }
            return "/author/author-form";
        } else {
            authorService.saveAuthor(authorDto);
            return "redirect:/author/index";
        }
    }

    @GetMapping("/delete")
    public String delete (@RequestParam Long id, Model model){
        Long totalBook = authorService.depedentBook(id);
        if (totalBook > 0){
            model.addAttribute("dependencies", totalBook);
            model.addAttribute("breadCrumbs", "Author Index / Fail to Delete Author");
            return "author/delete-author";
        }
        authorService.deleteAuthorById(id);
        return "redirect:/author/index";
    }

    @GetMapping("/books")
    public String books(@RequestParam Long id, Model model,
                        @RequestParam(defaultValue = "1") int page){
        UpsertAuthorDTO authorDto = authorService.getAuthorById(id);
        AuthorGridDTO authorGridDto = AuthorGridDTO.convertBook(authorDto);

        Page<Book> allBooks = authorService.findAllBookByAuthor(id, page);
        List<AuthorBooksGridDTO> bookGrid = AuthorBooksGridDTO.convert(allBooks.getContent());
        model.addAttribute("authors", authorGridDto);
        model.addAttribute("books", bookGrid);
        model.addAttribute("breadCrumbs", "Author / Books");
        model.addAttribute("totalPage", allBooks.getTotalPages());
        model.addAttribute("page", page);
        return "author/author-books";
    }
}

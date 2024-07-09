package jsg.culturelog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jsg.culturelog.domain.Review;
import jsg.culturelog.domain.dto.BookDto;
import jsg.culturelog.domain.item.Book;
import jsg.culturelog.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService;

    @GetMapping("/item/book/bestSeller")
    public String bestSeller(Model model) {
        List<BookDto> bestSeller = bookService.getBestSeller();

        model.addAttribute("bestSeller", bestSeller);

        return "views/item/book/bestSeller";
    }

    @GetMapping("/item/book/{isbn}")
    public String bookInfo(@PathVariable(name = "isbn") String isbn, Model model) {
        bookService.setBookModel(isbn, model);

        return "views/item/book/bookInfo";
    }

    @GetMapping("/item/book")
    public String bookSearch(Model model, HttpServletRequest request) {
        String queryType = request.getParameter("QueryType");
        String query = null;
        String page = (request.getParameter("Page") == null || request.getParameter("Page").equals("")) ? "1" : request.getParameter("Page");
        if (request.getParameter("Query") != null) {
            query = request.getParameter("Query").replace(" ", "+");
        }

        if (queryType == null) {
            model.addAttribute("bookList", null);
        } else {
            List<BookDto> bookList = bookService.searchBook(queryType, query, page);
            int totalCount = bookList.get(0).getCount();
            int totalPage = (totalCount % 12 == 0) ? totalCount / 12 : (totalCount / 12) + 1;
            if (totalPage >= 10) {
                totalPage = 10;
            }
            model.addAttribute("page", totalPage);
            model.addAttribute("bookList", bookList);
        }

        model.addAttribute("QueryType", queryType);
        model.addAttribute("Query", request.getParameter("Query"));
        model.addAttribute("Page", page);

        return "views/item/book/bookSearch";
    }
}

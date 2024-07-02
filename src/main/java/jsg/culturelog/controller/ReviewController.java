package jsg.culturelog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jsg.culturelog.domain.Member;
import jsg.culturelog.domain.Review;
import jsg.culturelog.domain.form.BookReviewAddForm;
import jsg.culturelog.domain.item.Book;
import jsg.culturelog.repository.MemberRepository;
import jsg.culturelog.service.BookService;
import jsg.culturelog.service.MemberService;
import jsg.culturelog.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping("/review/book/add/{isbn}")
    public String bookReviewPage(@PathVariable(name = "isbn") String isbn, Model model) {
        bookService.setBookModel(isbn, model);

        return "views/review/book/addPage";
    }

    @PostMapping("/review/book/add")
    public String bookReviewAdd(@Validated @ModelAttribute BookReviewAddForm form, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()){
            model.addAttribute("book",form);
        }

        Book book = bookService.getBook(form.getIsbn());
        if (book == null) {
            book = bookService.saveBook(new Book(form.getName(),form.getAuthor(), form.getPubDate(), form.getDescription(), form.getIsbn(), form.getPublisher(), form.getCover(), form.getBookLink()));
        }

        request.getSession().setAttribute("login", memberService.findMemberById(1L));

        Member login = (Member) request.getSession().getAttribute("login");

        Review review = reviewService.saveReview(new Review(form.getTitle(), form.getContent(), form.getGrade(), login, book));

        return "redirect:/review/book/" + review.getId();
    }

    @GetMapping("/review/book/{id}")
    public String bookReview(@PathVariable("id") Long id, Model model) {
        Review review = reviewService.getBookReview(id);

        model.addAttribute("review", review);
        model.addAttribute("book", (Book) review.getItem());

        return "views/review/book/review";
    }

    @GetMapping("/review/book")
    public String bookReviewList(Model model) {
        List<Review> reviews = reviewService.getAllBookReview();
        List<Book> bookList = new ArrayList<>();
        for (Review review : reviews) {
            Book book = (Book) review.getItem();
            bookList.add(book);
        }
        model.addAttribute("reviews", reviews);
        model.addAttribute("bookList", bookList);
        return "views/review/book/reviewList";
    }
}

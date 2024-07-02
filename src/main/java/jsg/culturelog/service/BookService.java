package jsg.culturelog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsg.culturelog.domain.Review;
import jsg.culturelog.domain.dto.BookDto;
import jsg.culturelog.domain.item.Book;
import jsg.culturelog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto> getBestSeller() {

        HttpURLConnection connection = null;
        List<BookDto> bestSellers = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder("http://www.aladin.co.kr/ttb/api/ItemList.aspx?");
            sb.append("ttbkey=ttbsungi12051129001");
            sb.append("&QueryType=Bestseller");
            sb.append("&MaxResults=10");
            sb.append("&start=1");
            sb.append("&Cover=Big");
            sb.append("&SearchTarget=Book");
            sb.append("&output=JS");
            sb.append("&Version=20131101");

            URL url = new URL(sb.toString());

            log.info("Aladin Open API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }

            ObjectMapper mapper = new ObjectMapper();

            HashMap hashMap = mapper.readValue(sb.toString(), HashMap.class);
            ArrayList item = (ArrayList) hashMap.get("item");


            for (Object book : item) {
                HashMap<String, Object> map = (HashMap<String, Object>) book;
                BookDto bookDto = new BookDto();
                bookDto.setName((String) map.get("title"));
                bookDto.setAuthor((String) map.get("author"));
                bookDto.setDescription((String) map.get("description"));
                bookDto.setIsbn((String) map.get("isbn13"));
                bookDto.setPubDate((String) map.get("pubDate"));
                bookDto.setCover((String) map.get("cover"));
                bookDto.setPublisher((String) map.get("publisher"));

                bestSellers.add(bookDto);
            }

            rd.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
                log.info("Aladin Open API Disconnect");
            }
        }
        return bestSellers;
    }

    public BookDto getBookInfo(String isbn) {
        HttpURLConnection connection = null;

        BookDto bookDto = new BookDto();
        try {
            StringBuilder sb = new StringBuilder(" http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?");
            sb.append("ttbkey=ttbsungi12051129001");
            sb.append("&itemIdType=ISBN13");
            sb.append("&ItemId=" + isbn);
            sb.append("&Cover=Big");
            sb.append("&output=JS");
            sb.append("&Version=20131101");

            URL url = new URL(sb.toString());

            log.info("Aladin Open API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }
            ObjectMapper mapper = new ObjectMapper();

            HashMap hashMap = mapper.readValue(sb.toString(), HashMap.class);

            ArrayList item = (ArrayList) hashMap.get("item");


            for (Object book : item) {
                HashMap<String, Object> map = (HashMap<String, Object>) book;

                bookDto.setName((String) map.get("title"));
                bookDto.setAuthor((String) map.get("author"));
                bookDto.setDescription((String) map.get("description"));
                bookDto.setIsbn((String) map.get("isbn13"));
                bookDto.setPubDate((String) map.get("pubDate"));
                bookDto.setCover((String) map.get("cover"));
                bookDto.setPublisher((String) map.get("publisher"));
                bookDto.setBookLink((String) map.get("link"));
            }

            rd.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
                log.info("Aladin Open API Disconnect");
            }
        }
        return bookDto;
    }

    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void setBookModel(String isbn, Model model) {
        Book book = getBook(isbn);
        Double grade = 0D;
        int count = 0;
        if (book != null) {
            Double sum = 0D;
            List<Review> reviews = book.getReviews();
            for (Review review : reviews) {
                sum += review.getGrade();
            }
            grade = sum / reviews.size();
            count = reviews.size();
            model.addAttribute("book", book);

        } else {
            BookDto bookDto = getBookInfo(isbn);
            model.addAttribute("book", bookDto);
        }
        model.addAttribute("grade", grade);
        model.addAttribute("count", count);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }


    public List<BookDto> searchBook(String queryType, String query, String page) {
        HttpURLConnection connection = null;
        List<BookDto> bookList = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder("http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?");
            sb.append("ttbkey=ttbsungi12051129001");
            sb.append("&QueryType=" + queryType);
            sb.append("&Query=" + query);
            sb.append("&Start=" + page);
            sb.append("&MaxResults=12");
            sb.append("&Cover=Big");
            sb.append("&output=JS");
            sb.append("&Version=20131101");

            URL url = new URL(sb.toString());

            log.info("Aladin Open API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }

            System.out.println(sb.toString());

            ObjectMapper mapper = new ObjectMapper();

            HashMap hashMap = mapper.readValue(sb.toString(), HashMap.class);
            ArrayList item = (ArrayList) hashMap.get("item");


            for (Object book : item) {
                HashMap<String, Object> map = (HashMap<String, Object>) book;
                BookDto bookDto = new BookDto();
                bookDto.setName((String) map.get("title"));
                bookDto.setAuthor((String) map.get("author"));
                bookDto.setDescription((String) map.get("description"));
                bookDto.setIsbn((String) map.get("isbn13"));
                bookDto.setPubDate((String) map.get("pubDate"));
                bookDto.setCover((String) map.get("cover"));
                bookDto.setPublisher((String) map.get("publisher"));

                bookList.add(bookDto);
            }

            rd.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
                log.info("Aladin Open API Disconnect");
            }
        }
        return bookList;
    }
}

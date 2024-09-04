package sungi.culturelog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungi.culturelog.aop.annotation.Trace;
import sungi.culturelog.domain.Review;
import sungi.culturelog.domain.dto.BookDto;
import sungi.culturelog.domain.item.Book;
import sungi.culturelog.repository.BookRepository;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 현재 bestSeller 조회하는 메서드
     * @author jsg
     * @return List<BookDto>
     */

    @Trace
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
                bookDto.setImg((String) map.get("cover"));
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

    /**
     * isbn으로 책 정보 조회하는 메서드
     * @Author jsg
     * @param isbn
     * @return BookDto
     */
    @Trace
    @Transactional
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
                bookDto.setImg((String) map.get("cover"));
                bookDto.setPublisher((String) map.get("publisher"));
                bookDto.setBookLink((String) map.get("link"));
            }

            saveBook(bookDto);

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

    /**
     * isbn으로 api 조회한 값 DB 적재
     * @param bookDto
     */
    @Trace
    @Transactional
    protected void saveBook(BookDto bookDto) {
        Book book = new Book(bookDto.getName(), bookDto.getImg(), bookDto.getDescription(), bookDto.getAuthor(), bookDto.getPubDate(), bookDto.getIsbn(), bookDto.getPublisher(), bookDto.getBookLink());
        bookRepository.save(book);
    }

    /**
     * isbn으로 책 정보 조회, DB에 있을 시 DB에서 select, 없을시 API로 조회
     * @Author jsg
     * @param isbn
     * @return
     */
    @Trace
    @Transactional
    public BookDto getBook(String isbn) {
        Optional<Book> findBook = bookRepository.findByIsbn(isbn);
        int count = 0;
        double grade = 0D;
        if (findBook.isPresent()) {
            BookDto bookDto = new BookDto(findBook.get());
            int sum = 0;

            List<Review> reviews = findBook.get().getReviews();
            for (Review review : reviews) {
                sum += review.getGrade();
            }

            count = reviews.size();
            grade = (double) sum /count;

            bookDto.setCount(count);
            bookDto.setGrade(grade);
            return bookDto;
        } else {
            return getBookInfo(isbn);
        }
    }
}

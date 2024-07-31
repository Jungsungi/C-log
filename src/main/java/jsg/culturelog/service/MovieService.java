package jsg.culturelog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsg.culturelog.domain.dto.MovieDto;
import jsg.culturelog.domain.item.Movie;
import jsg.culturelog.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public List<MovieDto> weekBoxoffice() {
        HttpURLConnection connection = null;
        List<MovieDto> list = new ArrayList<>();

        // 주간 조회가 일요일 기준으로 조회 됨으로 조회 날짜를 직전 일요일로 설정
        String ld = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .toString().replaceAll("-", "");

        try {
            StringBuilder sb = new StringBuilder("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?");
            sb.append("key=fa1ddead2e9b1ded363a3b40a7c8f55a");
            sb.append("&targetDt=" + ld);
            sb.append("&weekGb=0");

            log.info("request url : {}" , sb.toString());
            URL url = new URL(sb.toString());

            log.info("Movie KOFIC API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

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
            HashMap boxOfficeResult = (HashMap) hashMap.get("boxOfficeResult");
            ArrayList boxOfficeList = (ArrayList) boxOfficeResult.get("weeklyBoxOfficeList");

            for (Object o : boxOfficeList) {
                HashMap movieMap = (HashMap) o;
                MovieDto movieDto = new MovieDto();
                String movieCd = (String) movieMap.get("movieCd");
                Optional<Movie> findMovie = movieRepository.findByMovieKey(movieCd);

                if (!findMovie.isEmpty()) {
                    movieDto.setMovieCode(findMovie.get().getMovieKey());
                    movieDto.setMoviename(findMovie.get().getName());
                    movieDto.setOpenDate(findMovie.get().getOpenDate());
                    movieDto.setDirector(findMovie.get().getDirector());
                    movieDto.setImg(findMovie.get().getImg());
                } else {
                    movieDto.setMovieCode(movieCd);
                    movieDto.setMoviename((String) movieMap.get("movieNm"));
                    movieDto.setOpenDate((String) movieMap.get("openDt"));
                    Map<String, String> infoMap = movieDetailInfo((String) movieMap.get("movieNm"), (String) movieMap.get("openDt"));
                    movieDto.setImg(infoMap.get("poster"));
                    movieDto.setDetail(infoMap.get("detail"));

                    movieSave(movieDto);
                }
                list.add(movieDto);
            }


        } catch (Exception e) {
            log.error("error : {}", e);
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return list;
    }

    //영화 엔티티 DB 적재
    @Transactional
    protected void movieSave(MovieDto movieDto) {
        Movie movie = new Movie(movieDto.getMoviename(), movieDto.getDirector(), movieDto.getOpenDate(), movieDto.getDetail(), null, movieDto.getMovieCode(), 0D, movieDto.getImg());
        movieRepository.save(movie);
    }

    public MovieDto getMovieInfo(String movieCode) {
        HttpURLConnection connection = null;

        try {
            StringBuilder sb = new StringBuilder("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?");
            sb.append("key=fa1ddead2e9b1ded363a3b40a7c8f55a");
            sb.append("&movieCd=" + movieCode);

            log.info("request url : {}" , sb.toString());
            URL url = new URL(sb.toString());

            log.info("Movie KOFIC API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuilder();

            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }

            System.out.println("sb = " + sb);

            ObjectMapper mapper = new ObjectMapper();

            HashMap hashMap = mapper.readValue(sb.toString(), HashMap.class);
//            HashMap boxOfficeResult = (HashMap) hashMap.get("boxOfficeResult");
//            ArrayList boxOfficeList = (ArrayList) boxOfficeResult.get("weeklyBoxOfficeList");
//
//            for (Object o : boxOfficeList) {
//                HashMap movieMap = (HashMap) o;
//                MovieDto movieDto = new MovieDto();
//                movieDto.setMovieCode((String) movieMap.get("movieCd"));
//                movieDto.setMoviename((String) movieMap.get("movieNm"));
//                movieDto.setOpenDate((String) movieMap.get("openDt"));
//            }


        } catch (Exception e) {
            log.error("error : {}", e);
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;

    }

    // 영화진흥위원회 api에서 제목, 개봉일을 가지고 DMDb 에서 상세 정보 조회
    private Map<String, String> movieDetailInfo(String movieNm, String openDt) {
        HttpURLConnection connection = null;
        Map<String, String> map = new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
            sb.append("&ServiceKey=LQ1R17470X2M04E0E57R");
            sb.append("&title=" + URLEncoder.encode(movieNm, StandardCharsets.UTF_8));
            sb.append("&releaseDts=" + openDt.replaceAll("-", ""));
            sb.append("&detail=Y");

            log.info("request url : {}" , sb.toString());
            URL url = new URL(sb.toString());

            log.info("Movie KMDb API Connect");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuilder();

            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }

            System.out.println("sb = " + sb.toString());

            ObjectMapper mapper = new ObjectMapper();

            HashMap hashMap = mapper.readValue(sb.toString(), HashMap.class);

            ArrayList<LinkedHashMap> data = (ArrayList<LinkedHashMap>) hashMap.get("Data");
            LinkedHashMap dataMap = data.get(0);
            LinkedHashMap result = (LinkedHashMap) ((ArrayList) dataMap.get("Result")).get(0);
            String posters = (String) result.get("posters");
            String poster = posters.substring(0, posters.indexOf("|"));

            ArrayList array = (ArrayList) (((LinkedHashMap) result.get("plots")).get("plot"));
            LinkedHashMap plotMap = (LinkedHashMap) array.get(0);

            ArrayList director = (ArrayList) ((LinkedHashMap) result.get("directors")).get("director");
            LinkedHashMap directorMap = (LinkedHashMap) director.get(0);

            map.put("poster", poster);
            map.put("detail", (String) plotMap.get("plotText"));
            map.put("director", (String) directorMap.get("directorNm"));

        } catch (Exception e) {
            log.error("error : {}", e);
        }

        return map;
    }
}

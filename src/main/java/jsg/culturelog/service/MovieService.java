package jsg.culturelog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsg.culturelog.domain.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieService {

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

            log.info("Movie Open API Connect");

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
            HashMap boxOfficeResult = (HashMap) hashMap.get("boxOfficeResult");
            ArrayList boxOfficeList = (ArrayList) boxOfficeResult.get("weeklyBoxOfficeList");

            for (Object o : boxOfficeList) {
                HashMap movieMap = (HashMap) o;
                MovieDto movieDto = new MovieDto();
                movieDto.setMovieCode((String) movieMap.get("movieCd"));
                movieDto.setMoviename((String) movieMap.get("movieNm"));
                movieDto.setOpenDate((String) movieMap.get("openDt"));

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
}

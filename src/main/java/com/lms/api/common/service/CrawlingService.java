package com.lms.api.common.service;


import com.lms.api.common.service.dto.NewsDto;
import com.lms.api.common.util.DayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.stereotype.Service;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService {

    private final String NAVER_NEWS_SEARCH_URL = "https://search.naver.com/search.naver?where=news&query=국회";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public List<NewsDto> getNaverNews(){
        List<NewsDto> newsList = new ArrayList<>();
        Date currentDate = new Date ();
        try {
            // 네이버 뉴스 검색 결과 크롤링
            Document doc = Jsoup.connect(NAVER_NEWS_SEARCH_URL).get();
            Elements articles = doc.select(".news_area"); //뉴스 목록 선택자

            for(Element article : articles){
                String title = article.select(".news_tit").text();
                String link = article.select(".news_tit").attr("href");

                // span.info 태그만 가져오기
                Element dateElement = article.selectFirst(".info_group > span.info");
                String relativeDateText = (dateElement != null) ? dateElement.text() : "날짜 없음";
                // 상대적 날짜를 yyyy.MM.dd. 형식으로 변환
                String formattedDate = DayUtils.convertRelativeDate(relativeDateText);
                if (formattedDate != null && isWithinLast7Days(formattedDate)) {
                    newsList.add(new NewsDto(title, formattedDate, link));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return newsList;
    }

    // 기사 본문 가져오기
    private String getNewsContent(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element contentElement = doc.selectFirst(".newsct_article");
            return contentElement != null ? contentElement.text() : "본문을 가져올 수 없습니다.";
        } catch (IOException e) {
            return "본문을 가져올 수 없습니다.";
        }
    }
    // 최근 7일 이내인지 확인하는 메서드
    private boolean isWithinLast7Days(String dateText) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.");
            Date articleDate = dateFormat.parse(dateText);
            Date currentDate = new Date();

            long differenceInMillis = currentDate.getTime() - articleDate.getTime();
            long daysDifference = differenceInMillis / (1000 * 60 * 60 * 24); // 밀리초 → 일(day) 변환
            return daysDifference <= 7;
        } catch (Exception e) {
            return false; // 날짜 변환 실패 시 제외
        }
    }

}

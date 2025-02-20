package com.lms.api.common.controller;


import com.lms.api.common.service.CrawlingService;
import com.lms.api.common.service.dto.NewsDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crawling")
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;
    @Operation(summary = "네이버 뉴스 크롤링", description = "네이버 뉴스에서 특정 키워드로 검색된 최신 기사를 크롤링하여 반환.")
    @GetMapping("/naver/news")
    public List<NewsDto> getNaverNews(){
        return crawlingService.getNaverNews();
    }
}

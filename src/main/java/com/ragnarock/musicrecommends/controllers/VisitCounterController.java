package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.services.VisitCounterService;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visits")
public class VisitCounterController {

    private final VisitCounterService visitCounterService;

    public void incrementVisit(String url) {
        visitCounterService.incrementVisit(url);
    }

    @GetMapping("/count")
    public int getVisitCount(@RequestParam String url) {
        return visitCounterService.getVisits(url);
    }

    @GetMapping("/all")
    public Map<String, AtomicInteger> getAllVisitCounts() {
        return visitCounterService.getAllVisits();
    }
}

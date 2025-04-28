package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.services.VisitCounterService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class VisitCounterServiceImpl implements VisitCounterService {
    private final ConcurrentHashMap<String, AtomicInteger>
            urlVisitCounts = new ConcurrentHashMap<>();

    public void incrementVisit(String url) {
        urlVisitCounts.computeIfAbsent(url, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getVisits(String url) {
        return urlVisitCounts.getOrDefault(url, new AtomicInteger(0)).get();
    }

    public ConcurrentHashMap<String, AtomicInteger> getAllVisits() {
        return urlVisitCounts;
    }
}

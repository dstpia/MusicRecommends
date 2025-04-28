package com.ragnarock.musicrecommends.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public interface VisitCounterService {

    void incrementVisit(String url);

    int getVisits(String url);

    ConcurrentHashMap<String, AtomicInteger> getAllVisits();
}

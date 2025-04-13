package com.ragnarock.musicrecommends.cache;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class CustomCache<K, V> {
    private final Map<K, CacheEntry<V>> cache = new LinkedHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final long ttlMillis = 10000; //Временно установлено на 10 сек
    private final int maxSize = 1000;
    private boolean initialized = false;

    public CustomCache() {
        log.info("Cache initialized, ttl: {} (seconds), maxSize: {}", ttlMillis / 1000, maxSize);
        init();
    }

    @PostConstruct
    public void init() {
        if (!initialized) {
            initialized = true;
            scheduler.scheduleAtFixedRate(this::deletingExpiredEntries,
                    0, ttlMillis / 1000, TimeUnit.SECONDS);
            log.info("Scheduler initialized");
        }
    }

    public void putInCache(K key, V value) {
        synchronized (cache) {
            if (cache.size() >= maxSize) {
                Iterator<K> iterator = cache.keySet().iterator();
                if (iterator.hasNext()) {
                    K oldestKey = iterator.next();
                    iterator.remove();
                    log.info("Cache limit reached. Removed oldest key: {}", oldestKey);
                }
            }
            cache.put(key, new CacheEntry<>(value, Instant.now().toEpochMilli()));
            log.info("Put into cache: key = {}, value = {}", key, value);
        }
    }

    public V getFromCache(K key) {
        synchronized (cache) {
            CacheEntry<V> entry;
            if (containsKeyCheck(key)) {
                entry = cache.get(key);
            } else {
                return null;
            }
            if (isExpired(entry)) {
                cache.remove(key);
                log.info("Cache entry expired for key: {}", key);
                return null;
            }
            cache.put(key, new CacheEntry<>(entry.value, Instant.now().toEpochMilli()));
            log.info("Cache hit for key: {}", key);
            return entry.value;
        }
    }

    public void removeFromCache(K key) {
        synchronized (cache) {
            if (cache.containsKey(key)) {
                cache.remove(key);
                log.info("Removed from cache: key = {}", key);
            }
        }
    }

    public int cacheCurrentSize() {
        synchronized (cache) {
            Set<V> set = new HashSet<>();
            for (CacheEntry<V> entry : cache.values()) {
                if (entry.value != null) {
                    set.add(entry.value);
                }
            }
            return set.size();
        }
    }

    public boolean notEmptyCheck() {
        synchronized (cache) {
            return !cache.isEmpty();
        }
    }

    public List<V> getValues() {
        synchronized (cache) {
            Set<V> set = new HashSet<>();
            for (CacheEntry<V> entry : cache.values()) {
                if (entry.value != null) {
                    set.add(entry.value);
                }
            }
            return set.stream().toList();
        }
    }

    public boolean containsKeyCheck(K key) {
        synchronized (cache) {
            if (notEmptyCheck()) {
                log.info("Key found in cache: {}", key);
                return cache.containsKey(key);
            } else {
                log.info("Key not found in cache: {}", key);
                return false;
            }
        }
    }

    public void clearCache() {
        synchronized (cache) {
            if (notEmptyCheck()) {
                cache.clear();
                log.info("Cache cleared");
            } else {
                log.info("Cache is empty, cannot clear");
            }
        }
    }

    private void deletingExpiredEntries() {
        synchronized (cache) {
            Iterator<Map.Entry<K, CacheEntry<V>>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, CacheEntry<V>> entry = iterator.next();
                if (isExpired(entry.getValue())) {
                    log.info("Deleting expired cache entry for key: {}", entry.getKey());
                    iterator.remove();
                }
            }
        }
    }

    private boolean isExpired(CacheEntry<V> entry) {
        return (Instant.now().toEpochMilli() - entry.timestamp) > ttlMillis;
    }

    @PreDestroy
    public void shutdown() {
        this.clearCache();
        scheduler.shutdown();
        log.info("Cache shutdown");
    }

    @Data
    private static class CacheEntry<V> {
        private final V value;
        private final long timestamp;
    }
}
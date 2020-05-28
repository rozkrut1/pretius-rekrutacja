package org.jakub.rozkrut.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class CacheCleaner {

    private final CacheManager cacheManager;
    private final String cacheName;

    @Autowired
    CacheCleaner(CacheManager cacheManager, @Value("${cache.nbp.name}") String cacheName) {
        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
    }

    @Scheduled(cron = "${cache.nbp.cron}")
    void clearCache() {
        log.info("Rozpoczynam czyszczenie chache o nazwie: {}", cacheName);
        try {
            cacheManager.getCache(cacheName).clear();
        } catch (Exception e) {
            log.error("Nie udało się wyczyścić cache {}", cacheName, e);
        }
        log.info("Czyszczenie cache: {} zakończone", cacheName);
    }
}

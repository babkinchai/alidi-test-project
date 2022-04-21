package org.example.aliditestproject.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.example.aliditestproject.services.BasketService;
import org.example.aliditestproject.services.BasketServiceInterface;
import org.example.aliditestproject.services.PriceService;
import org.example.aliditestproject.services.PriceServiceInterface;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class Config {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES));
        return caffeineCacheManager;
    }

    @Bean
    public BasketServiceInterface basketServiceInterface() {
        return new BasketService(priceServiceInterface());
    }

    @Bean
    public PriceServiceInterface priceServiceInterface() {
        return new PriceService();
    }
}

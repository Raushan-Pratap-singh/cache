package com.ecom.patientdemographics.cache.config;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        try {
            CachingProvider provider = Caching.getCachingProvider();
            URI myUri = getClass().getResource("/ehcache.xml").toURI();
            CacheManager cacheManager = provider.getCacheManager(myUri, getClass().getClassLoader());
            return new JCacheCacheManager(cacheManager);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to initialize EHCache configuration", e);
        }
    }

}

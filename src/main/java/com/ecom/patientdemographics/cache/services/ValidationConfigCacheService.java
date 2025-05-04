package com.ecom.patientdemographics.cache.services;


import com.ecom.patientdemographics.cache.models.FacilityConfigMapWrapper;
import com.ecom.patientdemographics.cache.models.ValidationConfig;
import com.ecom.patientdemographics.cache.repositories.ValidationConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@Service
public class ValidationConfigCacheService {

    private static final String CACHE_NAME = "validationConfigCache";

    @Value("${patientDemographicValidation.enabled:true}")
    private boolean isValidationEnabled;

    @Autowired
    private ValidationConfigRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void initializeCache() {
        if (!isValidationEnabled) return;

        Cache cache = cacheManager.getCache(CACHE_NAME);

        repository.findAll().forEach(config -> {
            if ("HL7".equalsIgnoreCase(config.getInterfaceType())) {
                config.getMessageConfigurations().forEach((messageType, messageTypeConfig) -> {
                    String key = buildKey("HL7", messageType);
                    cache.put(key, new FacilityConfigMapWrapper(messageTypeConfig.getSendingFacilities()));
                });
            } else {
                String key = config.getInterfaceType();
                cache.put(key, new FacilityConfigMapWrapper(config.getSendingFacilities()));
            }
        });
    }

    public Map<String, ValidationConfig.FacilityConfig> getFromCache(String interfaceType, String messageType) {
        String key = "HL7".equalsIgnoreCase(interfaceType)
                ? buildKey("HL7", messageType)
                : interfaceType;

        Cache.ValueWrapper valueWrapper = cacheManager.getCache(CACHE_NAME).get(key);
        if (valueWrapper == null) {
            return Collections.emptyMap();
        }

        Object value = valueWrapper.get();
        if (value instanceof FacilityConfigMapWrapper) {
            return ((FacilityConfigMapWrapper) value).getFacilityConfigMap();
        }

        return Collections.emptyMap();
    }

    private String buildKey(String interfaceType, String messageType) {
        return interfaceType + "::" + messageType;
    }
}


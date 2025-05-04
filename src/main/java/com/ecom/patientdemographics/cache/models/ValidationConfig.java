package com.ecom.patientdemographics.cache.models;

// ================================
// ValidationConfig.java
// ================================

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Data
@Document(collection = "patientDemographicValidationConfig")
public class ValidationConfig {

    private String interfaceType;
    private Map<String, MessageTypeConfig> messageConfigurations;
    private Map<String, FacilityConfig> sendingFacilities;

    @Data
    public static class MessageTypeConfig {
        private Map<String, FacilityConfig> sendingFacilities;
    }

    @Data
    public static class FacilityConfig {
        private boolean enabled;
        private Map<String, Boolean> validationFields;
    }
}
/**
Project: patient-demographics-cache
Description: EHCache-backed Spring Boot module for caching patient demographic validation configuration data across HL7, CCDA, and BulkFhir interfaces.
Package base: com.ecom.patientdemographics.cache
*/

// ================================
// FacilityConfigMapWrapper.java
// ================================



package com.ecom.patientdemographics.cache.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityConfigMapWrapper {
    private Map<String, ValidationConfig.FacilityConfig> facilityConfigMap;
}
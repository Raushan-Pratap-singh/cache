// ================================
// README.md
// ================================
# Patient Demographics Cache

A reusable Spring Boot module that caches patient demographic validation configurations from MongoDB using EHCache.

## 📆 Interfaces Supported
- HL7 (with message types like ADT, MDM, etc.)
- CCDA
- BulkFhir

## 💾 Sample MongoDB Entry

### HL7
```json
db.patientDemographicsValidationConfig.insertOne({
  "interfaceType": "HL7",
  "messageConfigurations": {
    "ADT_corewell": {
      "sendingFacilities": {
        "corewell": {
          "enabled": true,
          "validationFields": {
            "DOB": true,
            "Gender": true,
            "Telecom": false,
            "isFallbackEnabled": false
          }
        }
      }
    },
    "MDM_corewell": {
      "sendingFacilities": {
        "corewell": {
          "enabled": true,
          "validationFields": {
            "DOB": true,
            "Gender": true,
            "Telecom": false,
            "isFallbackEnabled": false
          }
        }
      }
    }
  }
})

```

### CCDA
```json
db.patientDemographicsValidationConfig.insertOne({
  "interfaceType": "CCDA",
  "sendingFacilities": {
    "CCDA_corewell": {
      "enabled": true,
      "validationFields": {
        "DOB": true,
        "Gender": true,
        "Telecom": false,
        "isFallbackEnabled": false
      }
    },
    "CCDA_veradigm": {
      "enabled": true,
      "validationFields": {
        "DOB": true,
        "Gender": true,
        "Telecom": false,
        "isFallbackEnabled": true
      }
    }
  }
})

```


### BulkFhir
```json
db.patientDemographicsValidationConfig.insertOne({
  "interfaceType": "CCDA",
  "sendingFacilities": {
    "BulkFhir_corewell": {
      "enabled": true,
      "validationFields": {
        "DOB": true,
        "Gender": true,
        "Telecom": false,
        "isFallbackEnabled": false
      }
    },
    "BulkFhir_veradigm": {
      "enabled": true,
      "validationFields": {
        "DOB": true,
        "Gender": true,
        "Telecom": false,
        "isFallbackEnabled": true
      }
    }
  }
})

## 🔧 Setup

1. Add this module as a dependency in your application.
2. Enable caching and include the configuration property:
```properties
patientDemographicValidation.enabled=true
```
3. Use `ValidationConfigCacheService#getFromCache(interfaceType, messageType)` to access facility-level config.

## 🦖 Example Use
```java
Map<String, FacilityConfig> configMap = cacheService.getFromCache("HL7", "ADT");
FacilityConfig corewellConfig = configMap.get("corewell");
```

## 🦖 Tests
- `ValidationConfigCacheServiceTest` ensures boot caching logic works and proper retrieval happens.
- Uses Mockito for mocking repository.

## 📌 Notes
- Cache auto-initializes on startup if the flag is enabled.
- TTL for cache entries is 60 minutes (configurable in `ehcache.xml`).

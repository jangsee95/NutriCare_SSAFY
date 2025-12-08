package com.nutricare.model.service;

import com.nutricare.model.dto.HealthProfile;

public interface HealthProfileService {
    HealthProfile getHealthProfile(Long userId);
    boolean saveOrUpdateHealthProfile(HealthProfile healthProfile);
}
package com.nutricare.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutricare.model.dao.HealthProfileDao;
import com.nutricare.model.dto.HealthProfile;

@Service
public class HealthProfileServiceImpl implements HealthProfileService {

    private final HealthProfileDao healthProfileDao;

    public HealthProfileServiceImpl(HealthProfileDao healthProfileDao) {
        this.healthProfileDao = healthProfileDao;
    }

    @Override
    public HealthProfile getHealthProfile(Long userId) {
        return healthProfileDao.selectByUserId(userId);
    }

    @Override
    @Transactional
    public boolean saveOrUpdateHealthProfile(HealthProfile healthProfile) {
        // 1. 기존 정보가 있는지 확인
        HealthProfile existing = healthProfileDao.selectByUserId(healthProfile.getUserId());
        
        if (existing == null) {
            // 2. 없으면 INSERT
            return healthProfileDao.insert(healthProfile) > 0;
        } else {
            // 3. 있으면 UPDATE (ID는 유지해야 하므로 기존 ID가 필요하다면 세팅, 
            // 여기선 WHERE user_id로 업데이트하므로 ID 세팅 불필요할 수 있으나 안전하게)
            healthProfile.setHealthId(existing.getHealthId());
            return healthProfileDao.update(healthProfile) > 0;
        }
    }
}
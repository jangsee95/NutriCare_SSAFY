package com.nutricare.model.dao;

import com.nutricare.model.dto.RagDietContext;

public interface RagDietContextDao {
	
	RagDietContext findRagDietContextByRecId(Long recId);
}

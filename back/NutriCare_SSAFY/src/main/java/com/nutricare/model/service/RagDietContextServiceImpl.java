package com.nutricare.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutricare.model.dao.RagDietContextDao;
import com.nutricare.model.dto.RagDietContext;

@Service
public class RagDietContextServiceImpl implements RagDietContextService {

	private final RagDietContextDao ragDietContextDao;

	public RagDietContextServiceImpl(RagDietContextDao ragDietContextDao) {
		this.ragDietContextDao = ragDietContextDao;
	}

	@Override
	@Transactional(readOnly = true)
	public RagDietContext getContextForRec(Long recId) {
		RagDietContext ctx = ragDietContextDao.findRagDietContextByRecId(recId);
		if (ctx == null) {
			// 필요하면 커스텀 예외로 변경
			throw new IllegalArgumentException("diet_recommendation not found. recId=" + recId);
		}
		return ctx;
	}

}

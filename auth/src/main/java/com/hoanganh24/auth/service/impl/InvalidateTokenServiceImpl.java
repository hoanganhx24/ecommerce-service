package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.model.InvalidateToken;
import com.hoanganh24.auth.repository.InvalidateTokenRepository;
import com.hoanganh24.auth.service.InvalidateTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class InvalidateTokenServiceImpl implements InvalidateTokenService{
    private final InvalidateTokenRepository invalidateTokenRepository;

    @Override
    @Transactional
    public void create(String id, Date expiration) {
        invalidateTokenRepository.save(InvalidateToken.builder()
                        .id(id)
                        .expiryTime(expiration)
                        .build());
    }

    @Override
    public boolean existById(String id) {
        return invalidateTokenRepository.existsById(id);
    }
}

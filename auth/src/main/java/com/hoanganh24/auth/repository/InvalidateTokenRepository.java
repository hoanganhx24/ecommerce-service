package com.hoanganh24.auth.repository;

import com.hoanganh24.auth.model.InvalidateToken;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepository extends BaseRepository<InvalidateToken, String> {
}

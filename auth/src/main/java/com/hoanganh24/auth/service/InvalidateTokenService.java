package com.hoanganh24.auth.service;

import java.util.Date;

public interface InvalidateTokenService {
    void create(String id, Date expiration);
}

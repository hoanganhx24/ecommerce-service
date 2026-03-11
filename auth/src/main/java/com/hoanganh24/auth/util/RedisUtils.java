package com.hoanganh24.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedisUtils {

    private final RedisTemplate<Object, Object> redisTemplate;

    // ========== 1. KEY-VALUE Operations ==========

    /**
     * Set giá trị key-value đơn giản
     */
    public void setKV(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Set giá trị key-value với TTL (Time To Live)
     */
    public void setKVWithTtl(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Lấy giá trị theo key
     */
    public Object getKV(Object key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lấy nhiều giá trị cùng lúc (batch operation)
     */
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(new HashSet<>(keys));
    }

    // ========== 2. DISTRIBUTED LOCKING ==========

    /**
     * Thử acquire lock phân tán (Distributed Lock)
     * @return true nếu acquire được lock, false nếu không
     */
    public boolean tryLock(String key, Object value, Duration ttl) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, ttl);
        return Boolean.TRUE.equals(success);
    }

    /**
     * Release lock
     */
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }

    // ========== 3. KEY Management ==========

    /**
     * Xóa key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Kiểm tra key có tồn tại không
     */
    public Boolean existKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Tìm tất cả key theo prefix (sử dụng SCAN - production safe)
     */
    public Set<String> scanKeysByPrefix(String prefix) {
        Set<String> keys = new HashSet<>();

        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(prefix + "*")
                .count(500)  // Số lượng key scan mỗi lần
                .build();

        try (Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
                connection -> connection.scan(scanOptions)
        )) {
            while (cursor != null && cursor.hasNext()) {
                keys.add(new String(cursor.next(), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keys;
    }

    // ========== 4. SORTED SET (ZSet) Operations ==========

    /**
     * Thêm phần tử vào Sorted Set với score
     */
    public void addZSet(String key, String value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Lấy phần tử trong khoảng score
     */
    public Set getZSet(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * Lấy tất cả phần tử trong ZSet
     */
    public Set<Object> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * Xóa phần tử theo khoảng score
     */
    public Long removeRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * Lấy score cao nhất trong ZSet
     */
    public double getHighestScore(String key) {
        Set<ZSetOperations.TypedTuple<Object>> zset =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 0);
        double maxScore = 0;
        if (zset != null) {
            for (var element : zset) {
                if (element.getScore() != null && element.getScore() > maxScore) {
                    maxScore = element.getScore();
                }
            }
        }
        return maxScore;
    }

    // ========== 5. LIST Operations (sử dụng ZSet) ==========

    /**
     * Thêm phần tử vào list (không trùng lặp)
     */
    public void addToList(String key, Object member) {
        redisTemplate.opsForZSet().add(key, member, 0D);
    }

    /**
     * Thêm phần tử vào list với TTL
     */
    public void addToListWithTtl(String key, Object member, Duration ttl) {
        redisTemplate.opsForZSet().add(key, member, 0D);
        redisTemplate.expire(key, ttl);
    }

    /**
     * Lấy toàn bộ list
     */
    public Set<Object> getList(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * Xóa phần tử khỏi list
     */
    public void removeFromList(String key, Object member) {
        redisTemplate.opsForZSet().remove(key, member);
    }
}
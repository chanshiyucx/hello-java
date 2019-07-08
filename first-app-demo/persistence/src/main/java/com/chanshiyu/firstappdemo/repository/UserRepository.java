package com.chanshiyu.firstappdemo.repository;

import com.chanshiyu.firstappdemo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link User} {@link Repository}
 */
@Repository
public class UserRepository {

    // ConcurrentMap，它是一个接口，是一个能够支持并发访问的 java.util.map 集合；是一个线程安全，并且是一个高效的 HashMap;
    private final ConcurrentMap<Integer, User> repository = new ConcurrentHashMap<>();

    private final static AtomicInteger idGenerator = new AtomicInteger();

    /**
     * 保存用户对象
     * @param user {@link User} 用户对象
     * @return 如果保存成功，返回 <code>true</code>
     *         否则，返回 <code>false</code>
     */
    public boolean save(User user) {
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.put(id, user) == null;
    }

    /**
     * 返回用户列表
     * @return
     */
    public Collection<User> findAll() {
        return repository.values();
    }
}

package com.example.bankingsystem.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockManager {
    private Map<Long, ReentrantLock> locks;

    public void lock(Long id){
        if(locks.containsKey(id)){
            locks.get(id).lock();
        }else{
            ReentrantLock lock = new ReentrantLock();
            locks.put(id, lock);
        }
    }

    public void unlock(Long id){
        locks.get(id).unlock();
    }
    
}

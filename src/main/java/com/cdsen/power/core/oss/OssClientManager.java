package com.cdsen.power.core.oss;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author HuSen
 * create on 2019/10/14 11:09
 */
public class OssClientManager {

    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    private static final Table<String, String, OssProperties> PROPERTIES_TABLE = HashBasedTable.create();

    private static final ConcurrentMap<String, OssProperties> USE_FOR_MAP = new ConcurrentHashMap<>();

    public static void init(List<OssProperties> propertiesList) {
        Lock writeLock = LOCK.writeLock();
        try {
            writeLock.lock();
            propertiesList.forEach(properties -> {
                PROPERTIES_TABLE.put(properties.getEndpoint(), properties.getBucketName(), properties);
                USE_FOR_MAP.put(properties.getUseFor(), properties);
            });
        } finally {
            writeLock.unlock();
        }
    }

    public static void refresh(List<OssProperties> propertiesList) {
        Lock writeLock = LOCK.writeLock();
        try {
            writeLock.lock();
            PROPERTIES_TABLE.clear();
            USE_FOR_MAP.clear();
            init(propertiesList);
        } finally {
            writeLock.unlock();
        }
    }

    public static OssClient getClient(String endpoint, String bucketName) {
        Lock readLock = LOCK.readLock();
        try {
            readLock.lock();
            if (PROPERTIES_TABLE.contains(endpoint, bucketName)) {
                return new OssClient(PROPERTIES_TABLE.get(endpoint, bucketName));
            } else {
                throw new IllegalArgumentException("can not find!");
            }
        } finally {
            readLock.unlock();
        }
    }

    public static OssClient getClient(String useFor) {
        Lock readLock = LOCK.readLock();
        try {
            readLock.lock();
            if (USE_FOR_MAP.containsKey(useFor)) {
                return new OssClient(USE_FOR_MAP.get(useFor));
            } else {
                throw new IllegalArgumentException("can not find!");
            }
        } finally {
            readLock.unlock();
        }
    }

    public static OssProperties getProperties(String endPoint, String bucketName) {
        Lock readLock = LOCK.readLock();
        try {
            readLock.lock();
            if (PROPERTIES_TABLE.contains(endPoint, bucketName)) {
                return PROPERTIES_TABLE.get(endPoint, bucketName);
            } else {
                throw new IllegalArgumentException("can not find!");
            }
        } finally {
            readLock.unlock();
        }
    }

    public static OssProperties getProperties(String useFor) {
        Lock readLock = LOCK.readLock();
        try {
            readLock.lock();
            if (USE_FOR_MAP.containsKey(useFor)) {
                return USE_FOR_MAP.get(useFor);
            } else {
                throw new IllegalArgumentException("can not find!");
            }
        } finally {
            readLock.unlock();
        }
    }
}

package com.cdsen.power.core.oss;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
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

    public static void init(List<OssProperties> propertiesList) {
        Lock writeLock = LOCK.writeLock();
        try {
            writeLock.lock();
            propertiesList.forEach(properties -> PROPERTIES_TABLE.put(properties.getEndpoint(), properties.getBucketName(), properties));
        } finally {
            writeLock.unlock();
        }
    }

    public static void refresh(List<OssProperties> propertiesList) {
        Lock writeLock = LOCK.writeLock();
        try {
            writeLock.lock();
            PROPERTIES_TABLE.clear();
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
}

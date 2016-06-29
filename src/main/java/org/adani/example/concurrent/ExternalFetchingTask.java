package org.adani.example.concurrent;


import org.apache.commons.lang.NotImplementedException;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExternalFetchingTask<T> implements Runnable {

    private final long fetchInterval;
    private AtomicBoolean running;

    public ExternalFetchingTask(long fetchInterval) {
        this.fetchInterval = fetchInterval;
        running = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        while (running.get()) {
            // TODO: fetch according to a schedule.
        }
    }

    private T fetchExternal() {
        //TODO: Fetch External Data
        throw new NotImplementedException("Method yet not implemented");
    }

    private T writeInternal() {
        // TODO: write to internal to local DB
        throw new NotImplementedException("Method yet not implemented");
    }
}

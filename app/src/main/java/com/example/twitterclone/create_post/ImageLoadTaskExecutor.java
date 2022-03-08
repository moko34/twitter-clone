package com.example.twitterclone.create_post;

import java.util.concurrent.Executor;

public class ImageLoadTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}

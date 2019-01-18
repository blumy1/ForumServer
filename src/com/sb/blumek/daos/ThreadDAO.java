package com.sb.blumek.daos;

import com.sb.blumek.models.Post;
import com.sb.blumek.models.Thread;
import com.sb.blumek.utils.DBManager;

import java.util.List;

public class ThreadDAO {

    public List<Thread> getThreads() {
        return DBManager.getThreads();
    }

    public Thread getThread(Integer threadId) {
        return DBManager.getThread(threadId);
    }

    public Thread getLastThread() {
        return DBManager.getLastThread();
    }

    public Thread createThread(Thread thread) {
        return DBManager.createThread(thread);
    }

    public List<Post> getThreadPosts(Integer threadId) {
        return DBManager.getThreadPosts(threadId);
    }

    public Post getThreadPost(Integer threadId, Integer postId) {
        return DBManager.getThreadPost(threadId, postId);
    }

    public Post getLastThreadPost(Integer threadId) {
        return DBManager.getLastThreadPost(threadId);
    }

    public void createThreadPost(Integer threadId, Post post) {
        DBManager.createThreadPost(threadId, post);
    }


}

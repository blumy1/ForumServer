package com.sb.blumek;

import com.sb.blumek.webservices.CategoryWebservice;
import com.sb.blumek.webservices.ThreadWebservice;
import com.sb.blumek.webservices.UserWebservice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ForumApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( UserWebservice.class );
        h.add( ThreadWebservice.class );
        h.add( CategoryWebservice.class );
        return h;
    }
}

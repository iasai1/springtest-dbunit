package com.diana.server;

/*
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.JspPropertyGroupServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class EmbeddedJetty {

    static final Logger LOG = LoggerFactory.getLogger(EmbeddedJetty.class);

    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/";
    private static final String WEBAPP_DIRECTORY = "C:\\Users\\ishipitc\\IdeaProjects\\TestThang\\src\\main\\webapp";
    private static final String CONFIG_PACKAGE_LOCATION = "com.diana.config";

    private Server server;

    public EmbeddedJetty(int PORT) throws Exception{
        startJetty(PORT);
    }

    private void startJetty(int port) throws Exception {
        LOG.info("Stating embedded Jetty at " + port);
        this.server = new Server(port);
        this.server.setHandler(getServletContextHandler());

        this.server.start();
        LOG.info("Jetty server started at " + port);
        this.server.join();
        LOG.info("Joined Jetty server started at " + port);

    }

    public void stopJetty() throws Exception {
        LOG.info("Stopping embedded Jetty server");
        this.server.stop();
        LOG.info("Jetty server was stopped");
    }

    private static ServletContextHandler getServletContextHandler() throws IOException{
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setErrorHandler(null);

        contextHandler.setResourceBase(WEBAPP_DIRECTORY);
        contextHandler.setContextPath(CONTEXT_PATH);

        contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
        contextHandler.addServlet(JspPropertyGroupServlet.class, "*.jsp");

        WebApplicationContext wac = getWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(wac);
        ServletHolder springServletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);
        contextHandler.addServlet(springServletHolder, MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(wac));

        return contextHandler;
    }

    private static WebApplicationContext getWebApplicationContext(){
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_PACKAGE_LOCATION);
        return context;
    }

}
*/
/*
 * Copyright The mod_cluster Project Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.modcluster.demo;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * @author Radoslav Husar
 */
@WebListener
public class ContextParamConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Set the context parameter programmatically
        sce.getServletContext().setInitParameter("duration", "15");
    }

}

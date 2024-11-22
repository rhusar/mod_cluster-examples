/*
 * Copyright The mod_cluster Project Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.modcluster.demo.servlet;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Paul Ferraro
 * @author Radoslav Husar
 */
@WebServlet(
        name = "cpu",
        urlPatterns = {"/cpu"}
)
public class SystemLoadServlet extends LoadServlet {

    @Serial
    private static final long serialVersionUID = 5665079393261425098L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();

        int duration = Integer.parseInt(this.getParameter(request, DURATION, "15")) * 1000;

        this.log("Begin handling system load request");

        // Naughty loop
        while (System.currentTimeMillis() - start < duration) {
            if ((System.currentTimeMillis() % 10) == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        this.log("End handling system load request");

        this.writeLocalName(request, response);
    }
}
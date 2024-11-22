/*
 * Copyright The mod_cluster Project Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.modcluster.demo.servlet;

import java.io.IOException;
import java.io.Serial;
import java.util.Collections;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author Paul Ferraro
 * @author Radoslav Husar
 */
@WebServlet(
        name = "record",
        urlPatterns = {"/record"}
)
public class RecordServlet extends LoadServlet {

    @Serial
    private static final long serialVersionUID = -4143320241936636855L;

    private static final String DESTROY = "destroy";
    private static final String TIMEOUT = "timeout";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        boolean destroy = Boolean.parseBoolean(request.getParameter(DESTROY));

        if (destroy) {
            session.invalidate();
        } else {
            String timeout = request.getParameter(TIMEOUT);

            if (timeout != null) {
                session.setMaxInactiveInterval(Integer.parseInt(timeout));
            }
        }

        if (!request.getAttributeNames().hasMoreElements()) {
            System.out.println("No request attributes");
        }
        for (String attribute : Collections.list(request.getAttributeNames())) {
            System.out.println(attribute + " = " + request.getAttribute(attribute));
        }
        response.setHeader("X-ClusterNode", this.getJvmRoute());

        this.writeLocalName(request, response);
    }
}

/*
 * Copyright The mod_cluster Project Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.modcluster.demo.servlet;

import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.util.Collections;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Paul Ferraro
 * @author Radoslav Husar
 */
@WebServlet(
        name = "send",
        urlPatterns = {"/send"},
        initParams = {
                @WebInitParam(name = "size", value = "100")
        }
)
public class SendTrafficLoadServlet extends LoadServlet {

    @Serial
    private static final long serialVersionUID = -8586013739155819909L;
    private static final String SIZE = "size";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int size = Integer.parseInt(request.getParameter(SIZE)) * 1024;

        this.log("Writing " + (size / 1024) + "KB blob to response for: " + request.getRequestURL().toString());

        response.getOutputStream().write(new byte[size]);
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int duration = Integer.parseInt(this.getParameter(request, DURATION, DEFAULT_DURATION));

        String size = this.getParameter(request, SIZE, "100");
        URI uri = this.createLocalURI(request, Collections.singletonMap(SIZE, size));

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            for (int i = 0; i < duration; ++i) {
                this.log("Sending send traffic load request to: " + uri);

                long start = System.currentTimeMillis();

                HttpClientUtils.closeQuietly(client.execute(new HttpPost(uri)));

                long ms = 1000 - (System.currentTimeMillis() - start);

                if (ms > 0) {
                    try {
                        Thread.sleep(ms);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        this.writeLocalName(request, response);
    }
}

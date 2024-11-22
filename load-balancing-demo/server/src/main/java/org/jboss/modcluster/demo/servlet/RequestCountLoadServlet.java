/*
 * Copyright The mod_cluster Project Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.modcluster.demo.servlet;

import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.util.Collections;

import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Paul Ferraro
 * @author Radoslav Husar
 */
@WebServlet(
        name = "requests",
        urlPatterns = {"/requests"},
        initParams = {
                @WebInitParam(name = "count", value = "50")
        }
)
public class RequestCountLoadServlet extends LoadServlet {

    @Serial
    private static final long serialVersionUID = -5001091954463802789L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int count = Integer.parseInt(this.getParameter(request, COUNT, "50"));

        if (count > 1) {
            URI uri = this.createLocalURI(request, Collections.singletonMap(COUNT, String.valueOf(count - 1)));

            this.log("Sending request count request to: " + uri);

            try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
                HttpClientUtils.closeQuietly(client.execute(new HttpGet(uri)));
            }
        }

        this.writeLocalName(request, response);
    }
}

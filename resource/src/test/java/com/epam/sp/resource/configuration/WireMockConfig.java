package com.epam.sp.resource.configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

public class WireMockConfig {
    public static void configureStubs() {
        stubFor(post(urlPathMatching("/api/v1/songs"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("""
                                {
                                    "id": 1
                                }
                                """)));
    }
}

package org.example.jpr.util;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.*;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeoutException;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    public static String sendGetRequest(String urlString) {
        HttpRequest request = new HttpRequest(HttpMethod.GET, urlString);
        Mono<Response<String>> response =
                stringResponse(HTTP_PIPELINE.send(request)
                        .flatMap(response1 -> {
                            int code = response1.getStatusCode();
                            if (code == 200 || code == 400 || code == 404) {
                                return Mono.just(response1);
                            } else {
                                return Mono.error(new HttpResponseException(response1));
                            }
                        })
                        .retryWhen(Retry
                                .fixedDelay(5, Duration.ofSeconds(120))
                                .filter(t -> {
                                    boolean retry = false;
                                    if (t instanceof TimeoutException) {
                                        retry = true;
                                    } else if (t instanceof HttpResponseException
                                            && ((HttpResponseException) t).getResponse().getStatusCode() == 503) {
                                        retry = true;
                                    }

                                    if (retry) {
                                        logger.info("retry GET request to {}", urlString);
                                    }
                                    return retry;
                                })));
        Response<String> ret = response.block();
        return ret == null ? null : ret.getValue();
    }

    private static final HttpPipeline HTTP_PIPELINE = new HttpPipelineBuilder()
            .policies(
                    new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BASIC)),
                    new RetryPolicy("Retry-After", ChronoUnit.SECONDS))
            .build();

    private static Mono<Response<String>> stringResponse(Mono<HttpResponse> responseMono) {
        return responseMono.flatMap(response -> response.getBodyAsString()
                .map(str -> new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), str)));
    }
}

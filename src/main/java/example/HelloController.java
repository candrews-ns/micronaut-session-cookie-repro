/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.RxProxyHttpClient;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.session.Session;
import io.micronaut.validation.Validated;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.net.URI;

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@Controller("/")
@Validated
public class HelloController {

    @Inject
    RxProxyHttpClient httpClient;

    @Get(uri = "/")
    public HttpResponse<?> root(Session session) {
        session.put("foo", "bar");
        URI uri = UriBuilder.of("http://localhost:8080/hello/foo").build();
        MutableHttpRequest<?> req = HttpRequest.GET(uri);
        return httpClient.proxy(req).blockingFirst();
    }

    @Get(uri = "/hello/{name}", produces = MediaType.TEXT_PLAIN)
    public Single<String> hello(@NotBlank String name) {
        return Single.just("Hello " + name + "!");
    }
}

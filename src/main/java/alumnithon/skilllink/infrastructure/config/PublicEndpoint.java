package alumnithon.skilllink.infrastructure.config;

import org.springframework.http.HttpMethod;

public class PublicEndpoint {
    private final String url;
    private final HttpMethod method;

    public PublicEndpoint(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String url() {
        return url;
    }

    public HttpMethod method() {
        return method;
    }
}

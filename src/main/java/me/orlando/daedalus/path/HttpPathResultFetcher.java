package me.orlando.daedalus.path;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPathResultFetcher implements PathResultFetcher {

    private final HttpClient client = HttpClient.newHttpClient();
    private final URI moveUrl;

    public HttpPathResultFetcher(String moveUrl) {
        try {
            this.moveUrl = new URI(moveUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result fetch(Path path) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Cookie", "path=" + path.encoded())
                .uri(moveUrl)
                .build();

        String body = null;
        while (body == null) {
            try {
                body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } catch (Exception ignored) {}
        }

        if (body.contains("BONK!")) {
            return Result.BONK;
        } else if (body.contains("avance bien!")) {
            return Result.MOVING;
        }

        return Result.WIN;
    }
}

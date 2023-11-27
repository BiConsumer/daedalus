package me.orlando.daedalus.path;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPathResolver implements PathResolver {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String moveUrl;

    public HttpPathResolver(String moveUrl) {
        this.moveUrl = moveUrl;
    }

    @Override
    public Result resolve(Path path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Cookie", "path=" + path.encoded())
                .uri(new URI(moveUrl))
                .build();

        System.out.println("SENDING REQUEST");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("RESPONSE!");
        String body = response.body();

        if (body.contains("BONK!")) {
            return Result.BONK;
        } else if (body.contains("avance bien!")) {
            return Result.MOVING;
        }

        return Result.WIN;
    }
}

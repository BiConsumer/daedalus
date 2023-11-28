package me.orlando.daedalus.result;

import me.orlando.daedalus.Path;

public interface PathResultFetcher {

    Result fetch(Path path);

    enum Result {
        BONK,
        MOVING,
        WIN
    }

}

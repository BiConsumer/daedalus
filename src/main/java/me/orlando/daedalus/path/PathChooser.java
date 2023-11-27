package me.orlando.daedalus.path;

import java.util.Map;

public interface PathChooser {

    Path choose(Map<Path, PathResolver.Result> previous);

}

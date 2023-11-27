package me.orlando.daedalus.path;

import java.util.Collection;
import java.util.Optional;

public interface PathChooser {

    Path choose(Collection<Path> previous);

}

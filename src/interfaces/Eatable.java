package interfaces;

import islandOccupants.IslandOccupant;

public interface Eatable {
    boolean eat(IslandOccupant occupant);

    void nutritionProcess(IslandOccupant occupant);
}

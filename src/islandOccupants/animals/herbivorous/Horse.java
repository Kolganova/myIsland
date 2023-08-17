package islandOccupants.animals.herbivorous;

import enums.CreationType;
import enums.OccupantType;
import island.Location;

public class Horse extends Herbivorous {

    public Horse(Location location, OccupantType type, CreationType creationType) {
        super(location, type, creationType);
        initAnimal(20, false, 400, 60, 4);
        location.addOccupantInLocation(this);
    }
}

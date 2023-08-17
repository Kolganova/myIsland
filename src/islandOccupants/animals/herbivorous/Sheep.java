package islandOccupants.animals.herbivorous;

import enums.CreationType;
import enums.OccupantType;
import island.Location;

public class Sheep extends Herbivorous {

    public Sheep(Location location, OccupantType type, CreationType creationType) {
        super(location, type, creationType);
        initAnimal(140, false, 70, 15, 3);
        location.addOccupantInLocation(this);
    }

}

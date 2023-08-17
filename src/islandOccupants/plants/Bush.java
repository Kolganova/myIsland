package islandOccupants.plants;

import enums.CreationType;
import enums.OccupantType;
import island.Location;

public class Bush extends Plant {

    public Bush(Location location, OccupantType type, CreationType creationType) {
        super(location, type, creationType);
        initPlant(50, false, 3, 1);
        location.addOccupantInLocation(this);
    }

}

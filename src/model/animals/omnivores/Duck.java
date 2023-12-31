package model.animals.omnivores;

import enums.types.CreationType;
import enums.types.OccupantType;
import island.Location;

public class Duck extends Omnivores {

    public Duck(Location location, OccupantType type, CreationType creationType) {
        super(location, type, creationType);
        initAnimal(200, false, 1, 0.15, 4);
        location.addOccupantInLocation(this);
    }

}

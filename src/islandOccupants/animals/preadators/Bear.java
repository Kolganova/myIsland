package islandOccupants.animals.preadators;

import enums.CreationType;
import enums.OccupantType;
import island.Location;

public class Bear extends Predator {

    public Bear(Location location, OccupantType type, CreationType creationType) {
        super(location, type, creationType);
        setMaxAmountOfOccupants(5);
        setWeight(500.0);
        setBellySize(80.0);
        setCurrentSatiety(getRandom().nextDouble(this.getBellySize().get()));
        setSatietyCostOnMove(getBellySize().get() / 5);
        setMaxAmountOfMoves(2);
        location.addOccupantInLocation(this);
    }

}

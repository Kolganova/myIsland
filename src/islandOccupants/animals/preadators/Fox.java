package islandOccupants.animals.preadators;

import enums.AnimalCreationType;
import island.Location;

public class Fox extends Predator {

    public Fox(Location location, String type, AnimalCreationType creationType) {
        super(location, type, creationType);
        setMaxAmountOfOccupants(30);
        setWeight(8.0);
        setBellySize(2.0);
        setSatietyCostOnMove(getBellySize().get() / 5);
        setMaxAmountOfMoves(2);
        location.addOccupantInLocation(this);
    }

}

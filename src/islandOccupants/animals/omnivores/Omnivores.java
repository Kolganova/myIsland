package islandOccupants.animals.omnivores;

import enums.CreationType;
import interfaces.EatableAnimals;
import interfaces.EatableDeadAnimals;
import island.Location;
import islandOccupants.IslandOccupant;
import islandOccupants.animals.Animal;
import islandOccupants.animals.herbivorous.Caterpillar;
import islandOccupants.animals.herbivorous.Mouse;
import islandOccupants.deadAnimals.DeadAnimal;
import islandOccupants.plants.Plant;

public abstract class Omnivores extends Animal implements EatableAnimals, EatableDeadAnimals {

    public Omnivores(Location location, String type, CreationType creationType) {
        super(location, type, creationType);
    }

    @Override
    public synchronized boolean eat(IslandOccupant occupant) {
        if (occupant instanceof Plant) {
            if (this.isIsPoisonProtected()) {
                nutritionProcess(this, occupant);
            } else {
                this.die();
                occupant.die();
            }
            return true;
        } else if (occupant instanceof DeadAnimal && getRandom().nextBoolean()) {
            return eatDeadAnimal(this, (DeadAnimal) occupant);
        } else {
            return eatAnimal((Animal) occupant);
        }

    }

    @Override
    public boolean eatAnimal(Animal victim) {
        if (victim instanceof Caterpillar || (victim instanceof Mouse && this instanceof Boar)) {
            if (getRandom().nextInt(100) <= 60) {
                nutritionProcess(this, victim);
                return true;
            }
        }

        return false;
    }

}

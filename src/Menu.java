import island.Island;
import island.Location;
import islandOccupants.IslandOccupant;
import islandOccupants.animals.Animal;
import islandOccupants.deadAnimals.DeadAnimal;
import islandOccupants.plants.Plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {

    public static void playInSimulation(Island island) {
        System.out.println("Добрый день!\nВы открыли симуляцию очень реалистичного острова \"" + island.getName() +
                "\".\nВводите \"play\" для того, что бы наш островок прожил свой очередной жалкий цикл (10 дней) " +
                "или введите \"stop\" для уничтожения несчастного островка.\nЕсли все животные умрут, " +
                "то симуляция так же закончится.");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if ("play".equals(command)) {
                live10DaysAtIsland(island);
                if (isThereNoAliveAnimal()) {
                    System.out.println("______________________________");
                    System.out.println("Эх, все животные умерли :(");
                    break;
                }
                showStatistic();
                        // мб сделать остров Singleton
            } else if ("stop".equals(command)) {
                break;
            }
        }
        sc.close();
        // почему сразу появляются мертвые животные?
    }

    private static void showStatistic() {
        System.out.println("______________________________");
        System.out.println("Текущее количество животных на островке " + Island.getAmountOfAnimals());
        System.out.println("Текущее количество мертвых животных (мяса) на островке " + Island.getAmountOfDeadAnimals());
        System.out.println("Текущее количество растений на островке " + Island.getAmountOfPlants());
        // мб добавить более детализированную статистику если будет время
    }

    private static void live10DaysAtIsland(Island island) {
        for (CopyOnWriteArrayList<Location> list : island.getListOfLocations()) {
            for (Location location : list) {
                live10DaysAtLocation(location.getListOfOccupants(), island);
            }
        }
    }

    private static void live10DaysAtLocation(CopyOnWriteArrayList<IslandOccupant> listOfOccupants, Island island) {
        for (int i = 0; i < 10; i++) {
            liveADayAtLocation(listOfOccupants, island);
        }
        listOfOccupants.parallelStream()
                .filter(occupant -> occupant instanceof Animal)
                .map(occupant -> (Animal) occupant)
                .forEach(animal -> animal.setMoveCounter(0));
    }


    private static void liveADayAtLocation(CopyOnWriteArrayList<IslandOccupant> listOfOccupants, Island island) {

        List<IslandOccupant> temp = new ArrayList<>(listOfOccupants);
        Collections.shuffle(temp);
        listOfOccupants.clear();
        listOfOccupants.addAll(temp);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);
        for (AtomicInteger i = new AtomicInteger(); i.get() <= listOfOccupants.size(); i.getAndIncrement()) {
            completionService.submit(() -> {
                IslandOccupant occupant = listOfOccupants.get(i.get());
//                synchronized (occupant) {
                    if (occupant instanceof Plant) {
                        ((Plant) occupant).actLikePlant();
                    } else if (occupant instanceof Animal) {
                        int dice = occupant.getRandom().nextInt(100);
                        if (dice <= 40) {
                            int counter = 0;
                            while (true) {
                                int indexOfVictim = occupant.getRandom().nextInt(listOfOccupants.size());
                                if (indexOfVictim != i.get()) {
                                    boolean didEat = ((Animal) occupant).actLikeEatingAnimal(listOfOccupants.get(indexOfVictim));
                                    if (didEat || counter > 4)
                                        break;
                                }
                                counter++;
                            }
                            ((Animal) occupant).actLikeAnimal();
                        } else if (dice <= 75) {
                            int counter = 0;
                            while (true) {
                                int indexOfPartner;
                                do {
                                    indexOfPartner = occupant.getRandom().nextInt(listOfOccupants.size());
                                } while (indexOfPartner == i.get());
                                boolean didMultiply = ((Animal) occupant).actLikeMultipliableAnimal(
                                        (Animal) listOfOccupants.get(indexOfPartner));
                                if (didMultiply || counter > 9)
                                    break;
                                counter++;
                            }
                            ((Animal) occupant).actLikeAnimal();
                        } else {
                            ((Animal) occupant).actLikeMovingAnimal(island.getListOfLocations());
                        }
                    } else {
                        ((DeadAnimal) occupant).actLikeDeadAnimal();
                    }
//                }

                return null;
            });
        }

        executor.shutdown();

    }

    private static boolean isThereNoAliveAnimal() {

        return Island.getAmountOfAnimals().get() <= 0;
    }

}

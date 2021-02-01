package ca.ulaval.glo4002.reservation.reports.chef.domain;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.chef.domain.exceptions.NoPossibleChefException;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChefAssignator {

  private static final int MAX_NUMBER_OF_CLIENTS_SERVED = 5;

  private final List<Chef> allChefs;

  public ChefAssignator(List<Chef> chefs) {
    this.allChefs = chefs;
  }

  public Set<Chef> getChefsForDay(List<Restriction> restrictions) {
    return getChefsForTheReport(restrictions);
  }

  public void validateChefsForTheDay(List<Restriction> restrictions)
      throws NoPossibleChefException {
    Set<Set<Chef>> allThePossibleSolutions = generateAllTheChefCombinations(allChefs);
    boolean combinationExists = false;
    for (Set<Chef> combination : allThePossibleSolutions) {
      if (combinationCanFeedThePeople(combination, restrictions)) {
        combinationExists = true;
        break;
      }
    }
    if (!combinationExists) {
      throw new NoPossibleChefException();
    }
  }

  public Money priceOfChefsForTheDay(List<Restriction> restrictions) {
    Set<Chef> chefsForTheDay = getChefsForTheReport(restrictions);
    Money costOfAllChefs = new Money(0);
    for (Chef chef : chefsForTheDay) {
      costOfAllChefs = costOfAllChefs.add(chef.getPrice());
    }
    return costOfAllChefs;
  }

  private Set<Chef> getChefsForTheReport(List<Restriction> restrictions) {
    Set<Set<Chef>> allThePossibleSolutions = generateAllTheChefCombinations(allChefs);
    Set<Chef> currentBestCombination = new HashSet<>();
    for (Set<Chef> combination : allThePossibleSolutions) {
      if (combinationCanFeedThePeople(combination, restrictions)) {
        if (currentBestCombination.isEmpty()) {
          currentBestCombination = combination;
        } else if (combinationIsBetterThanOldOne(combination, currentBestCombination)) {
          currentBestCombination = combination;
        }
      }
    }
    return currentBestCombination;
  }

  private boolean combinationIsBetterThanOldOne(
      Set<Chef> combination, Set<Chef> currentBestCombination) {
    if (combination.size() < currentBestCombination.size()) {
      return true;
    } else if (combination.size() == currentBestCombination.size()) {
      return combinationHasBetterPriority(combination, currentBestCombination);
    }
    return false;
  }

  private boolean combinationHasBetterPriority(
      Set<Chef> combination, Set<Chef> currentBestCombination) {
    List<Chef> newCombination = new ArrayList<>(combination);
    List<Chef> currentCombination = new ArrayList<>(currentBestCombination);
    List<Integer> newChefPriorities = new ArrayList<>();
    List<Integer> currentChefPriorities = new ArrayList<>();

    for (int i = 0; i < combination.size(); i++) {
      newChefPriorities.add(allChefs.indexOf(newCombination.get(i)));
      currentChefPriorities.add(allChefs.indexOf(currentCombination.get(i)));
    }

    newChefPriorities.sort(Integer::compareTo);
    currentChefPriorities.sort(Integer::compareTo);

    for (int i = 0; i < newChefPriorities.size(); i++) {
      if (newChefPriorities.get(i) < currentChefPriorities.get(i)) {
        return true;
      } else if (newChefPriorities.get(i) > currentChefPriorities.get(i)) {
        return false;
      }
    }
    return false;
  }

  private boolean combinationCanFeedThePeople(Set<Chef> chefs, List<Restriction> restrictions) {
    List<ChefOccupation> chefsForTheDay = new ArrayList<>();
    for (Chef chef : chefs) {
      chefsForTheDay.add(new ChefOccupation(chef));
    }
    for (Restriction restriction : restrictions) {
      boolean restritionServed = false;
      for (int i = 0; i < chefsForTheDay.size() && !restritionServed; i++) {
        if ((chefsForTheDay.get(i).getChef().hasThisSpeciality(restriction.getType()))
            && chefsForTheDay.get(i).canServeAnotherClient()) {
          chefsForTheDay.get(i).addClientsServed();
          restritionServed = true;
        }
      }
      if (!restritionServed) {
        return false;
      }
    }
    return true;
  }

  private Set<Set<Chef>> generateAllTheChefCombinations(List<Chef> chefs) {
    Set<Set<Chef>> combinations = new HashSet<>();
    for (Chef chef : chefs) {
      Set<Chef> tempSet = new HashSet<>();
      tempSet.add(chef);
      combinations.add(tempSet);
    }

    return generateAllTheChefCombinations(combinations, chefs, 1, chefs.size());
  }

  private Set<Set<Chef>> generateAllTheChefCombinations(
      Set<Set<Chef>> combinations, List<Chef> chefs, int currentSize, int maxsize) {
    Set<Set<Chef>> combinationsTemp = new HashSet<>();
    combinationsTemp.addAll(combinations);
    if (currentSize < maxsize) {
      for (Set<Chef> set : combinations) {
        Set<Chef> tempSet = new HashSet<>();
        tempSet.addAll(set);

        for (Chef chef : chefs) {
          tempSet.add(chef);
          combinationsTemp.add(tempSet);
          tempSet = new HashSet<>();
          tempSet.addAll(set);
        }
      }

      return generateAllTheChefCombinations(combinationsTemp, chefs, currentSize + 1, maxsize);
    }
    return combinationsTemp;
  }

  private class ChefOccupation {

    private final Chef chef;
    private int clientsServed = 0;

    public ChefOccupation(Chef chef) {
      this.chef = chef;
    }

    public Chef getChef() {
      return chef;
    }

    public boolean canServeAnotherClient() {
      return clientsServed < MAX_NUMBER_OF_CLIENTS_SERVED;
    }

    public void addClientsServed() {
      clientsServed = clientsServed + 1;
    }
  }
}

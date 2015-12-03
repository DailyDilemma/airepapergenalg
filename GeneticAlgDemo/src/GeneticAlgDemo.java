/**
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author Josh Westlake
 *
 */
public class GeneticAlgDemo {
	
	static final int POPULATION = 10;
	static final int INTERVALS_PER_DAY = 96; // Each interval is 15 minutes.
	static int evalResults[] = new int[POPULATION]; // Stores the fitness measure for each population member
	static final int NUM_ROUNDS = 10000;
	static final int THRESHOLD = 96;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean population[][] = new boolean[POPULATION][INTERVALS_PER_DAY];
		int currentBest = 0;
		
		// Create a population of random schedules
		initialize(population);		
		//printPopulation(population, false, "After Initialization:");	

		evaluate(population);
		printPopulation(population, true, "Initial Population: (Randomly Generated)");		
					
		for (int i=0; i<NUM_ROUNDS && currentBest<THRESHOLD; i++){
				
			currentBest = evalResults[indexOfBestFit()];
			
			System.out.println(String.format("Round %4d", i));
			
			population = selection(population);
			//printPopulation(population, false, "After Selection:");
			
			crossover(population);
			printPopulation(population, true, "After Crossover:");
			
			mutation(population);
			printPopulation(population, true, "After Mutation:");
			
			evaluate(population);
			printPopulation(population, true, "After Evaluation:");			
		}
	}
	
	public static void initialize(boolean[][] population) {
		
		// Initialize an array of randomized possible schedules
		for (int i=0; i<POPULATION; i++) {
			for (int j=0; j<INTERVALS_PER_DAY; j++) {
				if (Math.random() < 0.5) {
					population[i][j] = true;
				} else {
					population[i][j] = false;
				}			
			}						
		}
		
	}
	
	public static void printPopulation(boolean[][] population, boolean withScores, String message) {
		
		if (message.length() > 0) {
			System.out.println(message);
		}
		
		if (withScores) {
			evaluate(population);
		}
		
		// Initialize an array of randomized possible schedules
		for (int i=0; i<POPULATION; i++) {
			System.out.print(String.format("%2d: ", i+1));
			for (int j=0; j<INTERVALS_PER_DAY; j++) {
				if (population[i][j]) {
					System.out.print("X");
				} else {
					System.out.print(" ");
				}
			}
			
			if (withScores) {
				System.out.println(" (fitness:" + evalResults[i] + ")");
			} else {
				System.out.println("");				
			}
		}		
		
		System.out.println();
		
	}

	public static int fitnessFunction(boolean[] schedule) {
		
		int fitness = 0;
		int intervalsSinceLastBreak = 0;
		
		for (int i=0; i<INTERVALS_PER_DAY; i++) {
		
			if (schedule[i]) {
				fitness++;
				intervalsSinceLastBreak++;
			} else {
				intervalsSinceLastBreak = 0;
			}
			
			if (intervalsSinceLastBreak > 8) {
				fitness--;
			}
						
		}
				
		return fitness;
		
	}
	
	public static void evaluate(boolean[][] population) {
		
		// Initialize an array of randomized possible schedules
		for (int i=0; i<POPULATION; i++) {
			evalResults[i] = fitnessFunction(population[i]);			
		}				
		
	}

	public static void printEvalResults() {
		
		// Initialize an array of randomized possible schedules
		for (int i=0; i<POPULATION; i++) {
			System.out.print(evalResults[i] + ",");	
		}			
				
	}
	
	public static int indexOfBestFit() {
		
		int val=0;
		int index=-1;
		
		for (int i=0; i<evalResults.length; i++) {
			if (evalResults[i] > val) {
				val = evalResults[i];
				index = i;
			}
		}
		
		return index;
		
	}
	
	public static boolean[][] selection(boolean[][] population) {
		
		boolean[][] newPopulation = new boolean[POPULATION][INTERVALS_PER_DAY];
		int bestFitPos = indexOfBestFit();
		int i; // counter
		int selectedPos;
		
		// Create a list of eligible positions to be picked from
		List<Integer> schedPos = new ArrayList<Integer>();
		for (i=0; i<POPULATION; i++) {
			schedPos.add(i);
		}
		
		// Put the best fit solution in first and remove it from the list
		System.arraycopy(population[bestFitPos], 0, newPopulation[0], 0, INTERVALS_PER_DAY);
		schedPos.remove(bestFitPos);
		
		// Pick the next 4 "mates" randomly from the remaining population
		for (i=1; i<(POPULATION/2); i++) {
			selectedPos = (int) (Math.random() * schedPos.size());
			newPopulation[i] = population[selectedPos];
			schedPos.remove(selectedPos);
		}		
		
		return newPopulation;
		
	}
	
	public static void crossover(boolean[][] population) {
		
		// Create new population members by breeding the most fit member 
		// with the 4 other members chosen at random
		population[5] = crossMembers(population[0], population[1]);
		population[6] = crossMembers(population[0], population[2]);
		population[7] = crossMembers(population[0], population[3]);
		population[8] = crossMembers(population[0], population[4]);
		
		// Breed 2 of the randomly chosen members with each other
		population[9] = crossMembers(population[1], population[4]);
		
	}
	
	public static boolean[] crossMembers(boolean[] member1, boolean[] member2) {
		
		boolean[] newMember = new boolean[INTERVALS_PER_DAY];
		
		for (int i=0; i<INTERVALS_PER_DAY; i++) {
			if (Math.random() < 0.5) {
				newMember[i] = member1[i];
			} else {
				newMember[i] = member2[i];
			}
		}
		
		return newMember;
		
	}
	
	public static void mutation(boolean[][] population) {
		
		int posToMutate = 0;
		
		// for each member there is a 50% chance that one of it's chromosomes will
		// be selected for mutation (the best fit member is never mutated)
		for (int i=1; i<POPULATION; i++) {
			
			if (Math.random() < 0.5) {
				posToMutate = (int)(Math.random() * INTERVALS_PER_DAY);							
				if (population[i][posToMutate]) {
					population[i][posToMutate] = false;
				} else {
					population[i][posToMutate] = true;					
				}
			}			
			
		}
		
	}
	
}

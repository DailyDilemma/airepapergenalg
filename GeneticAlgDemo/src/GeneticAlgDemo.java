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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean population[][] = new boolean[POPULATION][INTERVALS_PER_DAY];
		//System.out.println("Hello World!");			
		
		// Create a population of random schedules
		initialize(population);				
		//printPopulation(population, false);
		evaluate(population);
		printPopulation(population, true);
		population = selection(population);
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
	
	public static void printPopulation(boolean[][] population, boolean withScores) {
		
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
		
	}

	public static int fitnessFunction(boolean[] schedule) {
		
		int fitness = 0;
		
		for (int i=0; i<INTERVALS_PER_DAY; i++) {
			if (schedule[i]) {
				fitness++;
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
		newPopulation[0] = population[bestFitPos];
		schedPos.remove(bestFitPos);
		
		// Pick the next 4 "mates" randomly from the remaining population
		for (i=1; i<(POPULATION/2); i++) {
			selectedPos = (int) (Math.random() * (schedPos.size() + 1));
			newPopulation[i] = population[selectedPos];
			schedPos.remove(selectedPos);
		}		
		
		return newPopulation;
		
	}
	
	public static void crossover(boolean[][] population) {
		
		int crossoverPoint = 0;
		int i;
		
		crossoverPoint = (int)(Math.random() * INTERVALS_PER_DAY);
		
		// Create the last half of the next generation by breeding the elite/best fit population
		// member with each of the 
		for (i=(POPULATION/2); i<POPULATION; i++) {
			
		}
		
	}
	
	public static boolean[] crossMembers(boolean[] member1, boolean[2])
	
}

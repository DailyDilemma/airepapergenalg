/**
 * 
 */

/**
 * @author Josh Westlake
 *
 */
public class GeneticAlgDemo {
	
	static final int POPULATION = 40;
	static final int MIN_PER_INTERVAL = 15;
	static final int MIN_PER_DAY = 3600;
	static final int INTERVALS_PER_DAY = MIN_PER_INTERVAL / MIN_PER_DAY;
	

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean population[][] = new boolean[INTERVALS_PER_DAY][POPULATION];
		System.out.println("Hello World!");			
		
		// Create a population of random schedules
		initialize(population);
		
	}
	
	public static void initialize(boolean[][] population) {
		
		// Initialize an array of randomized possible schedules
		for (int i=0; i<INTERVALS_PER_DAY; i++) {
			for (int j=0; j<POPULATION; j++) {
				if (Math.random() < 0.5) {
					population[i][j] = true;
				} else {
					population[i][j] = false;
				}			
			}						
		}
		
	}

}

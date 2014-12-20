

/**
 * @author praks
 * 
 */
public class RandomGenerator {
	public static int genRandom(int low, int high) {
		int random = low + (int) (Math.random() * (high - low)); // +1 -->
																	// includes
																	// the
																	// highest
																	// number
		// System.out.println("random:"+random);
		return random;
	}

	public static double genRandomDouble(double low, double weightRange) {
		// TODO Auto-generated method stub
		double random = low + (int) (Math.random() * (weightRange - low)); // +1 -->
		// includes
		// the
		// highest
		// number
		// System.out.println("random:"+random);
		return random;
	}
}

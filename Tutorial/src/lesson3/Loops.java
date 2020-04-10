package lesson3;

public class Loops {

	public static void main(String[] args) {

//		boolean loop = true;
//		boolean myLoop = 4<5;
//		System.out.println(loop);
//		System.out.println(myLoop);

		// infinite loop
		int value = 0;
		while (value < 7) {
			System.out.println("hello " + value);
			// stop the loop (will stop when value will be more than 7) 
			value = value + 1;
		}

	}
}

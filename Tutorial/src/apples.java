import java.util.Scanner;

class apples {
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		carrot c1 = new carrot();
		String name  = input.next();
		c1.printName(name);
		name = input.next();
		c1.printName(name);
	}
}

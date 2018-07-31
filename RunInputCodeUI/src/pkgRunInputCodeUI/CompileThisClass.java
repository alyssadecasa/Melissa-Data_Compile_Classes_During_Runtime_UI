/**
 * Project RunInputCodeUI
 * CompileThisClass.java
 * 
 * Sample code to have new code be inserted into it and be compiled (at runtime ?)
 */

package pkgRunInputCodeUI;

import java.io.PrintStream;

public class CompileThisClass {
	public int sum;
	private PrintStream print;

	public CompileThisClass() {
		//print = new PrintStream(new TextAreaOutputStream());
		//System.setOut(print);
		//System.setErr(print);

	}

	public static void main(String[] args) {
		CompileThisClass obj = new CompileThisClass();
		obj.doThing();
	}

	public void doThing() {
		sum = 1 + 1;
		System.out.println(sum);
		/* PLACEHOLDER */
	}
}
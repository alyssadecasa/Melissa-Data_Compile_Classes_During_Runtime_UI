/**
 * Project Compile-On-The-Fly-UI
 * CompileThisClass.java
 * 
 * Sample code to have new code be inserted into it and be compiled at runtime
 * 
 * @author alyssa
 */

package pkgRunInputCodeUI;

public class CompileThisClass {
	public int sum;

	public CompileThisClass() {
	}

	public static void main(String[] args) {
		CompileThisClass obj = new CompileThisClass();
		obj.doThing();
	}

	public void doThing() {
		sum = 1 + 1;
		System.out.println(sum);
		/* start */
		/* PLACEHOLDER */
		/* end */
	}
}
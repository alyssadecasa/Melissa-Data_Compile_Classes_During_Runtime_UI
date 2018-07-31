/**
 * Project RunInputCodeUI
 * Main.java
 * 
 * Main code to test features and functionality of project with GUI
 */

package pkgRunInputCodeUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class Main {

	static GuiWindow window;
	private static CompilableClass compilable;
	private static PrintStream print;
	private static final String PLACEHOLDER = "/* PLACEHOLDER */";
	private static int count = 0;

	public static void main(String[] args) {
		window = new GuiWindow();
		compilable = new CompilableClass();
		print = new PrintStream(new TextAreaOutputStream());
		System.setOut(print);
		System.setErr(print);
	}

	public static void submitButtonPressed() {
		window.clearOutputText();
		System.out.println("Modified class compiled and loaded.\n---");
		boolean noExceptionThrown = true;
		
		compilable.setInputCode(window.getUserCode());

		try {
			compilable.updateSourceClass(PLACEHOLDER);
		} catch (FileNotFoundException e) {
			System.out.println("Error trying to update source code: " 
					+ "source code file not found.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error trying to update source code: "
					+ "source code file incorrectly formatted.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (PlaceholderStringNotFoundException e) {
			System.out.println("Error trying to update source code: " 
					+ "placeholder string not found in source code file.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		try {
			compilable.compileSourceClass();
		} catch (IOException e) {
			System.out.println("Error trying to compile code: Invalid file destination.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		if (noExceptionThrown) {
			loadClass();
		}
		
	}
	
	public static void restoreButtonPressed() {
		window.clearOutputText();
		System.out.println("Defaults restored.\n---");
		boolean noExceptionThrown = true;

		compilable.setInputCode(PLACEHOLDER);
		try {
			compilable.updateSourceClass(compilable.getLastInputCode());
		} catch (FileNotFoundException e) {
			System.out.println("Error trying to update source code: " 
					+ "source code file not found.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error trying to update source code: "
					+ "source code file incorrectly formatted.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (PlaceholderStringNotFoundException e) {
			System.out.println("Error trying to update source code: " 
					+ "placeholder string not found in source code file.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		try {
			compilable.compileSourceClass();
		} catch (IOException e) {
			System.out.println("Error trying to compile code: Invalid file destination.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		if (noExceptionThrown) {
			loadClass();
		}
		
	}
	
	public static void loadClass() {
		Class<?> compileThisClass = null;
		try {
			compileThisClass = compilable.loadSourceClass();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Method m = null;
		try {
			m = compileThisClass.getMethod("main", String[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String[] params = null;
				m.invoke(null, (Object) params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

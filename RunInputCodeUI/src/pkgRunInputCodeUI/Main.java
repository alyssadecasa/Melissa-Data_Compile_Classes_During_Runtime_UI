/**
 * Project Compile-On-The-Fly-UI
 * Main.java
 * 
 * Main code to test features and functionality of project with GUI
 * 
 * @author alyssa
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

	public static void main(String[] args) {
		window = new GuiWindow();
		compilable = new CompilableClass();
		print = new PrintStream(new TextAreaOutputStream());
		System.setOut(print);
		System.setErr(print);
	}

	public static void submitButtonPressed() {
		window.clearOutputText();
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
			compilable.compileClass();
		} catch (IOException e) {
			System.out.println("Error trying to compile code: Invalid file destination.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		if (noExceptionThrown) {
			loadClass("Modified class compiled and loaded.\n---");
		}
		
	}
	
	public static void restoreButtonPressed() {
		window.clearOutputText();
		boolean noExceptionThrown = true;

		compilable.setInputCode(PLACEHOLDER);
		try {
			compilable.updateSourceClass(compilable.getLastInputCode());
		} catch (FileNotFoundException e) {
			System.out.println("Error trying to restore source code to defaults: " 
					+ "source code file not found.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error trying to restore source code to defaults: "
					+ "source code file incorrectly formatted.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (PlaceholderStringNotFoundException e) {
			System.out.println("Error trying to restore source code to defaults: " 
					+ "placeholder string not found in source code file.");
			noExceptionThrown = false;
			e.printStackTrace();
		} catch (UnretrievableInputCodeException e) {
			System.out.println("Error trying to restore source code to defaults: " 
					+ "cannot restore defaults before compiling at least once.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		try {
			compilable.compileClass();
		} catch (IOException e) {
			System.out.println("Error trying to compile code: Invalid file destination.");
			noExceptionThrown = false;
			e.printStackTrace();
		}

		if (noExceptionThrown) {
			loadClass("Defaults restored.\n---");
		}
		
	}
	
	public static void loadClass(String successMessage) {
		Class<?> compileThisClass = null;
		try {
			compileThisClass = compilable.loadClass("file:H:\\git\\Compile-On-The-Fly-UI\\RunInputCodeUI\\bin\\pkgRunInputCodeUI\\CompileThisClass.class");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Method m = null;
		try {
			m = compileThisClass.getMethod("main", String[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			System.out.println(successMessage);
			
			String[] params = null;
				m.invoke(null, (Object) params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}

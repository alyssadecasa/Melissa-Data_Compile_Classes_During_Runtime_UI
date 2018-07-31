/**
 * Project RunInputCodeUI
 * CompilableClass.java
 * 
 * Class that allows for users to load a class at runtime without 
 * needing to restart the current program
 */

package pkgRunInputCodeUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

public class CompilableClass {

	private String inputCodeString;
	private String lastInputCodeString;
	private File root;
	private File javaFile;
	private File classFile;

	// Constructors
	// ------------------------------------------------------------------------------------------
	public CompilableClass() {
		root = new File(System.getProperty("user.dir"));
		javaFile = new File(root.toString() + "\\src\\pkgRunInputCodeUI\\CompileThisClass.java");
		classFile = new File(root.toString() + "\\bin\\pkgRunInputCodeUI\\CompileThisClass.class");
	}

	public CompilableClass(File Root, File sourceJavaFile, File destinationClassFile) {
		root = Root;
		javaFile = sourceJavaFile;
		classFile = destinationClassFile;
	}

	// Public Methods
	// ------------------------------------------------------------------------------------------
	
	/**
	 * Sets the string containing code to be inserted into the source java file
	 * 
	 * @param InputCodeString
	 *            string of java code inputted by the user
	 */
	public void setInputCode(String inputCodeString) {
		if (inputCodeString != null) {
			lastInputCodeString = this.inputCodeString;
		}
		this.inputCodeString = inputCodeString;
	}

	public String getLastInputCode() {
		return lastInputCodeString;
	}
	/**
	 * Sets the Source Class from a string containing the FULL pathname
	 * 
	 * @param pathname
	 *            FULL pathname of desired file
	 */
	public void setSourceClass(String pathname) {
		javaFile = new File(pathname);
	}

	/**
	 * Inserts the user's code block into the java source file, replacing the
	 * placeholder string in the source file
	 * 
	 * @param placeholder
	 *            char set located in the java source file to be replaced by the
	 *            user's code block
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws PlaceholderStringNotFoundException
	 */
	public void updateSourceClass(String placeholder)
			throws FileNotFoundException, IOException, PlaceholderStringNotFoundException {
		String fileAsString = "";

		fileAsString = fileToString(new File(root.toString() + "\\src\\pkgRunInputCodeUI\\CompileThisClass.java"));

		// Ensure placeholder exists
		if (!fileAsString.contains(placeholder)) {
			throw new PlaceholderStringNotFoundException("Placeholder string not found in source file.");
		}

		fileAsString = fileAsString.replace(placeholder, inputCodeString);
		stringToFile(fileAsString, javaFile);
	}

	/**
	 * Compiles the java source file
	 * 
	 * @throws IOException
	 */
	public void compileSourceClass() throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, javaFile.getPath());

		// Change destination to bin folder
		File sourceClass = new File(javaFile.toString().replace(".java", ".class"));
		try {
			if (classFile.exists()) {
				moveFile(sourceClass.toPath(), classFile.toPath());
			}
		} catch (IOException e) {
			throw new IOException(
					"Unable to move newly compiled class file from source " 
							+ "folder to specified class location.");
		}
	}

	public Class<?> loadSourceClass() throws ClassNotFoundException, MalformedURLException {
		File bin = classFile.getParentFile().getParentFile();
		URLClassLoader classLoader = null;
		DynamicURLClassLoader dynamicLoader = null;
		Class<?> cls = null;
		String className = classFile.getParentFile().getName() + "."
				+ classFile.getName().substring(0, classFile.getName().length() - 6);
		classLoader = URLClassLoader.newInstance(new URL[] { bin.toURI().toURL() });
		dynamicLoader = new DynamicURLClassLoader(new URL[] { bin.toURI().toURL() });
		cls = Class.forName(className, true, dynamicLoader);

		return cls;
	}

	// Private Helper Methods
	// ------------------------------------------------------------------------------------------
	
	/**
	 * Converts a File to a String
	 * 
	 * @param sourceFile
	 *            File to be converted into a String
	 * @return fileAsString inputted File formatted as a String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String fileToString(File sourceFile) throws FileNotFoundException, IOException {
		String fileAsString = "";

		// Verify source file exists
		if (!sourceFile.exists()) {
			throw new FileNotFoundException();
		}

		fileAsString = FileUtils.readFileToString(sourceFile, "UTF-8");

		return fileAsString;
	}

	/**
	 * Converts a String to a File
	 * 
	 * @param sourceString
	 *            String to be converted into a File
	 * @param destinationFile
	 *            name of newly created File
	 * @throws IOException
	 */
	private void stringToFile(String sourceString, File destinationFile) throws IOException {
		FileUtils.writeStringToFile(destinationFile, sourceString, "UTF-8");
	}

	/**
	 * Moves a file from the source location to the destination location
	 * @param sourceFile source path
	 * @param destinationFile destination path
	 * @throws IOException
	 */
	private void moveFile(Path sourceFile, Path destinationFile) throws IOException {
		Files.move(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);

	}

}
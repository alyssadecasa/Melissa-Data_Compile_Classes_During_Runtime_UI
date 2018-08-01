/**
 * Project RunInputCodeUI
 * CompilableClass.java
 * 
 * Class that allows for users to load a class at runtime without 
 * needing to restart the current program
 * 
 * @author alyssa
 */

package pkgRunInputCodeUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

	/**
	 * Returns the last code string inputted by the user
	 * 
	 * @return last code string inputted by the user
	 */
	public String getLastInputCode() throws UnretrievableInputCodeException {
		if (lastInputCodeString == null) {
			throw new UnretrievableInputCodeException("No input code previously entered");
		}
		return lastInputCodeString;
	}

	/**
	 * Sets the Java Source Class file from a string containing the FULL pathname
	 * 
	 * @param pathname
	 *            FULL pathname of desired file
	 */
	public void setSourceFile(String pathname) {
		javaFile = new File(pathname);
	}

	/**
	 * Sets the Destination Class file from a string containing the FULL pathname
	 * 
	 * @param pathname
	 *            FULL pathname of desired file
	 */
	public void setDestinationFile(String pathname) {
		classFile = new File(pathname);
	}

	/**
	 * Sets the root directory from a string containing the FULL pathname
	 * 
	 * @param pathname
	 *            FULL pathname of desired directory
	 */
	public void setRootDirectory(String pathname) {
		root = new File(pathname);
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

		fileAsString = fileToString(javaFile);

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
	public void compileClass() throws IOException {
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
					"Unable to move newly compiled class file from source folder to specified class location.");
		}
	}

	/**
	 * Loads the class file at the given url
	 * 
	 * @param url
	 *            file location of class to be loaded
	 * @return Class object of specified loaded class
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	public Class<?> loadClass(String url) throws ClassNotFoundException, MalformedURLException {
		File bin = classFile.getParentFile().getParentFile();

		if (!url.startsWith("file:" + bin)) {
			throw new MalformedURLException("class location url is not contained in parent destination file:"
					+ "\n\turl: " + url + "\n\tparent destination file: " + bin);
		}

		DynamicURLClassLoader dynamicLoader = null;
		Class<?> cls = null;
		String className = classFile.getParentFile().getName() + "."
				+ classFile.getName().substring(0, classFile.getName().length() - 6);

		dynamicLoader = new DynamicURLClassLoader(new URL[] { bin.toURI().toURL() });
		dynamicLoader.setDynamicClassUrl(url);
		dynamicLoader.setDynamicClassName(className);

		cls = Class.forName(className, true, dynamicLoader);

		return cls;
	}

	// Private Helper Methods
	// ------------------------------------------------------------------------------------------
	private String fileToString(File sourceFile) throws FileNotFoundException, IOException {
		String fileAsString = "";

		// Verify source file exists
		if (!sourceFile.exists()) {
			throw new FileNotFoundException();
		}

		fileAsString = FileUtils.readFileToString(sourceFile, "UTF-8");

		return fileAsString;
	}

	private void stringToFile(String sourceString, File destinationFile) throws IOException {
		FileUtils.writeStringToFile(destinationFile, sourceString, "UTF-8");
	}

	private void moveFile(Path sourceFile, Path destinationFile) throws IOException {
		Files.move(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
	}

}

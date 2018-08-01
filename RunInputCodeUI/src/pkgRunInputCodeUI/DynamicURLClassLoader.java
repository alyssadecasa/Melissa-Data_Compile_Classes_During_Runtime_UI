/**
 * Project Compile-On-The-Fly-UI
 * DynamicURLClassLoader.java
 * 
 * Subclass of URLClassLoader that will dynamically load and reload classes
 * 
 * @author alyssa
 */

package pkgRunInputCodeUI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;

public class DynamicURLClassLoader extends URLClassLoader {

	private String url;
	private String dynamicClassName;

	public DynamicURLClassLoader(URL[] urls) {
		super(urls);
	}

	public DynamicURLClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public DynamicURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (dynamicClassName == null) {
			throw new ClassNotFoundException("Dynamic Class Name not specified using setDynamicClassName() function.");
		}

		if (!dynamicClassName.equals(name))
			return super.loadClass(name);

		try {
			URL myUrl = new URL(url);
			URLConnection connection = myUrl.openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();

			while (data != -1) {
				buffer.write(data);
				data = input.read();
			}

			input.close();

			byte[] classData = buffer.toByteArray();

			return defineClass(name, classData, 0, classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Sets the url that points to the location of a class to be dynamically loaded
	 * 
	 * @param url
	 *            file location of class
	 */
	public void setDynamicClassUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the name of the class to be dynamically loaded
	 * 
	 * @param name
	 *            in the format of package.class
	 */
	public void setDynamicClassName(String name) {
		this.dynamicClassName = name;
	}
}

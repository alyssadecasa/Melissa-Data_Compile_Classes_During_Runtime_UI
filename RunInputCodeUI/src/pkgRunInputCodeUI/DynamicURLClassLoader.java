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
	public Class loadClass(String name) throws ClassNotFoundException {
		if (!"pkgRunInputCodeUI.CompileThisClass".equals(name))
			return super.loadClass(name);

		try {
			String url = "file:H:\\git\\CompileOnTheFlyUI\\RunInputCodeUI\\bin\\pkgRunInputCodeUI\\CompileThisClass.class"; // "file:C:\\Users\\alyssa\\eclipse-workspace\\RunInputCodeUI\\bin\\pkgRunInputCodeUI\\CompileThisClass.class";
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

			return defineClass("pkgRunInputCodeUI.CompileThisClass", classData, 0, classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}

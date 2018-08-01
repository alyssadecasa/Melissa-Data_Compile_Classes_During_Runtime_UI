/**
 * Project Compile-On-The-Fly-UI
 * TextAreaOutputStream.java
 * 
 * Subclass of OutputStream that writes to Main.window's output text
 * 
 * @author alyssa
 */
package pkgRunInputCodeUI;

import java.io.IOException;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {

	public TextAreaOutputStream() {
	}

	@Override
	public void write(int b) throws IOException {
		Main.window.appendOutputText(String.valueOf((char) b));
	}

}

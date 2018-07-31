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
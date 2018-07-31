/**
 * Project RunInputCodeUI
 * GuiWindow.java
 * 
 * Class for handling all GUI related elements
 */

package pkgRunInputCodeUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.SystemColor;

public class GuiWindow {

	private JFrame frame;
	private JTextArea inputText;
	private JTextArea outputText;
	private String outputString;
	private boolean buttonPressed;

	
	public GuiWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		setWindowContents();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void setWindowContents() {
		JPanel northStaticPanel = new JPanel();
		frame.getContentPane().add(northStaticPanel, BorderLayout.NORTH);

		JLabel northStaticTitle = new JLabel("Title");
		northStaticPanel.add(northStaticTitle);

		JPanel southStaticPanel = new JPanel();
		frame.getContentPane().add(southStaticPanel, BorderLayout.SOUTH);
		
		JButton southRestoreDefaultButton = new JButton("Restore to Default");
		addButtonListener(southRestoreDefaultButton);
		southStaticPanel.add(southRestoreDefaultButton);

		JButton southSubmitButton = new JButton("Submit and Compile");
		addButtonListener(southSubmitButton);
		southStaticPanel.add(southSubmitButton);
		

		JPanel centerDynamicPanel = new JPanel();
		frame.getContentPane().add(centerDynamicPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerDynamicPanel = new GridBagLayout();
		gbl_centerDynamicPanel.columnWidths = new int[] { 30, 0, 30, 0, 0, 0 };
		gbl_centerDynamicPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_centerDynamicPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_centerDynamicPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		centerDynamicPanel.setLayout(gbl_centerDynamicPanel);

		JLabel inputLabel = new JLabel("Input");
		inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_inputLabel = new GridBagConstraints();
		gbc_inputLabel.insets = new Insets(0, 0, 5, 5);
		gbc_inputLabel.gridx = 1;
		gbc_inputLabel.gridy = 0;
		centerDynamicPanel.add(inputLabel, gbc_inputLabel);

		JLabel outputLabel = new JLabel("Output");
		outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints OutputLabel = new GridBagConstraints();
		OutputLabel.insets = new Insets(0, 0, 5, 5);
		OutputLabel.gridx = 3;
		OutputLabel.gridy = 0;
		centerDynamicPanel.add(outputLabel, OutputLabel);

		inputText = new JTextArea();
		inputText.setWrapStyleWord(true);
		inputText.setLineWrap(true);
		JScrollPane inputScroll = new JScrollPane (inputText);
		inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_inputText = new GridBagConstraints();
		gbc_inputText.insets = new Insets(0, 0, 5, 5);
		gbc_inputText.fill = GridBagConstraints.BOTH;
		gbc_inputText.gridx = 1;
		gbc_inputText.gridy = 1;
		centerDynamicPanel.add(inputScroll, gbc_inputText);

		outputText = new JTextArea();
		outputText.setBackground(SystemColor.inactiveCaptionBorder);
		outputText.setEditable(false);
		outputText.setWrapStyleWord(true);
		outputText.setLineWrap(true);
		JScrollPane outputScroll = new JScrollPane (outputText);
		outputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_outputText = new GridBagConstraints();
		gbc_outputText.insets = new Insets(0, 0, 5, 5);
		gbc_outputText.fill = GridBagConstraints.BOTH;
		gbc_outputText.gridx = 3;
		gbc_outputText.gridy = 1;
		centerDynamicPanel.add(outputScroll, gbc_outputText);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 4;
		gbc_horizontalStrut.gridy = 1;
		centerDynamicPanel.add(horizontalStrut, gbc_horizontalStrut);

	}
	
	private void addButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton) e.getSource()).getText().equals("Submit and Compile")) {
					Main.submitButtonPressed();
				} else {
					Main.restoreButtonPressed();
				}
			}
		});
	}
	
	public boolean isOpen() {
		if (frame.isShowing()) {
			return true;
		}
		return false;
	}
	
	public void setButtonPressed(boolean bool) {
		buttonPressed = bool;
	}
	
	public boolean buttonIsPressed() {
		return buttonPressed;
	}
	
	public String getUserCode() {
		return inputText.getText();
	}
	
	public void clearOutputText() {
		outputText.setText("");
	}
	public void appendOutputText(String string) {
		outputText.append(string);
	}
}

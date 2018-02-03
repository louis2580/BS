import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JFrame;

// A GUI program is written as a subclass of Frame - the top-level container
// This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class GUI extends JFrame {

// private variables
 
	// Constructor to setup the GUI components
	public GUI() { 
	   
	   	//1. Create the frame.
	   	JFrame frame = new JFrame("FrameDemo");
		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//3. Create components and put them in the frame.
		
		
		
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		
		//4. Size the frame.
		frame.pack();
		//5. Show it.
		frame.setVisible(true);
	}

	// methods

	// The entry main() method
	public static void main(String[] args) {
		// Invoke the constructor (to setup the GUI) by allocating an instance
		new GUI();
	}
}
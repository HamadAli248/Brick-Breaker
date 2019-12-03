package com.hamad;


import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
    	//creates the frame for the game
	    JFrame obj = new JFrame();

	    // Calls the Gameplay file
		Gameplay gamePlay = new Gameplay();

		//sets the size
		obj.setBounds(10, 10,700,600);

		// sets the title
		obj.setTitle("Breakout Ball");
	    obj.setResizable(false);
	    obj.setVisible(true);
	    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //adds the code into th JFrame
	    obj.add(gamePlay);

    }
}

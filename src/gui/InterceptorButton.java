package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterceptorButton extends JButton{
	public InterceptorButton(final GameGUI gui){
		super("Buy Interceptor");
		this.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0) {
				gui.addInterceptorCommand();
			}
		});
	}
}

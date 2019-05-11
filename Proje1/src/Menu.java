import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
public class Menu extends JFrame {
	public void  initMenu()
	
	{
		JPanel optionsPane = new JPanel (new GridLayout(8,1));
		JPanel pane =null;
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton pv =new JButton("One Player");
		pv.addActionListener(new ActionAdapter());
		pv.setActionCommand("pv");
		pane = new JPanel (new GridLayout(1,1));
		pane.add(pv);
		optionsPane.add(pane);
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton pp =new JButton("Double Player");
		pp.addActionListener(new ActionAdapter());
		pp.setActionCommand("pp");
		pane = new JPanel (new GridLayout(1,1));
		pane.add(pp);
		optionsPane.add(pane);
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton on =new JButton("Online Play");
		on.addActionListener(new ActionAdapter());
		on.setActionCommand("on");
		pane = new JPanel (new GridLayout(1,1));
		pane.add(on);
		optionsPane.add(pane);
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton hp =new JButton("Help");
		hp.addActionListener(new ActionAdapter());
		hp.setActionCommand("hp");
		pane = new JPanel (new GridLayout(1,1));
		pane.add(hp);
		optionsPane.add(pane);
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cc =new JButton("CheatCodes");
		cc.addActionListener(new ActionAdapter());
		cc.setActionCommand("cc");
		pane = new JPanel (new GridLayout(1,1));
		pane.add(cc);
		optionsPane.add(pane);
		
		
		add(optionsPane);
		
		
	}
}
class ActionAdapter implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().contentEquals("hp")) {
			JOptionPane.showMessageDialog(null, "You need to collide cue ball with the other 2 ball to get point "
					+ "You can adjust the hit power by pushing to mouse"
					+ "0  button helps you to refresh the game"
					+ "9 helps you to pause the game "
					+ "Escape helps you to close the game"
					+ "Who ever get 50 points first will win");
		}
		else if(e.getActionCommand().contentEquals("cc")) {
			JOptionPane.showMessageDialog(null, "You can use up down left and right keys to move white ball ");
		
		}
		else if(e.getActionCommand().contentEquals("pp")) {
			
		}
		else if(e.getActionCommand().contentEquals("pv")) {
			
		}
		else if(e.getActionCommand().contentEquals("on")) {
			
		}
		
	}
}

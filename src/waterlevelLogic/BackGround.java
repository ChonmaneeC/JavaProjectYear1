package waterlevelLogic;

import java.awt.Color;
import javax.swing.JPanel;

public class BackGround extends JPanel
{
	Color bgColor;
	
	public BackGround()
	{
		bgColor = Color.LIGHT_GRAY;
		setBackground(bgColor);
	}
}
package lab.faceonfire.examplebutton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import lab.faceonfire.examplebutton.FruitMessage.Fruit;

import org.jivesoftware.spark.util.SwingWorker;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.component.RolloverButton;
import org.jivesoftware.spark.ui.ChatRoom;
import org.jivesoftware.spark.ui.ChatRoomClosingListener;
import org.jivesoftware.spark.util.GraphicUtils;

/**
 * This Class adds the Example toolbar button to the ChatWindow and implements the
 * ActionListener to react on buttonclicks
 */

public class ExampleButtonDecorator implements ActionListener, ChatRoomClosingListener {

    private RolloverButton _exampleButton;
    private ChatRoom _room;

    public ExampleButtonDecorator(ChatRoom room) {
	_room = room; 

	SwingWorker worker = new SwingWorker() {
	    public Object construct() {

		return true;
	    }

	    public void finished() {
  
		    ClassLoader cl = getClass().getClassLoader();

		    ImageIcon spellingIcon = new ImageIcon(
			    cl.getResource("text_ok.png"));
		    _exampleButton = new RolloverButton(spellingIcon);
		   
		    _exampleButton.setToolTipText(GraphicUtils			   
		    		.createToolTip("my apples button"));  //.createToolTip(SpellcheckerResource.getString("button.check.spelling")));
		    _exampleButton
			    .addActionListener(ExampleButtonDecorator.this);
		    _room.getEditorBar().add(_exampleButton);
                    
		   
		}
	 //   }

	};

	worker.start();

    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	JOptionPane.showMessageDialog(_room.getChatInputEditor(),
		           "pushed the button, will it show fruit next????");
    	Fruit fruit=RoomFruits.getFruit(_room);
    	JOptionPane.showMessageDialog(_room.getChatInputEditor(),
		           fruit.toString());
		//Fruit fruit=RoomFruits.getFruit(_room);
		if (fruit.equals(Fruit.Apple) ){ 
			RoomFruits.setFruit(_room, Fruit.Banana);
			 JOptionPane.showMessageDialog(_room.getChatInputEditor(),
			           "Messages will now be marked BANANA");
		}else {
			RoomFruits.setFruit(_room, Fruit.Apple);
			 JOptionPane.showMessageDialog(_room.getChatInputEditor(),
			           "Messages will now be marked APPLE");
		}
		
	    _room.getChatInputEditor().requestFocusInWindow();
	    
    }

    @Override
    public void closing() {

    }

    

}

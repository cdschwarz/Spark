package lab.faceonfire.examplebutton;

import org.jivesoftware.resource.SparkRes;
import org.jivesoftware.spark.plugin.Plugin;
import org.jivesoftware.spark.ChatManager;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.ui.ChatRoomButton;
import org.jivesoftware.spark.ui.ChatRoomListenerAdapter;
import org.jivesoftware.spark.ui.ChatRoom;
import org.jivesoftware.spark.ui.MessageFilter;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.NotFilter;
import org.jivesoftware.smack.filter.PacketExtensionFilter;
import org.jivesoftware.spark.component.RolloverButton;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.MessageEventManager;
import org.jivesoftware.smackx.MessageEventNotificationListener;
import org.jivesoftware.smackx.MessageEventRequestListener;
import org.jivesoftware.smack.packet.Message.Body;
import org.jivesoftware.smack.packet.PacketExtension;





import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import lab.faceonfire.examplebutton.FruitMessage.Fruit;


//import lab.faceonfire.examplebutton.FruitMessage.Fruit;



public class ExampleButtonPlugin implements Plugin{
	/**
	 * Implements the Spark Plugin framework to display the different possibilities using
	 * Spark.
	 */
	  private ExampleButtonRoomListener listener;

	    /**
	     * Called after Spark is loaded to initialize the new plugin.
	     */
	  public void initialize() {
		  System.out.println("Welcome To Spark");
		  // addChatRoomButton();
		  try {
			  listener = new ExampleButtonRoomListener();
			  SparkManager.getChatManager().addChatRoomListener(listener);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  this.addMessageFilter();
	  }
	    
	  public void addMessageFilter(){
			SparkManager.getChatManager().addMessageFilter(new MessageFilter() {
				@Override
				public void filterOutgoing(ChatRoom room, Message message) {
			
					Fruit roomFruit=RoomFruits.getFruit(room);
					
					if (roomFruit==null){
						RoomFruits.setFruit(room, Fruit.Banana);
						roomFruit=RoomFruits.getFruit(room);
					}
					message.addExtension(new FruitMessage(roomFruit));
					
					
//					String body = message.getBody();
//					for (Body oldBody : message.getBodies()) {
//						message.removeBody(oldBody);
//					}
//					message.setBody(body + ", baby!");
				}

				@Override
				public void filterIncoming(ChatRoom room, Message message) {
					String body = message.getBody();
					message.setBody(body + ", baby!");
				}
			});
	  }

	    /**
	     * Called when Spark is shutting down to allow for persistence of information
	     * or releasing of resources.
	     */
	    public void shutdown() {

	    }

	    /**
	     * Return true if the Spark can shutdown on users request.
	     * @return true if Spark can shutdown on users request.
	     */
	    public boolean canShutDown() {
	        return true;
	    }

	    /**
	    * Is called when a user explicitly asked to uninstall this plugin.
	    * The plugin owner is responsible to clean up any resources and
	    * remove any components install in Spark.
	    */
	    public void uninstall(){
	       // Remove all resources belonging to this plugin.
	    }


	    /**
	     * Adds a button to each Chat Room that is opened.
	  
	    private void addChatRoomButton() {
	        // Retrieve ChatManager from the SparkManager
	        ChatManager chatManager = SparkManager.getChatManager();
	      

	        // Create a new ChatRoomButton.
	        final ChatRoomButton toolbarButton = new ChatRoomButton("Push This");
	        
	       	
	        
	        // Add to a new ChatRoom when the ChatRoom opens.
	        chatManager.addChatRoomListener(new ChatRoomListenerAdapter() {
	            public void chatRoomOpened(ChatRoom room) {
	            	final RolloverButton button;
	    	        
	      	      	button= new RolloverButton(SparkRes.getImageIcon(SparkRes.BUZZ_IMAGE));
	      	        button.setToolTipText("my editor button");
	      	     // button.addActionListener(this);
	     
	      	        final JLabel dividerLabel = new JLabel(SparkRes.getImageIcon("DIVIDER_IMAGE"));
	      	        room.getEditorBar().add(dividerLabel);
	            	room.getEditorBar().add(button);
	             room.getToolBar().addChatRoomButton( toolbarButton);
	             toolbarButton.addActionListener(room);
	           
	            }

	            public void chatRoomLeft(ChatRoom room) {
	                room.getToolBar().removeChatRoomButton( toolbarButton);
	            
	            }
	        });
	          
	    }

 */

	}

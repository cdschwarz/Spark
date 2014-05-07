package lab.faceonfire.examplebutton;

import java.util.HashMap;



import lab.faceonfire.examplebutton.FruitMessage.Fruit;

import org.jivesoftware.spark.ui.ChatRoom;

public class RoomFruits {
	private static HashMap<ChatRoom,Fruit> fruits = new HashMap<ChatRoom,Fruit>();
	
	public static void setFruit(ChatRoom room, Fruit fruit) {
		fruits.put(room, fruit);
	}
	
	public static Fruit getFruit(ChatRoom room) {
		return fruits.get(room);
	}
	
}

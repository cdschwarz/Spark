package lab.faceonfire.examplebutton;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jivesoftware.smack.packet.PacketExtension;

@XmlRootElement(name = "fruit", namespace = "lab:faceonfire:fruit")
class FruitMessage implements PacketExtension {
	static final String NAMESPACE = "lab:faceonfire:fruit";
	static final String ELEMENT_NAME = "fruit";
	private Fruit fruit;
	enum Fruit {
		Apple, Banana, Cherry, Durian, Elderberry, Fig, Grape;
	}
	public FruitMessage(Fruit fruit) {
		this.setFruit(fruit);
	}

	@Override
	public String getElementName() {
		return ELEMENT_NAME;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String toXML() {
		String template = "<%s xmlns=\"%s\">%s</%s>";
		String result = String.format(template, ELEMENT_NAME, NAMESPACE, this
				.getFruit().toString(), ELEMENT_NAME);
		return result;
	}

	@XmlElement
	public Fruit getFruit() {
		return fruit;
	}

	private void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}

}

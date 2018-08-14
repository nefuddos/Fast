package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午3:22:01
*@problem
*@answer
*@action
*/
public class Ack {
	String title;
	List<String> paragraphs;
	public Ack() {
		this.paragraphs = new ArrayList<String>();
	}
	public Ack parse(Element element) {
		if(element == null) return null;
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("title"))
				this.title = ite.getText();
			else if(ite.getName().equals("p"))
				this.paragraphs.add(ite.getText());
		}
		return this;
	}
}

package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午3:25:21
*@problem
*@answer
*@action
*/
public class Note {
	String tile;
	List<String> paragraph;
	public Note() {
		this.paragraph = new ArrayList<String>();
	}
	public Note parse(Element element) {
		if(element == null) return null;
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("title"))
				this.tile = ite.getText();
			else if(ite.getName().equals("p"))
				this.paragraph.add(ite.getText());
		}
		return this;
	}
}

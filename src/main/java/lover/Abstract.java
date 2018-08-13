package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午4:57:31
*@problem
*@answer
*@action
*/
public class Abstract {
	List<String> paragraph;
	public Abstract() {
		this.paragraph = new ArrayList<String>();
	}
	public Abstract parse(Element element) {
		if(element == null) return null;
		List<Element> elements = element.element("p").elements();
		for(Element ite : elements) {
			if(ite.getName().equals("p")) {
				this.paragraph.add(ite.getText());
			}
		}
		return this;
	}

}

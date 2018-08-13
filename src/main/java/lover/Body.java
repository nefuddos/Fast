package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018��8��12��---����4:44:36
*@problem
*@answer
*@action
*/
public class Body {
	List<Sec> secs;
	public Body() {
		secs = new ArrayList<Sec>();
	}
	public Body parse(Element element) {
		if(element == null) return null;
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("sec"))
				this.secs.add(new Sec().parse(ite));
		}
		return this;
	}

}

package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午5:01:33
*@problem
*@answer
*@action
*/
public class Keywords {
	List<String> kwds;
	public Keywords() {
		kwds = new ArrayList<String>();
	}
	public Keywords parse(Element element) {
		if(element == null) return null;
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("kwd"))
				this.kwds.add(ite.getText());
		}
		return this;
	}
	public String toString() {
		String str = "";
		for(String i : kwds) {
			str += i;
		}
		return str;
	}
}

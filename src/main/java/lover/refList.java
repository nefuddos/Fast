package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午3:57:30
*@problem
*@answer
*@action
*/
public class refList {
	String tile;
	List<Ref> refs;
	public refList() {
		refs = new ArrayList<Ref>();
	}
	public refList parse(Element element) {
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("title"))
				this.tile = ite.getText();
			else if(ite.getName().equals("ref"))
				this.refs.add(new Ref().parse(ite));
		}
		return this;
	}
}

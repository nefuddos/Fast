package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午8:52:36
*@problem
*@answer
*@action
*/
public class PersonGroup {
	public class Name {
		String surname;
		String givenNames;
		public Name parse(Element ite) {
			if(ite == null) return null;
			this.surname = ite.element("surname").getText();
			this.givenNames = ite.element("given-names").getText();
			return this;
		}
	}
	List<Name> nameArr;
	public PersonGroup() {
		this.nameArr = new ArrayList<Name>();	
	}
	public PersonGroup parse(Element element) {
		if(element == null) return null;
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("name"))
				this.nameArr.add(new Name().parse(ite));
		}
		return this;
	}
}

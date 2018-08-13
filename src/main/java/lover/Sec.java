package lover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午5:06:52
*@problem
*@answer
*@action
*/
public class Sec {
	String title;
	List<String> paragrapth;
	List<Sec> childSecList;
	public Sec() {
		this.paragrapth = new ArrayList<String>();
		this.childSecList = new ArrayList<Sec>();
	}
	public Sec parse(Element element) {
		List<Element> meta = element.elements();
		for(Iterator<Element> iterator = meta.iterator(); iterator.hasNext();) {
			Element ite = iterator.next();
			if(ite.getName().equals("title"))
				this.title = ite.getText();
			else if(ite.getName().equals("p"))
				this.paragrapth.add(ite.getText());
			else if(ite.getName().equals("sec"))
				this.childSecList.add(new Sec().parse(ite));
		}
		return this;
	}

}

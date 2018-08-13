package lover;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午3:24:31
*@problem
*@answer
*@action
*/
public class Ref {
	String label;
	Citation citation;
	public Ref parse(Element element) {
		this.label = element.element("label").getText();
		this.citation =  new Citation().parse(element.element("citation"));
		return this;
	}
}

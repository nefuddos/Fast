package lover;

import java.util.List;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午3:20:56
*@problem 这个类包含ack,ref,notes
*@answer
*@action
*/
public class Back {
	Ack ack;
	Note notes;
	refList obj;
	public Back parse(Element element) {
		this.ack = new Ack().parse(element.element("ack"));
		this.notes = new Note().parse(element.element("notes"));
		this.obj = new refList().parse(element.element("ref-list"));
		return this;
	}
}

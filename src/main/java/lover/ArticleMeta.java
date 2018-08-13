package lover;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018��8��12��---����4:41:27
*@problem
*@answer
*@action
*/
public class ArticleMeta {
	String articleTitle;
	Abstract abs;
	Keywords kwdObj;
	public ArticleMeta parse(Element element) {
		this.articleTitle = element.element("title-group").element("article-title").getText();
		this.abs = new Abstract().parse(element.element("abstract"));
		this.kwdObj = new Keywords().parse(element.element("kwd-group"));
		return this;
	}

}

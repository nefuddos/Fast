package lover;

import org.dom4j.Element;

/**
*@author    created by Ren Jingui
*@date  2018��8��12��---����4:43:12
*@problem
*@answer
*@action
*/
public class Front {
	JournalMeta journalMeta;
	ArticleMeta articleMeta;
	public Front parse(Element element) {
		this.journalMeta = new JournalMeta().parse(element.element("journal-meta"));
		this.articleMeta = new ArticleMeta().parse(element.element("article-meta"));
		return this;
	}
}

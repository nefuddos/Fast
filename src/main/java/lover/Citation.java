package lover;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午4:05:36
*@problem
*@answer
*@action
*/
public class Citation {
	String collab;
	String source;
	String publisherName;
	String publisherLoc;
	int year;
	String volume;
	String fpage;
	String lpage;
	String pubId;
	String articleTile;
	PersonGroup pg;
	public void Citation() {
//		pg = new ArrayList<PersonGroup>();
	}
	public Citation parse(Element element) {
		if(element == null) return null;
		this.collab = Objects.isNull(element.element("collab"))?null:element.element("collab").getText();
		this.source = Objects.isNull(element.element("source"))?null:element.element("source").getText();
		this.publisherName = Objects.isNull(element.element("publisher-name"))?null:element.element("publisher-name").getText();
		this.publisherLoc = Objects.isNull(element.element("publisher-loc"))?null:element.element("publisher-loc").getText();
		this.year = Objects.isNull(element.element("year"))?0:Integer.parseInt(element.element("year").getText());
		this.volume = Objects.isNull(element.element("volume"))?null:element.element("volume").getText();
		this.fpage = Objects.isNull(element.element("fpage"))?null:element.element("fpage").getText();
		this.lpage = Objects.isNull(element.element("lpage"))?null:element.element("lpage").getText();
		this.pubId = Objects.isNull(element.element("pub-id"))?null:element.element("pub-id").getText();
		this.articleTile = Objects.isNull(element.element("article-title"))?null:element.element("article-title").getText();
		this.pg = Objects.isNull(element.element("person-group"))?null:new PersonGroup().parse(element.element("person-group"));//element.element("person-group").getText();
		return this; 
	}
}

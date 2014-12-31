package jp.leopanda.bloggerIndexPager.client;

import jp.leopanda.bloggerIndexPager.shared.BloggerPost;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IndexItem extends VerticalPanel {
	private String title;
	private String url;
	private String imgUrl;
	public IndexItem(BloggerPost bloggerPost) {
		title = bloggerPost.getTitle();
		url = bloggerPost.getUrl();
		imgUrl = bloggerPost.getImgUrl();
		String html = "<a href=\"" + url + "\"  target=\"_top\">"
					+ "<img class=\"indexImage\"src=\"" + imgUrl + "\"/><br/>"
					+ "<div class=\"indexTitle\">" + title +"</div>" 
					+ "</a>";
		this.add(new HTML(html));
		this.addStyleName("indexItem");
	}
}

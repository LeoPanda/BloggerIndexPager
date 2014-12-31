package jp.leopanda.bloggerIndexPager.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;



@SuppressWarnings("serial")
public class BloggerPost implements Serializable{
		private String title;
		private String published;
		private String url;
		private String content;
		private String imgUrl;
		private String isDraft = "NO";
		
		public BloggerPost() {
		}
		
		public String getTitle() {
			return title;
		}
		public String getPublished() {
			return published;
		}
		public String getUrl() {
			return url;
		}
		public String getContent() {
			return content;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public String getIsDraft() {
			return isDraft;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setPublished(String published) {
			this.published = published;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public void setIsDraft(String isDraft) {
			this.isDraft = isDraft;
		}
		

}

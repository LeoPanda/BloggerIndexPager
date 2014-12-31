package jp.leopanda.bloggerIndexPager.server;

import java.util.Map;
import java.util.logging.Logger;

import jp.leopanda.bloggerIndexPager.client.HostGateService;
import jp.leopanda.bloggerIndexPager.server.UrlService.ContentType;
import jp.leopanda.bloggerIndexPager.server.UrlService.Result;
import jp.leopanda.bloggerIndexPager.shared.BloggerPost;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class HostGateServiceImpl extends RemoteServiceServlet implements HostGateService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String BLOG_ID = "8188837734572962617";
	private static final Logger log = Logger.getLogger(HostGateServiceImpl.class.getName());

	public BloggerPost[] getBloggerPosts(String query) {
		String urlStr = "https://www.blogger.com/feeds/" + BLOG_ID 
						+ "/posts/default" + query;
		
		UrlService urlService = new UrlService();		
		Map<Result,String> results = 
			urlService.fetchGet(urlStr, 
						urlService.setHeader(ContentType.HTML));
		if(Integer.valueOf(results.get(Result.RETCODE)) != 200){
			log.info("HostGateServiceImpl Error: code=" + Result.RETCODE + ":"+ results.get(Result.BODY));
		}
		
		Document doc = Jsoup.parse(results.get(Result.BODY));
		Elements entrys = doc.getElementsByTag("entry");
		BloggerPost[] bloggerPosts = new BloggerPost[entrys.size()];
		int i = 0;
		for(Element entry : entrys){
			BloggerPost bloggerPost = setBloggerPost(entry);
			if(bloggerPost.getIsDraft() == "NO"){
				bloggerPosts[i] = bloggerPost;
				i++;
			}
		}

		return bloggerPosts;
	}
	/**
	 * 投稿オブジェクトへデータをセットする
	 * @param entry
	 * @return
	 */
	private BloggerPost setBloggerPost(Element entry) {
		BloggerPost bloggerPost = new BloggerPost();
		for (Element article : entry.children()){
			if( article.tagName().equals("title")){bloggerPost.setTitle(article.text());}
			else if( article.tagName().equals("published")){bloggerPost.setPublished(article.text());}
			else if( article.tagName().equals("app:draft")){bloggerPost.setIsDraft(article.text());}	
			else if( article.tagName().equals("content")){bloggerPost.setImgUrl(searchImgUrl(article.text()));}
			else if( article.tagName().equals("link")){		
				if(article.attr("rel").equals("alternate")){
						bloggerPost.setUrl(article.attr("href"));}
				}
			}
		return bloggerPost;
	}
	/**
	 * 投稿の本文から最初の画像URLを取り出す
	 * @param bloggerPost
	 * @param source
	 * @return
	 */
	private String searchImgUrl(String source){
		String ret = getBeforeCheck(
						getAfterCheck(
								getAfterCheck(source,"<img")
						,"src=\"")
					,"\"")
					.replaceFirst("/s\\d+/","/s120/");
		return ret;
	}
	//*文字列sourceの中から文字列checkより後ろを取り出す
	private String getAfterCheck(String source,String check){
		int index = source.indexOf(check);
		String ret = index > 0 ? source.substring(index + check.length()) : "";
		return ret;
	}
	//*文字列sourceの中から文字列checkより前を取り出す
	private String getBeforeCheck(String source,String check){
		int index = source.indexOf(check);
		String ret = index > 0 ? source.substring(0,index) : "";
		return ret;
	}
}

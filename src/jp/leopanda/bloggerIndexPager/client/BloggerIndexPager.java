package jp.leopanda.bloggerIndexPager.client;

import jp.leopanda.bloggerIndexPager.shared.BloggerPost;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BloggerIndexPager implements EntryPoint {
	/**
	 * Bloggerブログ投稿の目次を作成する。
	 * BloggerAPI V2の検索パラメータをそのまま使って表示する投稿を選び出せる。
	 * パラメータ:Blogger API V２パラメータ
	 * maxWidth:横方向の最大表示件数
	 */
	private  final HostGateServiceAsync hgs = 
									GWT.create(HostGateService.class); 
	private VerticalPanel 	outerPanel = new VerticalPanel();
	private int maxNumOfVertical = 5; //横方向へ表示するアイテムの最大数
	/**
	 * This is the entry point method.
	 */	
	@Override
	public void onModuleLoad() {
		String locationParm = Window.Location.getQueryString();
		String maxWidth= Window.Location.getParameter("maxWidth");
		if(maxWidth!=null){
			maxNumOfVertical = Integer.parseInt(maxWidth);
		}
		hgs.getBloggerPosts(locationParm, new getBloggerPostsCallBack());
		outerPanel.addStyleName("indexPanel");
		RootPanel.get().add(outerPanel);
	}
	/**
	 * callBack class for getbloggerPosts
	 * @author LeoPanda
	 *
	 */
	private class getBloggerPostsCallBack implements
		AsyncCallback<BloggerPost[]> {
		
		@Override
		public void onFailure(Throwable caught) {
			if(caught instanceof HostGateException){
				Window.alert("ブログ記事取得に失敗しました。HTTP StatisCode="+
					((HostGateException)caught).getStatus());
			}else{
				Window.alert("ブログ記事取得時にRPCエラー:" + caught.toString());
			}					
		}
		@Override
		public void onSuccess(BloggerPost[] result) {
			int i = 1;
			HorizontalPanel innerFrame = new HorizontalPanel();
			for (BloggerPost bloggerPost : result) {
				VerticalPanel postPage = new IndexItem(bloggerPost);
				innerFrame.add(postPage);
				if(i >= maxNumOfVertical){
					outerPanel.add(innerFrame);
					innerFrame = new HorizontalPanel();
					i=0;
				}
				i++;
			}
			if(i!=1){
				outerPanel.add(innerFrame);	
			}
		}
	}
}

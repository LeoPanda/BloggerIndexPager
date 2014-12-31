package jp.leopanda.bloggerIndexPager.client;


import java.util.ArrayList;
import java.util.List;




import jp.leopanda.bloggerIndexPager.shared.BloggerPost;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HostGateServiceAsync {

	void getBloggerPosts(String query, AsyncCallback<BloggerPost[]> callback);
}

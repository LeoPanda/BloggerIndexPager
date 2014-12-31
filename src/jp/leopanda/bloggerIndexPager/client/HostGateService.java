package jp.leopanda.bloggerIndexPager.client;


import java.util.ArrayList;
import java.util.List;

import jp.leopanda.bloggerIndexPager.shared.BloggerPost;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



@RemoteServiceRelativePath("HostGateService")
public interface HostGateService  extends RemoteService {	
	BloggerPost[] getBloggerPosts(String query) throws HostGateException;
	}

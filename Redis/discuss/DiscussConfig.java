package com.altratek.altraserver.extensions.discuss;

public class DiscussConfig {

	public static final String[] titleInfoName = {"tId", "title", "authorId", "lookId", "substance", "createTime"};
	
	public static final String[] commentaryInfoName = {"cId", "tId", "authorId", "substance", "createTime"};
	
	public static final String[] replyInfoName = {"rId", "tId", "cId", "authorId", "replyComment", "replyUserId", "rCreateTime"};

	public static final int HOT = 0;
	public static final int NEW = 1;
	public static final Integer[] TYPE = {HOT, NEW}; // 0-最热,1-最新
	
	public static final int TITLE_PRAISE_TYPE = 0;
	public static final int COMMENTARY_PRAISE_TYPE = 1;
	public static final Integer[] PRAISE_TYPE = {TITLE_PRAISE_TYPE, COMMENTARY_PRAISE_TYPE};
}

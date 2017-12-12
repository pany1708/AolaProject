package com.altratek.altraserver.extensions.discuss;

public class DiscussRedisKeyConfig {
	
	private static final String ADMINISTRATORS = "DISCUSS_%s_ADMINISTRATORS";

	// 帖子标题信息
	private static final String TITLE_INFO = "DISCUSS_%s_TITLE_INFO"; // 帖子信息key
	private static final String TITLE_CREATE_TIME = "DISCUSS_%s_TITLE_CRETE_TIME"; // 帖子创建时间
	private static final String TITLE_PRAISE = "DISCUSS_%s_TITLE_PRAISE"; // 帖子点赞数
	private static final String TITLE_PRAISE_INFO = "DISCUSS_%s_TITLE_PRAISE_%s"; // 玩家已点赞的帖子
	private static final String TITLE_PRAISE_USERS = "DISCUSS_%s_%s_PRAISE_USERS"; // 记录帖子被哪些玩家点赞
	private static final String TITLE_ID = "DISCUSS_%s_TITLE_ID"; // 帖子id
	private static final String TITLE_STICK = "DISCUSS_%s_TITLE_STICK"; // 置顶id 
	
	// 评论信息
	private static final String COMMENTARY_INFO = "DISCUSS_%s_%s_COMMENTARY_INFO"; // 评论信息
	private static final String COMMENTARY_CREATE_TIME = "DISCUSS_%s_%s_COMMENTARY_CRETE_TIME"; // 评论创建时间
	private static final String COMMENTARY_PRAISE = "DISCUSS_%s_%s_COMMENTARY_PRAISE"; // 评论点赞数
	private static final String COMMENTARY_PRAISE_INFO = "DISCUSS_%s_%s_COMMENTARY_PRAISE_%s"; //　玩家已点赞的评论
	private static final String COMMENTARY_PRAISE_USERS = "DISCUSS_%s_%s_PRAISE_USERS"; // 记录帖子评论被哪些玩家点赞
	private static final String COMMENTARY_PRAISE_USER_INFO = "DISCUSS_%s_%s_COMMENTARY_PRAISE_%s"; // 记录该评论被哪些玩家点赞
	private static final String COMMENTARY_ID = "DISCUSS_%s_%s_COMMENTARY_ID"; // 评论id
	private static final String COMMENTARY_FOCUS = "DISCUSS_%s_%s_FOCUS"; // 集中讨论
	
	
	// 楼中楼信息
	private static final String REPLY_INFO = "DISCUSS_%s_%s_%s_COMMENTARY_INFO";
	
	//**************************华丽的分割线***************************

	public static String getAdministratorsKey(String actName) {
		return String.format(ADMINISTRATORS, actName);
	}
	
	//**************************华丽的分割线***************************

	public static String getTitleInfoKey(String actName) {
		return String.format(TITLE_INFO, actName);
	}
	
	public static String getTitleCreateTimeKey(String actName) {
		return String.format(TITLE_CREATE_TIME, actName);
	}
	
	public static String getTitlePraiseOrderKey(String actName) {
		return String.format(TITLE_PRAISE, actName);
	}
	
	public static String getTitlePraiseInfoKey(int userId, String actName) {
		return String.format(TITLE_PRAISE_INFO, actName, userId);
	}
	
	public static String getTitlePraiseUsersKey(String actName, String titleId) {
		return String.format(TITLE_PRAISE_USERS, actName, titleId);
	}
	
	public static String getTitleIdKey(String actName) {
		return String.format(TITLE_ID, actName);
	}
	
	public static String getTitleStick(String actName) {
		return String.format(TITLE_STICK, actName);
	}
	
	//**************************华丽的分割线***************************
	
	public static String getCommentaryInfoKey(String actName, String titleId) {
		return String.format(COMMENTARY_INFO, actName, titleId);
	}
	
	public static String getCommentaryCreateTimeKey(String actName, String titleId) {
		return String.format(COMMENTARY_CREATE_TIME, actName, titleId);
	}
	
	public static String getCommentaryPraiseOrderKey(String actName, String titleId) {
		return String.format(COMMENTARY_PRAISE, actName, titleId);
	}
	
	public static String getCommentaryPraiseInfoKey(int userId, String actName, String titleId) {
		return String.format(COMMENTARY_PRAISE_INFO, actName, titleId, userId);
	}
	
	public static String getCommentaryPraiseUsersKey(String actName, String titleId) {
		return String.format(COMMENTARY_PRAISE_USERS, actName, titleId);
	}
	
	public static String getCommentaryPraiseUserInfoKey(String actName, String titleId, String substanceId) {
		return String.format(COMMENTARY_PRAISE_USER_INFO, actName, titleId, substanceId);
	}
	
	public static String getCommentaryIdKey(String actName, String titleId) {
		return String.format(COMMENTARY_ID, actName, titleId);
	}
	
	public static String getCommentaryFocus(String actName, String titleId) {
		return String.format(COMMENTARY_FOCUS, actName, titleId);
	}
	
	//**************************华丽的分割线***************************
	
	public static String getReplyInfoKey(String actName, String titleId, String substanceId) {
		return String.format(REPLY_INFO, actName, titleId, substanceId);
	}

}

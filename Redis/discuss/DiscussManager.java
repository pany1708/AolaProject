package com.altratek.altraserver.extensions.discuss;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.altratek.altraserver.extensions.holiday.data.CD;
import com.altratek.altraserver.extensions.holiday.data.McData;
import com.altratek.altraserver.extensions.holiday.data.RedisData;
import com.altratek.altraserver.extensions.material.data.MaterialResult;
import com.altratek.altraserver.lib.ActionscriptObject;
import com.aola.common.db.UserQuery;

/**
 * 通用讨论区
 * @author fengwenkun
 *
 */
public class DiscussManager {

	private static final DiscussManager INSTANCE = new DiscussManager();
	
	public static DiscussManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_discussP",方便前端
	 * 进入讨论区(显示热门贴和最新帖子)
	 * @in "pageId" - int : 最新的页数id,从0开始
	 * @param "sendTitleTimes" - McData : 每天发帖mcdata
	 * @param "sendCommenTimes" - McData : 每天评论加楼中楼mcdata
	 * @param "nEachPageNum" - int : 最新每页多少条
	 * @param "hEnd" - int : 最热多少条
	 * @param "userId" - int : 
	 * @param "actName" - String :
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject :
	 * @out "sids" - Array<String> : 置顶帖id
	 * @out "tih" - Array<ASObj> : 热门贴(有置顶帖的)  - {tId, title, lookId, substance, createTime, dd, nn, praise, rcn}
	 * 							  tId - String - 帖子id
	 * 							  title - String - 标题
	 * 							  lookId - String - 表情id
	 * 							  substance - String - 内容
	 * 							  createTime - String - 时间戳
	 * 							  dd - String - 多多号
	 * 							  nn - String - 昵称
	 * 							  praise - int - 点赞数
	 * 							  rcn - int - 回帖数
	 * @out "tin" - Array<ASObj> : 最新贴  - {tId, title, lookId, substance, createTime, dd, nn, praise, rcn}
	 * 							  tId - String - 帖子id
	 * 							  title - String - 标题
	 * 							  lookId - String - 表情id
	 * 							  substance - String - 内容
	 * 							  createTime - String - 时间戳
	 * 							  dd - String - 多多号
	 * 							  nn - String - 昵称
	 * 							  praise - int - 点赞数
	 * 							  rcn - int - 回帖数
	 * @out "tps" - Array<Integer> : 已点赞的帖子id
	 * @out "isAd" - boolean : 
	 * @out "ttn" - int : 帖子总数
	 * @out "stt" - int : 每天发帖次数
	 * @out "sct" - int : 每天评论加回复次数
	 */
	public void fillPanelInfo(McData sendTitleTimes, McData sendCommenTimes, int nEachPageNum, int hEnd, int userId, String actName, ActionscriptObject ao, ActionscriptObject res) {
		Integer pageId = ao.getInt("pageId");
		if (pageId == null || pageId < 0) {
			return ;
		}
		res.putArray("sids", DiscussRedisDao.getStickIds(actName).toArray());
		res.putASObjectList("tih", DiscussRedisDao.getTitle(actName, DiscussConfig.HOT, 0, hEnd));
		res.putASObjectList("tin", DiscussRedisDao.getTitle(actName, DiscussConfig.NEW, pageId * nEachPageNum, (pageId + 1) * nEachPageNum));
		res.putArray("tps", DiscussRedisDao.getHavePraiseIds4Title(userId, actName).toArray());
		res.putBool("isAd", RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getAdministratorsKey(actName), String.valueOf(userId)));
		res.putInt("ttn", DiscussRedisDao.getTitleTotalNum(actName));
		int[] data = McData.getMultiDataArray(userId, sendTitleTimes, sendCommenTimes);
		res.putInt("stt", data[0]);
		res.putInt("sct", data[1]);
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_view",方便前端
	 * 看帖
	 * @in "tabIndex" - int : 0-热门评论(集中评论)
	 * @in "titleId" - int : 帖子id,1~
	 * @in "pageId" - int : 第几页,从0开始
	 * @param "actName" - String : 
	 * @param "eachPageNum" - int : 每页多少条
	 * @param "totalHotSize" - int : 热门最多多少条
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @out "tInfo" - ASObj : 帖子信息 - tId, title, lookId, substance, createTime, dd, nn, praise, rc
	 * 							  tId - String - 帖子id
	 * 							  title - String - 标题
	 * 							  lookId - String - 表情id
	 * 							  substance - String - 内容
	 * 							  createTime - String - 时间戳
	 * 							  dd - String - 多多号
	 * 							  nn - String - 昵称
	 * 							  praise - int - 点赞数
	 * 							  rc - int - 回帖数
	 * @out "detail" - Array<ASObj> : 评论 - {cId, substance, createTime, praise, cdd, cnn, rc}
	 * 								   cId - String - 评论id
	 * 								   substance - String 评论内容
	 * 								   createTime - String -时间戳
	 * 								   praise - int -点赞数
	 * 								   cdd - String - 发评论人的多多号
	 * 								   cnn - String - 发评论人的昵称
	 * 								   rc - Array<ASObj> - 楼中楼 - {rId, replyComment, rCreateTime, rdd, rnn, brdd, brnn}
	 * 										rId - String - 回复id
	 * 										replyComment - String - 回复内容
	 * 										rCreateTime - String - 时间戳
	 * 										rdd - String - 回复人的多多号
	 * 										rnn - String - 回复人的昵称
	 * 										brdd - String - 被回复人的多多号,不为空,则是玩家回复玩家,否则是评论楼中楼
	 * 										brnn - String - 被回复人的昵称
	 * @out "ctn" - int : 按类型评论总数
	 * @out "cps" - Array<Integer> : 已点赞的评论id
	 */
	public int fillViewInfo(String actName, int eachPageNum, int totalHotSize, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer type = ao.getInt("tabIndex");
		Integer tiId = ao.getInt("titleId");
		Integer pageId = ao.getInt("pageId");
		List<Integer> typeList = Arrays.asList(DiscussConfig.TYPE);
		if (type == null || typeList.indexOf(type) == -1 || tiId == null || pageId == null || tiId <= 0 || pageId < 0) {
			return -1;
		}
		String titleId = String.valueOf(tiId);
		ActionscriptObject titleAppoint = DiscussRedisDao.getTitleAppoint(actName, titleId);
		if (titleAppoint == null) {
			return -12;
		}
		res.putASObj("tInfo", titleAppoint);
		res.putASObjectList("detail", DiscussRedisDao.getCommentary(actName, titleId, type, pageId * eachPageNum, (pageId + 1) * eachPageNum - 1));
		res.putInt("ctn", DiscussRedisDao.getCommentaryTotalNumByType(actName, titleId, type, totalHotSize));
		res.putArray("cps", DiscussRedisDao.getHavePraiseIds4Commentary(userId, actName, titleId).toArray());
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_title",方便前端
	 * 发帖
	 * @in "title" - int : 标题,1~
	 * @in "substance" - String : 内容
	 * @in "lookId" - int : 表情id
	 * @param "sendTitleTimes" - McData : 对应活动每天已发贴次数
	 * @param "maxSendTitleTimesEachDay" - int : 对应活动每天最大发帖次数
	 * @param "maxTitleLength" - int : 标题长度
	 * @param "maxSubLength" - int : 内容长度
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "cd" - CD : 发帖cd
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @out "id" - int : titleId
	 * @return "r" - int - 结果:1-成功 , -2:标题过长,-3:内容过长,-4:今天发帖数量已达到上限 
	 */
	public int title(McData sendTitleTimes, int maxSendTitleTimesEachDay, int maxTitleLength, int maxSubLength, String actName, int userId, CD cd, ActionscriptObject ao, ActionscriptObject res) {
		String title = ao.getString("title");
		String substance = ao.getString("substance");
		Integer lookId = ao.getInt("lookId");
		if (title == null || title.isEmpty() || substance == null || substance.isEmpty() || lookId == null || lookId < 0) {
			return -1;
		}
		try {
			if (title.getBytes("UTF-8").length > maxTitleLength) {
				return -2;
			}
			if (substance.getBytes("UTF-8").length > maxSubLength) {
				return -3;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (sendTitleTimes.get(userId) > maxSendTitleTimesEachDay) {
			return -4;
		}
		if (cd.get(userId) > 0) {
			return -5;
		}
		int titleId = DiscussRedisDao.recordNewTitleInfo(actName, userId, title, substance, lookId);
		res.putInt("id", titleId);
		sendTitleTimes.incr(userId, 1);
		cd.set(userId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_commentary",方便前端
	 * 评论
	 * @in "titleId" - int : 帖子id,1~
	 * @in "substance" - String : 内容
	 * @param "sendCommenTimes" - McData : 对应活动每天已评论和楼中楼次数
	 * @param "maxSendCommenTimesEachDay" - int : 对应活动每天评论和楼中楼最大次数
	 * @param "maxSubLength" - int : 内容长度
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "cd" - CD : 发帖cd
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @out "id" - int : 成功后的评论id
	 * @return "r" - int : 结果:1-成功 , -2:内容过长,-3:今天回帖数量已达到上限,-4帖子已达到最大楼层数
	 */
	public int commentary(McData sendCommenTimes, int maxSendCommenTimesEachDay, int maxSubLength, String actName, int userId, CD cd, ActionscriptObject ao, ActionscriptObject res) {
		Integer tiId = ao.getInt("titleId");
		String substance = ao.getString("substance");
		if (tiId == null || substance == null || substance.isEmpty()) {
			return -1;
		}
		try {
			if (substance.getBytes("UTF-8").length > maxSubLength) {
				return -2;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (sendCommenTimes.get(userId) > maxSendCommenTimesEachDay) {
			return -3;
		}
		if (cd.get(userId) > 0) {
			return -4;
		}
		String titleId = String.valueOf(tiId);
		int cId = DiscussRedisDao.recordNewCommentary(actName, titleId, userId, substance);
		res.putInt("id", cId);
		sendCommenTimes.incr(userId, 1);
		cd.set(userId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_reply",方便前端
	 * 楼中楼
	 * @in "titleId" - int : 帖子id,1~
	 * @in "substanceId" - int : 评论id
	 * @in "replyComment" - String : 内容
	 * @in "replyUserId" - String : 如果是玩家回复玩家,则传这个多多号
	 * @param "sendCommenTimes" - McData : 对应活动每天已评论和楼中楼次数
	 * @param "maxSendCommenTimesEachDay" - int : 对应活动每天评论和楼中楼最大次数
	 * @param "maxReplyLength" - int : 楼中楼内容长度
	 * @param "maxReplyFloor" - int : 楼中楼最多多少楼,-1表示不限制的话 
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "cd" - CD : 发帖cd
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @out "r" - int : 结果:1-成功
	 * @out "id" - int : 成功后的楼中楼id
	 * @out "ri" - ASOBj : cId, substance, createTime, praise, cdd, cnn, rc
	 * 					 cId - String - 评论id
	 * 					 substance - String 评论内容
	 * 					 createTime - String -时间戳
	 * 					 praise - int -点赞数
	 * 					 cdd - String - 发评论人的多多号
	 * 					 cnn - String - 发评论人的昵称
	 * 					 rc - Array<ASObj> - 楼中楼 - {rId, replyComment, rCreateTime, rdd, rnn, brdd, brnn}
	 * 										rId - String - 回复id
	 * 										replyComment - String - 回复内容
	 * 										rCreateTime - String - 时间戳
	 * 										rdd - String - 回复人的多多号
	 * 										rnn - String - 回复人的昵称
	 * 										brdd - String - 被回复人的多多号,不为空,则是玩家回复玩家,否则是评论楼中楼
	 * 										brnn - String - 被回复人的昵称
	 */
	public int reply(McData sendCommenTimes, int maxSendCommenTimesEachDay, int maxReplyLength, int maxReplyFloor, String actName,int userId, CD cd, ActionscriptObject ao, ActionscriptObject res) {
		Integer tiId = ao.getInt("titleId");
		Integer subId = ao.getInt("substanceId");
		String replyComment = ao.getString("replyComment");
		String replyUserId = ao.getString("replyDD");
		if (tiId == null || subId == null || replyComment.isEmpty()) {
			return -1;
		}
		try {
			if (replyComment.getBytes("UTF-8").length > maxReplyLength) {
				return -2;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (sendCommenTimes.get(userId) > maxSendCommenTimesEachDay) {
			return -3;
		}
		String titleId = String.valueOf(tiId);
		String substanceId = String.valueOf(subId);
		if (maxReplyFloor != -1 && DiscussRedisDao.getReplyTotalNum(actName, titleId, substanceId) > maxReplyFloor) {
			return -4;
		}
		if (cd.get(userId) > 0) {
			return -5;
		}
		int name2Id = UserQuery.name2Id(replyUserId);
		if (name2Id == 0) {
			replyUserId = "#";
		} else {
			replyUserId = "#" + name2Id;
		}
		int replyId = DiscussRedisDao.recordNewReply(actName, titleId, substanceId, userId, replyComment, replyUserId);
		res.putInt("id", replyId);
		res.putASObj("ri", DiscussRedisDao.getCommentaryAppoint(actName, titleId, substanceId));
		sendCommenTimes.incr(userId, 1);
		cd.set(userId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_praiseT",方便前端
	 * 帖子点赞
	 * @in "titleId" - int : 帖子id,1~
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @return "r" - int : 结果:1-成功
	 */
	public int titlePraise(String actName, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer tId = ao.getInt("titleId");
		if (tId == null || tId <= 0) {
			return -1;
		}
		String titleId = String.valueOf(tId);
		if (DiscussRedisDao.isPraise4Title(userId, actName, titleId)) {
			return -2;
		}
		DiscussRedisDao.recordPraise4Title(userId, actName, titleId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_praiseC",方便前端
	 * 评论点赞
	 * @in "titleId" - int : 帖子id,1~
	 * @in "substanceId" - int : 评论id
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @return "r" - int : 结果:1-成功
	 */
	public int commenPraise(String actName, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer tId = ao.getInt("titleId");
		Integer subId = ao.getInt("substanceId");
		if (tId == null || tId <= 0 || subId == null || subId < 0) {
			return -1;
		}
		String titleId = String.valueOf(tId);
		String substanceId = String.valueOf(subId);
		if (DiscussRedisDao.isPraise4Commentary(userId, actName, titleId, substanceId)) {
			return -2;
		}
		DiscussRedisDao.recordPraise4Commentary(userId, actName, titleId, substanceId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_deleteT",方便前端
	 * 管理员删帖
	 * @in "titleId" - int : 帖子id,1~
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @return "r" - int : 结果:1-成功
	 */
	public int deleteTitle(String actName, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer tId = ao.getInt("titleId");
		if (tId == null || tId <= 0) {
			return -1;
		}
		String titleId = String.valueOf(tId);
		if (!RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getAdministratorsKey(actName), String.valueOf(userId))) {
			return -2;
		}
		if (RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getTitleStick(actName), titleId)) { // 置顶帖不能删除
			return -3;
		}
		List<String> deleteKeys = new ArrayList<>();
		String commenKey = DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId); // 评论信息
		deleteKeys.add(commenKey);
		Set<String> replyKeys = RedisData.getRedisNode().hKeys(commenKey);
		for (String replyKey : replyKeys) { // 楼中楼信息
			deleteKeys.add(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, replyKey));
			deleteKeys.add(DiscussRedisKeyConfig.getCommentaryPraiseUserInfoKey(actName, titleId, replyKey));
		}
		deleteKeys.add(DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId)); // 评论时间排序
		deleteKeys.add(DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId)); // 评论点赞排序
		deleteKeys.add(DiscussRedisKeyConfig.getCommentaryIdKey(actName, titleId)); // 评论id
		String commenPraiseUsersKey = DiscussRedisKeyConfig.getCommentaryPraiseUsersKey(actName, titleId); // 点赞用户信息
		deleteKeys.add(commenPraiseUsersKey);
		Set<String> commenPraiseUserIdKeys = RedisData.getRedisNode().smembers(commenPraiseUsersKey);
		for (String cpuIdKey : commenPraiseUserIdKeys) { // 点赞用户
			DiscussRedisKeyConfig.getCommentaryPraiseInfoKey(Integer.valueOf(cpuIdKey), actName, titleId);
		}
		String titlePraiseUsersKey = DiscussRedisKeyConfig.getTitlePraiseUsersKey(actName, titleId); // 记录该帖子点赞玩家key
		deleteKeys.add(titlePraiseUsersKey);
		String[] titlePraiseUserIdKeys = RedisData.getRedisNode().smembers(titlePraiseUsersKey).toArray(new String[] {});
		for (int i = 0; i < titlePraiseUserIdKeys.length; i++) { // 
			titlePraiseUserIdKeys[i] = DiscussRedisKeyConfig.getTitlePraiseInfoKey(Integer.valueOf(titlePraiseUserIdKeys[i]), actName);
		}
		RedisData.getRedisNode().delMulti(deleteKeys.toArray(new String[] {})); // 删除redis key
		// 删除楼中楼信息
//		RedisData.getRedisNode().delMulti(replyKeys); // 删除楼中楼
		// 删除评论信息
//		RedisData.getRedisNode().del(DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId)); // 删除评论时间排序
//		RedisData.getRedisNode().del(DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId)); // 删除评论点赞排序
//		RedisData.getRedisNode().del(commenPraiseUsersKey); // 删除点赞用户信息
//		RedisData.getRedisNode().delMulti(commenPraiseUserIdKeys); // 删除点赞用户
//		RedisData.getRedisNode().del(DiscussRedisKeyConfig.getCommentaryIdKey(actName, titleId)); // 删除评论id
//		RedisData.getRedisNode().del(commenKey); // 删除评论信息
		// 删除帖子信息
		RedisData.getRedisNode().zrem(DiscussRedisKeyConfig.getTitleCreateTimeKey(actName), titleId); // 移除该帖子时间排序
		RedisData.getRedisNode().zrem(DiscussRedisKeyConfig.getTitlePraiseOrderKey(actName), titleId); // 移除该帖子点赞排序
		for (String tpuIdKey : titlePraiseUserIdKeys) { // 玩家点赞里面移除点赞过该帖子的帖子id
			RedisData.getRedisNode().srem(tpuIdKey, titleId);
		}
//		RedisData.getRedisNode().del(titlePraiseUsersKey); // 删除记录该帖子点赞玩家key
		RedisData.getRedisNode().hdel(DiscussRedisKeyConfig.getTitleInfoKey(actName), titleId); // 删除帖子信息
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_deleteC",方便前端
	 * 管理员删评论
	 * @in "titleId" - int : 帖子id,1~
	 * @in "substanceId" - int : 评论id
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @return "r" - int : 结果:1-成功
	 */
	public int deleteCommen(String actName, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer tId = ao.getInt("titleId");
		Integer subId = ao.getInt("substanceId");
		if (tId == null || tId <= 0 || subId == null || subId < 0) {
			return -1;
		}
		String titleId = String.valueOf(tId);
		String substanceId = String.valueOf(subId);
		if (!RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getAdministratorsKey(actName), String.valueOf(userId))) {
			return -2;
		}
		if (RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getTitleStick(actName), titleId)) { // 置顶帖不能删除
			return -3;
		}
		List<String> deleteKeys = new ArrayList<>();
		deleteKeys.add(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, substanceId));
		String commenPraiseUserInfoKey = DiscussRedisKeyConfig.getCommentaryPraiseUserInfoKey(actName, titleId, substanceId);
		deleteKeys.add(commenPraiseUserInfoKey);
		Set<String> smembers = RedisData.getRedisNode().smembers(commenPraiseUserInfoKey);
		for (String smember : smembers) {
			RedisData.getRedisNode().srem(DiscussRedisKeyConfig.getCommentaryPraiseInfoKey(Integer.valueOf(smember), actName, titleId), substanceId);
		}
		RedisData.getRedisNode().zrem(DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId), substanceId);
		RedisData.getRedisNode().zrem(DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId), substanceId);
		RedisData.getRedisNode().hdel(DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId), substanceId);
		return MaterialResult.SUCCESS;
	}
	
	/**
	 * 建议命令格式:CMDHEAD +　"_deleteR",方便前端
	 * 管理员删楼中楼
	 * @in "titleId" - int : 帖子id,1~
	 * @in "substanceId" - int : 评论id
	 * @in "replyId" - int : 楼中楼id
	 * @param "actName" - String :
	 * @param "userId" - int
	 * @param "ao" - ActionscriptObject : 
	 * @param "res" - ActionscriptObject : 
	 * @return "r" - int : 结果:1-成功
	 */
	public int deleteReply(String actName, int userId, ActionscriptObject ao, ActionscriptObject res) {
		Integer tId = ao.getInt("titleId");
		Integer subId = ao.getInt("substanceId");
		Integer rId = ao.getInt("replyId");
		if (tId == null || tId <= 0 || subId == null || subId < 0 || rId == null || rId < 0) {
			return -1;
		}
		if (!RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getAdministratorsKey(actName), String.valueOf(userId))) {
			return -2;
		}
		String titleId = String.valueOf(tId);
		String substanceId = String.valueOf(subId);
		String replyId = String.valueOf(rId);
		RedisData.getRedisNode().hdel(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, substanceId), replyId);
		return MaterialResult.SUCCESS;
	}
}

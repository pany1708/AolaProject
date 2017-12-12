package com.altratek.altraserver.extensions.discuss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.altratek.altraserver.extensions.holiday.data.RedisData;
import com.altratek.altraserver.extensions.util.StringUtil;
import com.altratek.altraserver.lib.ActionscriptObject;
import com.aola.common.db.NameParams;
import com.aola.common.db.UserQuery;

import redis.clients.jedis.Tuple;

public class DiscussRedisDao {

	//**************************帖子相关**************************
	
	/**
	 * 记录新帖子的数据
	 * @param actName
	 * @param userId
	 * @param title
	 * @param substance
	 * @param lookId
	 * @return
	 */
	public static int recordNewTitleInfo(String actName, int userId, String title, String substance, int lookId) {
		int titleId = (int) RedisData.getRedisNode().incCountBy(DiscussRedisKeyConfig.getTitleIdKey(actName), 1);
		long time = new Timestamp(System.currentTimeMillis()).getTime();
		Map<String, String> map = new HashMap<>();
		String tId = String.valueOf(titleId);
		String[] info = {tId, title, String.valueOf(userId), String.valueOf(lookId), substance, String.valueOf(time)};
		initMapInfo(DiscussConfig.titleInfoName, info, map);
		RedisData.getRedisNode().hset(DiscussRedisKeyConfig.getTitleInfoKey(actName), tId, mapToString(map));
		RedisData.getRedisNode().zadd(DiscussRedisKeyConfig.getTitleCreateTimeKey(actName), time, tId);
		return titleId;
	}
	
	/**
	 * 获取批量帖子信息
	 * @param actName
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 */
	public static ArrayList<ActionscriptObject> getTitle(String actName, int type, int start, int end) {
		List<Integer> typeList = Arrays.asList(DiscussConfig.TYPE);
		if (typeList.indexOf(type) == -1) {
			return new ArrayList<>();
		}
		boolean isHot = type == DiscussConfig.HOT;
		Set<String> stickIds;
		if (isHot) {
			stickIds = getStickIds(actName); // 置顶帖子id
			int stickSize = stickIds.size();
			if (stickSize >= end) {
				end = 0;
			} else {
				end -= stickSize;
			}
		} else {
			stickIds = new HashSet<>();
		}
		Set<Tuple> tuples = RedisData.getRedisNode().zrevrangeWithScores(isHot ? DiscussRedisKeyConfig.getTitlePraiseOrderKey(actName) : DiscussRedisKeyConfig.getTitleCreateTimeKey(actName), start, end);
		if (tuples.isEmpty() && stickIds.isEmpty()) {
			return new ArrayList<>();
		}
		Map<String, String> infoMap = RedisData.getRedisNode().hmget(DiscussRedisKeyConfig.getTitleInfoKey(actName), conditionStitching(tuples, stickIds));
		return titleInfo2AO(actName, infoMap);
	}
	
	public static Set<String> getStickIds(String actName) {
		return RedisData.getRedisNode().smembers(DiscussRedisKeyConfig.getTitleStick(actName));
	}
	
	public static ActionscriptObject getTitleAppoint(String actName, String titleId) {
		Map<String, String> infoMap = RedisData.getRedisNode().hmget(DiscussRedisKeyConfig.getTitleInfoKey(actName), titleId);
		ArrayList<ActionscriptObject> titleInfo2AO = titleInfo2AO(actName, infoMap);
		if (titleInfo2AO.isEmpty()) {
			return null;
		}
		return titleInfo2AO.get(0);
	}
	
	public static int getTitleTotalNum(String actName) {
		return RedisData.getRedisNode().hKeys(DiscussRedisKeyConfig.getTitleInfoKey(actName)).size();
	}
	
	//**************************评论相关**************************
	
	/**
	 * 记录新评论的数据
	 * @param actName
	 * @param titleId
	 * @param userId
	 * @param substance
	 * @return
	 */
	public static int recordNewCommentary(String actName, String titleId, int userId, String substance) {
		int commentaryId = (int) RedisData.getRedisNode().incCountBy(DiscussRedisKeyConfig.getCommentaryIdKey(actName, titleId), 1);
		long time = new Timestamp(System.currentTimeMillis()).getTime();
		Map<String, String> map = new HashMap<>();
		String cId = String.valueOf(commentaryId);
		String[] info = {cId, titleId, String.valueOf(userId), substance, String.valueOf(time)};
		initMapInfo(DiscussConfig.commentaryInfoName, info, map);
		RedisData.getRedisNode().hset(DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId), cId, mapToString(map));
		RedisData.getRedisNode().zadd(DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId), time, cId);
		RedisData.getRedisNode().zadd(DiscussRedisKeyConfig.getTitleCreateTimeKey(actName), time, titleId);
		return commentaryId;
	}
	
	/**
	 * 批量获取评论内容(包括楼中楼的内容)
	 * @param actName
	 * @param titleId
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 */
	public static ArrayList<ActionscriptObject> getCommentary(String actName, String titleId, int type, int start, int end) {
		List<Integer> typeList = Arrays.asList(DiscussConfig.TYPE);
		if (typeList.indexOf(type) == -1) {
			return new ArrayList<>();
		}
		boolean isHot = type == DiscussConfig.HOT;
		boolean isStick = isHot ? RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getTitleStick(actName), titleId) : false;
		// 置顶帖的是集中讨论区,普通贴是热门帖子区
		Set<String> focusIds = new HashSet<>();
		Set<Tuple> tuples = new HashSet<>();
		if (isHot && isStick) {
			focusIds = RedisData.getRedisNode().smembers(DiscussRedisKeyConfig.getCommentaryFocus(actName, titleId));
		} else {
			tuples = RedisData.getRedisNode().zrevrangeWithScores(isHot ? DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId) : DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId), start, end);
		}
		if (tuples.isEmpty() && focusIds.isEmpty()) {
			return new ArrayList<>();
		}
		Map<String, String> infoMap = RedisData.getRedisNode().hmget(DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId), conditionStitching(tuples, focusIds));
		return commentaryInfo2AO(actName, titleId, infoMap);
	}
	
	public static ActionscriptObject getCommentaryAppoint(String actName, String titleId, String substanceId) {
		Map<String, String> infoMap = RedisData.getRedisNode().hmget(DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId), substanceId);
		ArrayList<ActionscriptObject> commentaryInfo2AO = commentaryInfo2AO(actName, titleId, infoMap);
		if (commentaryInfo2AO.isEmpty()) {
			return null;
		}
		return commentaryInfo2AO.get(0);
	}
	
	/**
	 * 获取评论回复数量
	 * @param actName
	 * @param titleId
	 * @return
	 */
	public static int getCommentaryTotalNum(String actName, String titleId) {
		return (int) RedisData.getRedisNode().getHashMapSize(DiscussRedisKeyConfig.getCommentaryInfoKey(actName, titleId));
	}
	
	public static int getCommentaryTotalNumByType (String actName, String titleId, int type, int totalHotSize) {
		boolean isHot = type == DiscussConfig.HOT;
		int zcard = (int) RedisData.getRedisNode().zcard(isHot ? DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId) : DiscussRedisKeyConfig.getCommentaryCreateTimeKey(actName, titleId));
		if (isHot) {
			return Math.min(totalHotSize, zcard);
		}
		return zcard;
	}
	
	//**************************楼中楼相关**************************
	
	/**
	 * 记录新楼中楼的信息
	 * @param actName
	 * @param titleId
	 * @param substanceId
	 * @param userId
	 * @param replyComment
	 * @param replyUserId
	 * @return
	 */
	public static int recordNewReply(String actName, String titleId, String substanceId, int userId, String replyComment, String replyUserId) {
		int replyId = (int) RedisData.getRedisNode().incCountBy(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, substanceId), 1);
		long time = new Timestamp(System.currentTimeMillis()).getTime();
		Map<String, String> map = new HashMap<>();
		String rId = String.valueOf(replyId);
		String[] info = {rId, titleId, substanceId, String.valueOf(userId), replyComment, replyUserId, String.valueOf(time)};
		initMapInfo(DiscussConfig.replyInfoName, info, map);
		RedisData.getRedisNode().hset(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, substanceId), rId, mapToString(map));
		RedisData.getRedisNode().zadd(DiscussRedisKeyConfig.getTitleCreateTimeKey(actName), time, titleId);
		return replyId;
	}
	
	public static int getReplyTotalNum(String actName, String titleId, String substanceId) {
		return (int) RedisData.getRedisNode().getHashMapSize(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, substanceId));
	}
	
	//**************************点赞相关**************************
	
	/**
	 * 获取已点赞的帖子id
	 * @param actName
	 * @return
	 */
	public static Set<String> getHavePraiseIds4Title(int userId, String actName) {
		return RedisData.getRedisNode().smembers(DiscussRedisKeyConfig.getTitlePraiseInfoKey(userId, actName));
	}
	
	/**
	 * 获取已点赞的评论id
	 * @param actName
	 * @param titleId
	 * @return
	 */
	public static Set<String> getHavePraiseIds4Commentary(int userId, String actName, String titleId) {
		return RedisData.getRedisNode().smembers(DiscussRedisKeyConfig.getCommentaryPraiseInfoKey(userId, actName, titleId));
	}
	
	/**
	 * 判断该帖子是否已点赞
	 * @param actName
	 * @param titleId
	 * @return
	 */
	public static boolean isPraise4Title(int userId, String actName, String titleId) {
		return RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getTitlePraiseInfoKey(userId, actName), titleId);
	}
	
	/**
	 * 判断该评论是否已点赞
	 * @param actName
	 * @param titleId
	 * @param substanceId
	 * @return
	 */
	public static boolean isPraise4Commentary(int userId, String actName, String titleId, String substanceId) {
		return RedisData.getRedisNode().sismember(DiscussRedisKeyConfig.getCommentaryPraiseInfoKey(userId, actName, titleId), substanceId);
	}
	
	/**
	 * 记录点赞帖子
	 * @param actName
	 * @param titleId
	 */
	public static void recordPraise4Title(int userId, String actName, String titleId) {
		RedisData.getRedisNode().zincrby(DiscussRedisKeyConfig.getTitlePraiseOrderKey(actName), 1, titleId);
		RedisData.getRedisNode().sadd(DiscussRedisKeyConfig.getTitlePraiseInfoKey(userId, actName), titleId);
		RedisData.getRedisNode().sadd(DiscussRedisKeyConfig.getTitlePraiseUsersKey(actName, titleId), String.valueOf(userId));
	}
	
	/**
	 * 记录点赞评论
	 * @param actName
	 * @param titleId
	 * @param substanceId
	 */
	public static void recordPraise4Commentary(int userId, String actName, String titleId, String substanceId) {
		RedisData.getRedisNode().zincrby(DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId), 1, substanceId);
		RedisData.getRedisNode().sadd(DiscussRedisKeyConfig.getCommentaryPraiseInfoKey(userId, actName, titleId), substanceId);
		String uId = String.valueOf(userId);
		RedisData.getRedisNode().sadd(DiscussRedisKeyConfig.getCommentaryPraiseUsersKey(actName, titleId), uId);
		RedisData.getRedisNode().sadd(DiscussRedisKeyConfig.getCommentaryPraiseUserInfoKey(actName, titleId, substanceId), uId);
	}
	
	//**************************通用方法整合**************************
	
	private static void initMapInfo(String[] infoName, String[] info, Map<String, String> map) {
		if (infoName.length != info.length) {
			return ;
		}
		for (int i = 0; i < infoName.length; i++) {
			map.put(infoName[i], info[i]);
		}
	}
	
	private static String mapToString(Map<String, String> map) {
		String info = map.toString();
		info = info.substring(1, info.length() - 1);
		return info;
	}
	
	private static String[] conditionStitching(Set<Tuple> tuples, Set<String> stickIds) {
		Set<String> condition = new HashSet<>(tuples.size() + stickIds.size());
		for (Tuple tuple : tuples) {
			condition.add(tuple.getElement());
		}
		condition.addAll(stickIds);
		return condition.toArray(new String[] {});
	}
	
	private static Map<String, String> stringToMap(String value) {
		Map<String, String> map = new HashMap<>();
		if (value != null) {
			String[] vs = value.split(",");
			for (int i = 0; i < vs.length; i++) {
				String[] kv = vs[i].trim().split("=");
				if (kv.length != 2) {
					continue ;
				}
				map.put(kv[0], kv[1]);
			}
		}
		return map;
	}
	
	private static ArrayList<ActionscriptObject> titleInfo2AO(String actName, Map<String, String> infoMap) {
		ArrayList<ActionscriptObject> aos = new ArrayList<>(infoMap.size());
		for (Entry<String, String> info : infoMap.entrySet()) {
			Map<String, String> map = stringToMap(info.getValue());
			int authorId = Integer.valueOf(map.get("authorId"));
			NameParams nameParams = UserQuery.id2NameParams(authorId);
			ActionscriptObject ao = new ActionscriptObject();
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				if (key.equals("authorId")) {
					continue ;
				}
				ao.putString(key, entry.getValue());
			}
			// 多多号和昵称
			ao.putString("dd", nameParams == null ? "" : nameParams.getDuoduoId());
			ao.putString("nn", nameParams == null ? "" : nameParams.getNickname());
			String titleId = info.getKey();
			// 点赞数
			int praise = (int) RedisData.getRedisNode().zscore(DiscussRedisKeyConfig.getTitlePraiseOrderKey(actName), titleId);
			ao.putInt("praise", praise);
			// 评论回复数量
			int rcn = getCommentaryTotalNum(actName, titleId);
			ao.putInt("rcn", rcn);
			aos.add(ao);
		}
		return aos;
	}
	
	private static ArrayList<ActionscriptObject> commentaryInfo2AO(String actName, String titleId, Map<String, String> cInfoMap) {
		Set<Integer> uIds = new HashSet<>();
		resetIds(uIds, cInfoMap.values(), false);
		Map<String, Map<String, String>> redisInfoMap = new HashMap<>();
		for (String cId : cInfoMap.values()) {
			Map<String, String> rInfoMap = RedisData.getRedisNode().hgetAll(DiscussRedisKeyConfig.getReplyInfoKey(actName, titleId, cId));
			if (rInfoMap == null) {
				continue ;
			}
			resetIds(uIds, rInfoMap.values(), true);
			redisInfoMap.put(cId, rInfoMap);
		}
		Map<Integer, NameParams> id2NameParams = UserQuery.id2NameParams(new ArrayList<>(uIds));
		// 评论信息
		ArrayList<ActionscriptObject> aocs = new ArrayList<>(cInfoMap.size());
		for (Entry<String, String> cInfo : cInfoMap.entrySet()) {
			ActionscriptObject aoc = new ActionscriptObject();
			Map<String, String> map = stringToMap(cInfo.getValue());
			for (Entry<String, String> cDataMap : map.entrySet()) {
				String key = cDataMap.getKey();
				if (key.equals("authorId") || key.equals("tId")) {
					continue ;
				}
				aoc.putString(key, cDataMap.getValue());
			}
			int authorId = Integer.valueOf(map.get("authorId"));
			NameParams cNameParams = id2NameParams.get(authorId);
			aoc.putString("cdd", cNameParams == null ? "" : cNameParams.getDuoduoId());
			aoc.putString("cnn", cNameParams == null ? "" : cNameParams.getNickname());
			int praise = (int) RedisData.getRedisNode().zscore(DiscussRedisKeyConfig.getCommentaryPraiseOrderKey(actName, titleId), titleId);
			aoc.putInt("praise", praise);
			// 楼中楼的信息
			Map<String, String> rInfoMap = redisInfoMap.get(cInfo.getKey());
			ArrayList<ActionscriptObject> aors = new ArrayList<>(cInfoMap.size());
			if (rInfoMap != null) {
				ActionscriptObject aor = new ActionscriptObject();
				for (Entry<String, String> rDataMap : rInfoMap.entrySet()) {
					String key = rDataMap.getKey();
					if (key.equals("authorId") || key.equals("tId") || key.equals("cId") || key.equals("replyUserId")) {
						continue ;
					}
					aor.putString(key, rDataMap.getValue());
				}
				Integer rAuthorId = Integer.valueOf(rInfoMap.get("authorId"));
				NameParams rANameParams = id2NameParams.get(rAuthorId);
				aor.putString("rdd", rANameParams == null ? "" : rANameParams.getDuoduoId());
				aor.putString("rnn", rANameParams == null ? "" : rANameParams.getNickname());
				int replyUserId = Integer.valueOf(rInfoMap.get("replyUserId"));
				NameParams rNameParams = id2NameParams.get(replyUserId);
				aor.putString("brdd", rNameParams == null ? "" : rNameParams.getDuoduoId());
				aor.putString("brnn", rNameParams == null ? "" : rNameParams.getNickname());
				aors.add(aor);
			}
			aoc.putASObjectList("rc", aors);
			aocs.add(aoc);
		}
		return aocs;
	}
	
	private static void resetIds(Set<Integer> uIds, Collection<String> infos, boolean isReply) {
		for (String info : infos) {
			Map<String, String> map = stringToMap(info);
			String aId = map.get("authorId");
			if (StringUtil.isNullOrEmpty(aId)) {
				continue ;
			}
			uIds.add(Integer.valueOf(aId));
			if (isReply) {
				String replyUserId = map.get("replyUserId");
				replyUserId = replyUserId.substring(1, replyUserId.length());
				if (StringUtil.isNullOrEmpty(replyUserId)) {
					continue ;
				}
				uIds.add(Integer.valueOf(replyUserId));
			}
		}
	}
}

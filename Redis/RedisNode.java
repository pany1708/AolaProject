package com.aobi.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

import com.aobi.common.ApplicationLocal;
import com.aobi.common.config.ConfReaderFactory;
import com.aobi.common.utils.StringUtils;

public class RedisNode {

	private final static String SERVER_SP = ",";
	private final static String IP_PORT_SP = ":";

	private ShardedJedisPool pool;
	private String name;

	private static Map<String, RedisNode> nodes = new HashMap<String, RedisNode>();

	static {
		initRedisNode();
	}

	private static void initRedisNode() {
		try {
			Map<String, String> redisConfig = loadConfig();

			if (redisConfig == null) {
				return;
			}

			for (Entry<String, String> en : redisConfig.entrySet()) {
				create(en.getKey(), en.getValue());
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("init redis node error:", e);
		}
	}

	private static Map<String, String> loadConfig() {
		return ConfReaderFactory.getConfigReader().loadRedisConfig();
	}

	public static RedisNode create(String name, String serverConfigString) {
		RedisNode node = new RedisNode(name, serverConfigString);
		nodes.put(name, node);
		return node;
	}

	public static RedisNode getRedisNode(String name) {
		return nodes.get(name);
	}

	public static void reConfigAll() {
		Map<String, String> redisConfig = loadConfig();
		for (Entry<String, String> en : redisConfig.entrySet()) {
			reConfigWithAdd(en.getKey(), en.getValue());
		}
	}

	public static void reConfig(String name) {
		Map<String, String> redisConfig = loadConfig();
		String config = redisConfig.get(name);
		if (StringUtils.isEmpty(config)) {
			ApplicationLocal.instance().error(name + " redis node null config.");
			return;
		}

		reConfigWithAdd(name, config);
	}

	private static void reConfigWithAdd(String name, String config) {
		RedisNode node = nodes.get(name);
		if (node != null) {
			node.reload(config);
		} else {
			create(name, config);
		}
	}

	public static void destoryAll() {
		for (RedisNode node : nodes.values()) {
			node.destory();
		}
	}

	private RedisNode(String name, String serverConfigString) {
		this.name = name;
		try {
			this.init(serverConfigString);
		} catch (Exception e) {
			ApplicationLocal.instance().error("RedisNode init error:", e);
		}
	}

	private void init(String serverConfigString) {
		String[] servers = serverConfigString.split(SERVER_SP);

		JedisPoolConfig config = new JedisPoolConfig();
		
		config.setMaxTotal(40); //�ɰ汾��setMaxActive
		config.setMaxIdle(300000);
		config.setMaxWaitMillis(2000); //�ɰ汾��setMaxWait

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String server : servers) {
			String[] serverConfig = server.split(IP_PORT_SP);
			if (serverConfig == null || serverConfig.length != 2) {
				ApplicationLocal.instance().error("RedisNode Server's Format err!Must be like '10.17.1.182:6364'");
				return;
			}
			shards.add(new JedisShardInfo(serverConfig[0], Integer.valueOf(serverConfig[1])));
		}
		pool = new ShardedJedisPool(config, shards);
	}

	private void reload(String serverConfigString) {
		this.destory();
		this.init(serverConfigString);
	}

	private void destory() {
		if (pool != null) {
			pool.destroy();
			pool = null;
		}
	}

	public String getName() {
		return name;
	}

	public long del(String key) {
		long affectCount = 0;
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			affectCount = jedis.del(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis del err", e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return affectCount;
	}

	/**
	 * shard ֮�󣬶��key���ֲܷ��ڲ�ͬ�����ϣ� �ȸ����������ܣ�Ȼ�������del������socket����
	 */
	public long delMulti(String... keys) {
		long result = 0L;
		if (keys == null || keys.length == 0) {
			return result;
		}

		ShardedJedis shardedJedis = null;
		Map<Jedis, List<String>> serverKeys = new HashMap<Jedis, List<String>>();
		try {
			shardedJedis = pool.getResource();
			for (String key : keys) {
				Jedis jedis = shardedJedis.getShard(key);
				List<String> keyList = serverKeys.get(jedis);
				if (keyList == null) {
					keyList = new LinkedList<String>();
					serverKeys.put(jedis, keyList);
				}
				keyList.add(key);
			}

			for (Entry<Jedis, List<String>> entry : serverKeys.entrySet()) {
				Jedis jedis = entry.getKey();
				String[] keyArr = entry.getValue().toArray(new String[entry.getValue().size()]);
				if (keyArr == null || keyArr.length == 0) {
					continue;
				}
				long shardedRes = jedis.del(keyArr);
				result += shardedRes;
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis delMulti err", e);
		}finally{
			if(shardedJedis != null){
				shardedJedis.close();
			}
		}
		return result;
	}

	public boolean set(String key, String value) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
			result = true;
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis set err", e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return result;
	}

	public boolean setex(String key, String value, int sec) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			jedis.setex(key, sec, value);
			result = true;
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis setex err", e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return result;
	}

	/** �ж�ָ�� key �Ƿ���� */
	public boolean exists(String key) {
		ShardedJedis jedis = null;
		Boolean result = null;
		try {
			jedis = pool.getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis exists err", e);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		return null != result && result;
	}

	/**
	 * shard ֮�󣬶��key���ֲܷ��ڲ�ͬ�����ϣ� �ȸ����������ܣ�Ȼ�������mset������socket����
	 */
	public Map<String, Boolean> setMulti(Map<String, String> keyValueMap) {
		if (null == keyValueMap) {
			return null;
		}
		Map<String, Boolean> result = new HashMap<String, Boolean>(keyValueMap.size());
		if (0 == keyValueMap.size()) {
			return result;
		} else {
			for (String key : keyValueMap.keySet()) {
				result.put(key, false);
			}
		}

		ShardedJedis shardedJedis = null;
		Map<Jedis, Map<String, String>> serverKeyValue = new HashMap<Jedis, Map<String, String>>();
		try {
			shardedJedis = pool.getResource();
			for (Entry<String, String> entry : keyValueMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				Jedis jedis = shardedJedis.getShard(key);
				Map<String, String> keyValue = serverKeyValue.get(jedis);
				if (keyValue == null) {
					keyValue = new HashMap<String, String>();
					serverKeyValue.put(jedis, keyValue);
				}
				keyValue.put(key, value);
			}

			for (Entry<Jedis, Map<String, String>> entry : serverKeyValue.entrySet()) {
				Jedis jedis = entry.getKey();
				String[] keyValueArr = new String[2 * entry.getValue().size()];
				int i = 0;
				for (Entry<String, String> keyValue : entry.getValue().entrySet()) {
					keyValueArr[i] = keyValue.getKey();
					i++;
					keyValueArr[i] = keyValue.getValue();
					i++;
				}
				jedis.mset(keyValueArr);
				for (String key : entry.getValue().keySet()) {
					result.put(key, true);
				}
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis setMulti err", e);
		} finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}
		return result;
	}

	public String get(String key) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis get err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long getCount(String key) {
		long result = -1;
		try {
			String value = get(key);
			if (value != null) {
				result = Long.parseLong(value);
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis getcount err", e);
		}
		return result;
	}

	/**
	 * shard ֮�󣬶��key���ֲܷ��ڲ�ͬ�����ϣ� �ȸ����������ܣ�Ȼ�������mget������socket����
	 */
	public Map<String, String> getMulti(String... keys) {
		Map<String, String> result = new HashMap<String, String>();
		if (keys == null || keys.length == 0) {
			return result;
		}

		ShardedJedis shardedJedis = null;
		Map<Jedis, List<String>> serverKeys = new HashMap<Jedis, List<String>>();
		try {
			shardedJedis = pool.getResource();
			for (String key : keys) {
				Jedis jedis = shardedJedis.getShard(key);
				List<String> keyList = serverKeys.get(jedis);
				if (keyList == null) {
					keyList = new LinkedList<String>();
					serverKeys.put(jedis, keyList);
				}
				keyList.add(key);
			}

			for (Entry<Jedis, List<String>> entry : serverKeys.entrySet()) {
				Jedis jedis = entry.getKey();
				String[] keyList = entry.getValue().toArray(new String[] {});
				if (keyList == null || keyList.length == 0) {
					continue;
				}
				List<String> values = jedis.mget(keyList);
				for (int i = 0; i < keyList.length; i++) {
					result.put(keyList[i], values.get(i));
				}
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis getMulti err", e);
		}finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}

		return result;
	}

	/**
	 * 
	 * @param key
	 * @param inc
	 *            ���������ͱ�ʾ�ݼ�
	 * @return key�ĵ�ǰֵ
	 */
	public long incCountBy(String key, long inc) {
		long result = -1L;
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			result = jedis.incrBy(key, inc);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis inc err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * ��һ�� key �趨����ʱ��, ע��ĳЩ��������ı�����ʱ��, ����ժ�� redis-2.6-api (����)<br />
	 * <b>���һ������ֻ���޸�(alter)һ��������ʱ��� key ��ֵ��������һ���µ� key
	 * ֵ������(replace)���Ļ�����ô����ʱ�䲻�ᱻ�ı䡣</b>
	 */
	public boolean expire(String key, int seconds) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			jedis.expire(key, seconds);
			result = true;
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis expire err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long getHashMapSize(String key) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.hlen(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis getHashMapSize err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Map<String, String> getHashMap(String key, String... fields) {
		ShardedJedis jedis = null;
		Map<String, String> result = new HashMap<String, String>();
		try {
			jedis = pool.getResource();
			List<String> values = jedis.hmget(key, fields);
			if (values != null && !values.isEmpty()) {
				for (int i = 0; i < fields.length; i++) {
					result.put(fields[i], values.get(i));
				}
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis getHash err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public boolean setHashMap(String key, Map<String, String> values) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			jedis.hmset(key, values);
			result = true;
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis setHash err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Map<String, String> getHashMapAll(String key) {
		ShardedJedis jedis = null;
		Map<String, String> result = new HashMap<String, String>();
		try {
			jedis = pool.getResource();
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis get all hash err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public boolean existsInHashMap(String key, String field) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			result = jedis.hexists(key, field);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis exists in hash err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public List<String> lrange(String key, long start, long end) {
		ShardedJedis jedis = null;
		List<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.lrange(key, start, end);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis lrange err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long llen(String key) {
		ShardedJedis jedis = null;
		long result = 0;
		try {
			jedis = pool.getResource();
			result = jedis.llen(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis llen err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long rpush(String key, String value) {
		ShardedJedis jedis = null;
		long result = 0;
		try {
			jedis = pool.getResource();
			result = jedis.rpush(key, value);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis rpush err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long lrem(String key, long count, String value) {
		ShardedJedis jedis = null;
		long result = 0;
		try {
			jedis = pool.getResource();
			result = jedis.lrem(key, count, value);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis lrem err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public String ltrim(String key, long start, long end) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.ltrim(key, start, end);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis ltrim err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public String lindex(String key, long index) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.lindex(key, index);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis lindex err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * shard ֮�󣬶��key���ֲܷ��ڲ�ͬ�����ϣ� ��������������keys������socket����
	 */
	public Set<String> keys(String pattern) {
		Set<String> result = new HashSet<String>();
		if (pattern == null || "".equals(pattern)) {
			return result;
		}

		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			Collection<Jedis> jedisColl = shardedJedis.getAllShards();
			for (Jedis jedis : jedisColl) {
				Set<String> shardedRes = jedis.keys(pattern);
				if (null != shardedRes) {
					result.addAll(shardedRes);
				}
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis keys err", e);
		}finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}

		return result;
	}

	/**
	 * ��һ�� member Ԫ�ؼ��뵽���� key ���У��Ѿ������ڼ��ϵ� member Ԫ�ؽ������ԡ�<br/>
	 * ���� key �����ڣ��򴴽�һ��ֻ���� member Ԫ������Ա�ļ��ϡ�<br/>
	 * �� key ���Ǽ�������ʱ������һ������
	 * 
	 * @return ����ӵ������е���Ԫ�ص������������������Ե�Ԫ�ء�
	 */
	public long sadd(String key, String member) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.sadd(key, member);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis sadd err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * ����� member Ԫ�ؼ��뵽���� key ���У��Ѿ������ڼ��ϵ� member Ԫ�ؽ������ԡ�<br/>
	 * ���� key �����ڣ��򴴽�һ��ֻ���� member Ԫ������Ա�ļ��ϡ�<br/>
	 * �� key ���Ǽ�������ʱ������һ������
	 * 
	 * @return ����ӵ������е���Ԫ�ص������������������Ե�Ԫ�ء�
	 */
	public long sadd(String key, String[] members) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.sadd(key, members);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis sadd err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * �Ƴ����� key �е�һ������ member Ԫ�أ������ڵ� member Ԫ�ػᱻ���ԡ�<br/>
	 * �� key ���Ǽ������ͣ�����һ������
	 * 
	 * @return ���ɹ��Ƴ���Ԫ�ص������������������Ե�Ԫ�ء�
	 */
	public long srem(String key, String member) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.srem(key, member);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis srem err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * ���ؼ��� key �е����г�Ա��<br/>
	 * �����ڵ� key ����Ϊ�ռ��ϡ�
	 * 
	 * @return �����е����г�Ա��
	 */
	public Set<String> smembers(String key) {
		ShardedJedis jedis = null;
		Set<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.smembers(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis smembers err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * �ж� member Ԫ���Ƿ񼯺� key �ĳ�Ա��
	 * 
	 * @return ��� member Ԫ���Ǽ��ϵĳ�Ա������ 1 ��<br/>
	 *         ��� member Ԫ�ز��Ǽ��ϵĳ�Ա���� key �����ڣ����� 0 ��
	 */
	public boolean sismember(String key, String member) {
		ShardedJedis jedis = null;
		boolean result = false;
		try {
			jedis = pool.getResource();
			result = jedis.sismember(key, member);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis sismember err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * ���ؼ��� key �Ļ���(������Ԫ�ص�����)��
	 * 
	 * @return ���ϵĻ�����<br/>
	 *         �� key ������ʱ������ 0 ��
	 */
	public long scard(String key) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.scard(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis scard err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** ����Ƴ������ؼ��� key ��һ��Ԫ�� */
	public String spop(String key) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.spop(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis spop err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** ������ؼ��� key ��һ��Ԫ�� */
	public String srandmember(String key) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.srandmember(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis srandmember err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public List<String> srandmember(String key, int count){
		ShardedJedis jedis = null;
		List<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.srandmember(key, count);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis srandmember err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	

	/**
	 * Redis���ݽṹ�ḻ���ӿ��ڶ࣬����Ͳ�һһ��װ�� ��pool������ȥ������ע���Լ�������Դ�Ļ��� ����¶�ˣ��Ժ�Ҫ�µķ������Լ���
	 * 
	 * <pre>
	 * ShardedJedis jedis = null;
	 * 	try {
	 * 		jedis = pool.getResource();
	 * 	    ...
	 * 		pool.returnResource(jedis);
	 * 	} catch (Exception e) {
	 * 		pool.returnBrokenResource(jedis);
	 * 	}
	 * </pre>
	 */
//	 public ShardedJedisPool getPool() {
//		 return pool;
//	 }

	/**
	 * ʹ��pipeline��ʽ��������ȡ�ַ���
	 * 
	 * @param keys
	 *            ��Ҫ������ȡ��key
	 */
	public List<String> batchGetString(final Collection<String> keys) {
		ShardedJedis jedis = null;
		ArrayList<String> resultList = new ArrayList<String>();
		try {
			jedis = pool.getResource();
			ShardedJedisPipeline pipeline = jedis.pipelined();
			List<Response<String>> responses = new ArrayList<Response<String>>(keys.size());
			for (String k : keys) {
				responses.add(pipeline.get(k));
			}
			pipeline.sync();
			for (Response<String> response : responses) {
				resultList.add(response.get());
			}
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis batchGetString err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return resultList;
	}

	/**
	 * ʹ��pipeline��ʽ������д�뵥��list
	 * 
	 * @param listName
	 *            ��Ҫд���list��Ӧ��key
	 * @param values
	 *            ��Ҫ����list��Ԫ��������ʱֻ֧��String
	 */
	public void pushMultiToList(final String listName, final Collection<String> values) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			ShardedJedisPipeline pipeline = jedis.pipelined();
			for (String v : values) {
				pipeline.rpush(listName, v);
			}
			pipeline.sync();
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis batchPutList err", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * ������ key ��ȡ������ start �� end ֮��ĳ�Ա<br/>
	 * start �� end �� 0 ��ʼ, ������ʾ����<br/>
	 * ����� start �� end �� 2 ����Ա (������)
	 */
	public Set<String> zrange(final String key, final int start, final int end) {
		ShardedJedis jedis = null;
		Set<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrange(key, start, end);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrange err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �� {@link #zrange(String, int, int)} ������ͬ, Ψһ��ͬ���Ƿ��ص����򼯵�Ԫ�ذ�����Ա����� */
	public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {
		ShardedJedis jedis = null;
		Set<Tuple> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrangeWithScores(key, start, end);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrangeWithScore err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �� {@link #zrange(String, int, int)} ������ͬ, Ψһ��ͬ���Ƿ��ص������Ƿ���� */
	public Set<String> zrevrange(final String key, final int start, final int end) {
		ShardedJedis jedis = null;
		Set<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrevrange(key, start, end);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrevrange err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �� {@link #zrevrange(String, int, int)} ������ͬ, Ψһ��ͬ���Ƿ��ص����򼯵�Ԫ�ذ�����Ա����� */
	public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int end) {
		ShardedJedis jedis = null;
		Set<Tuple> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrevrangeWithScores(key, start, end);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrevrangeWithScores err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** ��ȡ���� key �ĳ�Ա���� */
	public long zcard(String key) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.zcard(key);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zcard err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** ��ȡ���� key �г�Ա member �ķ��� */
	public double zscore(String key, String member) {
		ShardedJedis jedis = null;
		Double result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zscore(key, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zscore err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null != result ? result.doubleValue() : 0.0;
	}

	/** ��ȡ���� key �г�Ա member ������, ������ 0 Ϊ�� */
	public long zrank(String key, String member) {
		ShardedJedis jedis = null;
		Long result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrank(key, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrank err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null != result ? result.longValue() : -1L;
	}

	/** �� {@link #zrange(String, String)} ������ͬ, Ψһ��ͬ���Ƿ��ص������Ƿ���� */
	public long zrevrank(String key, String member) {
		ShardedJedis jedis = null;
		Long result = null;
		try {
			jedis = pool.getResource();
			result = jedis.zrevrank(key, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrevrank err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null != result ? result.longValue() : -1L;
	}

	/** ������ key ���һ������Ϊ score �ĳ�Ա member */
	public long zadd(String key, double score, String member) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.zadd(key, score, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zadd err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** ������ key ��ɾ����Ա member */
	public long zrem(String key, String member) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.zrem(key, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zrem err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �����򼯺ϵ��Ҷ�pop��һ��Ԫ�� */
	public String rpop(String key) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.rpop(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis rpush err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �����򼯺ϵ����pop��һ��Ԫ�� */
	public String lpop(String key) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.lpop(key);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis rpush err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/** �����򼯺ϵ����push����һ��Ԫ�� */
	public long lpush(String key, String value) {
		ShardedJedis jedis = null;
		long result = 0;
		try {
			jedis = pool.getResource();
			result = jedis.lpush(key, value);
		} catch (Exception e) {
			ApplicationLocal.instance().error("redis rpush err", e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * �����򼯺ϵ����push������Ԫ��,���S���б��С
	 */
	public boolean lpushAndLtrim(String key, String[] values, int capacity){
		if(capacity <= 0){
			ApplicationLocal.instance().error("redis lpushAndLtrim err. capacity is " + capacity);
			return false;
		}
    	ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			if(jedis.lpush(key, values) > capacity){
				jedis.ltrim(key, 0, capacity - 1);
			}
			return true;
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis lpushAndLtrim err", t);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
    }
	
	public String lset(String key, long index, String value) {
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.lset(key, index, value);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis lset err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	/**
	 * ������ key ��ɾ�������� start �� end ֮��ĳ�Ա<br/>
	 * start �� end �� 0 ��ʼ, ������ʾ����<br/>
	 * ����� start �� end �� 2 ����Ա (������)
	 */
	public long zremrangeByRank(final String key, final int start, final int end) {
		ShardedJedis jedis = null;
		long result = 0L;
		try {
			jedis = pool.getResource();
			result = jedis.zremrangeByRank(key, start, end);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zremrangeByRank err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * key��Ӧ��map�У�memberΪ�Ǹ�map�еļ���Ӧ��ֵ����score����
	 * ����Ǹ�map�в�����member��Ӧ��ֵ����score��Ϊֵ��put��ȥ
	 */
	public Double zincrby(String key, double score, String member) {
		ShardedJedis jedis = null;
		Double result = 0d;
		try {
			jedis = pool.getResource();
			result = jedis.zincrby(key, score, member);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis zremrangeByRank err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Long hincrBy(String mapKey, String subKey, long increment) {
		ShardedJedis jedis = null;
		Long result = null;
		try {
			jedis = pool.getResource();
			result = jedis.hincrBy(mapKey, subKey, increment);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis hincrBy err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Boolean hexist(String key, String field) {
		ShardedJedis jedis = null;
		Boolean result = null;
		try {
			jedis = pool.getResource();
			result = jedis.hexists(key, field);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis hexist err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Set<String> hKeys(String key) {
		ShardedJedis jedis = null;
		Set<String> result = null;
		try {
			jedis = pool.getResource();
			result = jedis.hkeys(key);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis hKeys err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}

		return result;
	}
	
	public Long hset(String key, String field, String value){
		ShardedJedis jedis = null;
		Long result = null;
		try {
			jedis = pool.getResource();
			result = jedis.hset(key, field, value);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis hKeys err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}

		return result;
	}

	public String hget(String key, String field){
		ShardedJedis jedis = null;
		String result = null;
		try {
			jedis = pool.getResource();
			result = jedis.hget(key, field);
		} catch (Throwable t) {
			ApplicationLocal.instance().error("redis hKeys err", t);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}

		return result;
	}
	
	public Map<String, String> hgetAll(String key) {
		return getHashMapAll(key);
	}

	public Map<String, String> hmget(String key, String... fields) {
		return getHashMap(key, fields);
	}
	
	public boolean hmset(String key, Map<String, String> values) {
		return setHashMap(key, values);
	}
	
}

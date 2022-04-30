package cn.hutool.core.cache.impl;

import cn.hutool.core.cache.GlobalPruneTimer;
import cn.hutool.core.lang.mutable.Mutable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时缓存<br>
 * 此缓存没有容量限制，对象只有在过期后才会被移除
 *
 * @author Looly
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class TimedCache<K, V> extends StampedCache<K, V> {
	private static final long serialVersionUID = 1L;

	/** 正在执行的定时任务 */
	private ScheduledFuture<?> pruneJobFuture;

	/**
	 * 构造
	 *
	 * @param timeout 超时（过期）时长，单位毫秒
	 */
	public TimedCache(final long timeout) {
		this(timeout, new HashMap<>());
	}

	/**
	 * 构造
	 *
	 * @param timeout 过期时长
	 * @param map 存储缓存对象的map
	 */
	public TimedCache(final long timeout, final Map<Mutable<K>, CacheObj<K, V>> map) {
		this.capacity = 0;
		this.timeout = timeout;
		this.cacheMap = map;
	}

	// ---------------------------------------------------------------- prune
	/**
	 * 清理过期对象
	 *
	 * @return 清理数
	 */
	@Override
	protected int pruneCache() {
		int count = 0;
		final Iterator<CacheObj<K, V>> values = cacheObjIter();
		CacheObj<K, V> co;
		while (values.hasNext()) {
			co = values.next();
			if (co.isExpired()) {
				values.remove();
				onRemove(co.key, co.obj);
				count++;
			}
		}
		return count;
	}

	// ---------------------------------------------------------------- auto prune
	/**
	 * 定时清理
	 *
	 * @param delay 间隔时长，单位毫秒
	 */
	public void schedulePrune(final long delay) {
		this.pruneJobFuture = GlobalPruneTimer.INSTANCE.schedule(this::prune, delay);
	}

	/**
	 * 取消定时清理
	 */
	public void cancelPruneSchedule() {
		if (null != pruneJobFuture) {
			pruneJobFuture.cancel(true);
		}
	}

}

package cn.hutool.core.map;

import java.util.Map;

/**
 * 双向Map<br>
 * 互换键值对不检查值是否有重复，如果有则后加入的元素替换先加入的元素<br>
 * 值的顺序在HashMap中不确定，所以谁覆盖谁也不确定，在有序的Map中按照先后顺序覆盖，保留最后的值<br>
 * 它与TableMap的区别是，BiMap维护两个Map实现高效的正向和反向查找
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @since 5.2.6
 */
public class BiMap<K, V> extends MapWrapper<K, V> {
	private static final long serialVersionUID = 1L;

	private Map<V, K> inverse;

	/**
	 * 构造
	 *
	 * @param raw 被包装的Map
	 */
	public BiMap(final Map<K, V> raw) {
		super(raw);
	}

	@Override
	public V put(final K key, final V value) {
		if (null != this.inverse) {
			this.inverse.put(value, key);
		}
		return super.put(key, value);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		super.putAll(m);
		if (null != this.inverse) {
			m.forEach((key, value) -> this.inverse.put(value, key));
		}
	}

	@Override
	public V remove(final Object key) {
		final V v = super.remove(key);
		if(null != this.inverse && null != v){
			this.inverse.remove(v);
		}
		return v;
	}

	@Override
	public boolean remove(final Object key, final Object value) {
		return super.remove(key, value) && null != this.inverse && this.inverse.remove(value, key);
	}

	@Override
	public void clear() {
		super.clear();
		this.inverse = null;
	}

	/**
	 * 获取反向Map
	 *
	 * @return 反向Map
	 */
	public Map<V, K> getInverse() {
		if (null == this.inverse) {
			inverse = MapUtil.inverse(getRaw());
		}
		return this.inverse;
	}

	/**
	 * 根据值获得键
	 *
	 * @param value 值
	 * @return 键
	 */
	public K getKey(final V value) {
		return getInverse().get(value);
	}
}

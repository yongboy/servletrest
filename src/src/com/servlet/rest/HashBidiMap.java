package com.servlet.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 构建一个Map实现，可以通过键(key)得到值(value);可以通过值(value)得到多值键(values)数组
 * 因为仅仅需要一个可以单向反转的Map数组元素，Apache Commons collections 和 Google Collections
 * 都提供了BidiMap实现，但无法做到反转过来的一对多的Map
 * 为了使依赖尽可能的少，只好重构一个，继承HashMap，仅仅覆盖平常常用方法。添加一个reverse方法，进行反转.
 * 有点适配器模式的味道，继承只好，被附加了新的职责
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public class HashBidiMap<K, V> implements Map<K, V> {
	private Map<K, V> map = null;
	private Map<V, K> bidiMap = null;

	public HashBidiMap() {
		map = new HashMap<K, V>();

		bidiMap = new HashMap<V, K>();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public V get(Object key) {
		return map.get(key);
	}

	public V put(K key, V value) {
		bidiMap.put(value, key);

		return map.put(key, value);
	}

	public V remove(Object key) {
		V value = map.remove(key);

		bidiMap.remove(value);

		return value;
	}

	public void removeValue(Object value) {
		K key = bidiMap.remove(value);

		map.remove(key);
	}

	public void removeValueByClassPath(String valueClassPath) {
		Iterator<Entry<V, K>> iter = bidiMap.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<V, K> entry = iter.next();

			if (entry.getKey().getClass().getName().equals(valueClassPath)) {
				iter.remove();

				map.remove(entry.getValue());
				
				continue;
			}
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null || m.isEmpty()) {
			return;
		}

		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m
				.entrySet().iterator(); i.hasNext();) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			this.put(e.getKey(), e.getValue());
		}
	}

	public void clear() {
		map.clear();

		bidiMap.clear();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public Map<V, K> reverse() {
		return bidiMap;
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode() + bidiMap.hashCode();
	}
}
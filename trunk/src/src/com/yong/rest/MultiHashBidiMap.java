package com.yong.rest;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;

public class MultiHashBidiMap<K,V> extends HashMap<K,V> {
	private HashMap<K,V> map = null;
	private HashMap<V,Set<K>> bidiMap = null;

	public MultiHashBidiMap(){
		map = new HashMap<K,V>();

		bidiMap = new HashMap<V,Set<K>>();
	}

	@Override
	public Set<Map.Entry<K,V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

	@Override
	public V put(K key, V value) {
		Set<K> coll = bidiMap.get(value);
        if (coll == null) {
            coll = createCollection(null);
            bidiMap.put(value, coll);
        }
        coll.add(key);

		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m){
		if(m == null || m.isEmpty()){
			return;
		}

		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<? extends K, ? extends V> e = i.next();
            this.put(e.getKey(), e.getValue());
        }
	}

	@Override
	public V remove(Object key) {
		V value = map.remove(key);

		bidiMap.remove(value);

        return value;
    }

    @Override
    public void clear() {
    	map.clear();

    	bidiMap.clear();
    }

    @Override
    public boolean containsValue(Object value){
    	return map.containsValue(value);
    }

	@Override
	public Object clone() {
		return map.clone();
	}

	public HashMap<V,Set<K>> reverse(){
		return bidiMap;
	}

	private Set<K> createCollection(Set<K> coll) {
        if (coll == null) {
            return new HashSet<K>();
        } else {
            return new HashSet<K>(coll);
        }
    }

	@Override
    public Set<K> keySet() {
        return map.keySet();
    }

	@Override
    public Collection<V> values() {
    	return map.values();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
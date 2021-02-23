// Copyright 2021 Sebastian Kuerten
//
// This file is part of android-map-tile-manager.
//
// android-map-tile-manager is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// android-map-tile-manager is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with android-map-tile-manager. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.android.mapview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.topobyte.adt.misc.uniquedeque.UniqueLinkedList;

/**
 * @author Sebastian Kuerten (sebastian.kuerten@fu-berlin.de)
 * 
 *         A synchronized RefreshableCache implementation based on a HashMap as
 *         a storage for key-value pairs and a UniqueLinkedList as a efficient
 *         means for implementing a least-recently-refreshed-or-inserted
 *         replacement strategy.
 * 
 * @param <K>
 *            the key type.
 * @param <V>
 *            the value type.
 */
public class MemoryCachePlus<K, V> implements RefreshableCache<K, V>
{

	private int size;
	private UniqueLinkedList<K> keys = new UniqueLinkedList<>();
	private Map<K, V> map = new HashMap<>();

	/**
	 * Create a memory cache.
	 * 
	 * @param size
	 *            the number of elements to store.
	 */
	public MemoryCachePlus(int size)
	{
		this.size = size;
	}

	@Override
	public synchronized CacheEntry<K, V> put(K key, V value)
	{
		if (map.containsKey(key)) {
			map.put(key, value);
		} else {
			map.put(key, value);
			keys.addFirst(key);
			if (keys.size() > size) {
				K removedKey = keys.removeLast();
				V removedValue = map.get(removedKey);
				map.remove(removedKey);
				return new CacheEntry<>(removedKey, removedValue);
			}
		}
		return null;
	}

	@Override
	public synchronized V get(K key)
	{
		if (map.containsKey(key))
			return map.get(key);
		return null;
	}

	@Override
	public synchronized V remove(K key)
	{
		if (map.containsKey(key)) {
			return map.remove(key);
		}
		return null;
	}

	@Override
	public synchronized void refresh(K key)
	{
		if (!map.containsKey(key)) {
			return;
		}
		keys.moveToFront(key);
	}

	@Override
	public synchronized void clear()
	{
		keys.clear();
		map.clear();
	}

	@Override
	public synchronized Set<K> getKeys()
	{
		return Collections.unmodifiableSet(map.keySet());
	}

	@Override
	public synchronized Collection<V> getValues()
	{
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public synchronized int getSize()
	{
		return size;
	}

	@Override
	public synchronized Collection<V> setSize(int size)
	{
		this.size = size;
		if (keys.size() <= size) {
			return null;
		}
		List<V> removed = new ArrayList<>();
		while (keys.size() > size) {
			K removedKey = keys.removeLast();
			V removedValue = map.remove(removedKey);
			removed.add(removedValue);
		}
		return removed;
	}
}

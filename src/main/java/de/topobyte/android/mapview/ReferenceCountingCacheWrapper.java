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

import java.util.Collection;
import java.util.Set;

/**
 * A wrapper around a RefreshableCache that implements appropriate reference
 * counting for the objects stored in the cache. That means that inserted
 * elements will have their reference counter incremented and removed or
 * replaced elements will have their reference counter decremented.
 * 
 * @author Sebastian Kuerten (sebastian.kuerten@fu-berlin.de)
 * 
 * @param <K>
 *            the type of keys
 * @param <V>
 *            the type of stored values.
 */
public class ReferenceCountingCacheWrapper<K, V extends ReferenceCounted>
		implements RefreshableCache<K, V>
{

	private RefreshableCache<K, V> delegate;

	public ReferenceCountingCacheWrapper(RefreshableCache<K, V> delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public CacheEntry<K, V> put(K key, V value)
	{
		value.increment();
		CacheEntry<K, V> removed = delegate.put(key, value);
		if (removed != null) {
			removed.getValue().decrement();
		}
		return removed;
	}

	@Override
	public V get(K key)
	{
		return delegate.get(key);
	}

	@Override
	public V remove(K key)
	{
		V removedValue = delegate.remove(key);
		removedValue.decrement();
		return removedValue;
	}

	@Override
	public void clear()
	{
		Collection<V> values = delegate.getValues();
		for (V value : values) {
			value.decrement();
		}
		delegate.clear();
	}

	@Override
	public Set<K> getKeys()
	{
		return delegate.getKeys();
	}

	@Override
	public Collection<V> getValues()
	{
		return delegate.getValues();
	}

	@Override
	public void refresh(K key)
	{
		delegate.refresh(key);
	}

	@Override
	public int getSize()
	{
		return delegate.getSize();
	}

	@Override
	public Collection<V> setSize(int size)
	{
		Collection<V> removed = delegate.setSize(size);
		if (removed != null) {
			for (V value : removed) {
				value.decrement();
			}
		}
		return removed;
	}

}

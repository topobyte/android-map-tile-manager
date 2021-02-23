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

public interface Cache<K, V>
{

	/**
	 * Put key, value into the cache. The implementation may remove another
	 * element out of the cache. If this happens, the replaced key and value
	 * will be returned. Otherwise null will be returned.
	 * 
	 * @param key
	 *            the key.
	 * @param value
	 *            the value.
	 * @return the key removed or null
	 */
	public abstract CacheEntry<K, V> put(K key, V value);

	/**
	 * Get the stored element for the specified key or null if there is no
	 * element in the cache for the specified key.
	 * 
	 * @param key
	 *            the key to retrieve a value for.
	 * @return the value or null if the cache does not contain a value for the
	 *         key.
	 */
	public abstract V get(K key);

	/**
	 * Remove and get the stored element if any.
	 * 
	 * @param key
	 *            the key to remove the value for.
	 * @return the removed element or null.
	 */
	public abstract V remove(K key);

	/**
	 * Clear this cache. Removes all elements.
	 */
	public abstract void clear();

	/**
	 * Get an unmodifiable view on the keys in the cache.
	 * 
	 * @return an unmodifiable set.
	 */
	public abstract Set<K> getKeys();

	/**
	 * Get an unmodifiable view on the values in the cache.
	 * 
	 * @return an unmodifiable set.
	 */
	public abstract Collection<V> getValues();

	/**
	 * Get the number of elements this cache can store
	 * 
	 * @return the size of the cache
	 */
	public abstract int getSize();

	/**
	 * Set the number of elements this cache can store
	 * 
	 * @param size
	 *            the size of the cache
	 */
	public abstract Collection<V> setSize(int size);

}
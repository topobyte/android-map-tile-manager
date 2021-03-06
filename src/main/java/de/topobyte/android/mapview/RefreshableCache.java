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

public interface RefreshableCache<K, V> extends Cache<K, V>
{

	/**
	 * Reorder the key within the replacement list. The exact meaning is
	 * implementation dependent but it should have the meaning of the
	 * replacement strategy of the cache assign some kind of higher priority to
	 * the key refreshed.
	 * 
	 * @param key
	 *            the key to reorder.
	 */
	public abstract void refresh(K key);
}

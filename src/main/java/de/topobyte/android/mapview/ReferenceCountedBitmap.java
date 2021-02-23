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

import android.graphics.Bitmap;
import android.util.Log;

public class ReferenceCountedBitmap implements ReferenceCounted
{

	private Bitmap bitmap;

	public ReferenceCountedBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}

	int rc = 0;

	@Override
	public void increment()
	{
		rc += 1;
	}

	@Override
	public void decrement()
	{
		rc -= 1;
		if (rc == 0) {
			reachedZeroReferences();
		}
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	private void reachedZeroReferences()
	{
		Log.i("cleanup", "recycling image");
		bitmap.recycle();
	}
}

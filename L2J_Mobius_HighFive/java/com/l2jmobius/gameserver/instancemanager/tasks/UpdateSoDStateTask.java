/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jmobius.gameserver.instancemanager.tasks;

import com.l2jmobius.gameserver.instancemanager.SoDManager;

/**
 * Task which updates Seed of Destruction state.
 * @author xban1x
 */
public final class UpdateSoDStateTask implements Runnable
{
	@Override
	public void run()
	{
		final SoDManager manager = SoDManager.getInstance();
		manager.setSoDState(1, true);
		manager.updateSodState();
	}
}

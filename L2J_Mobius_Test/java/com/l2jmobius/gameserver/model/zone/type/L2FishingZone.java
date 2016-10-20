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
package com.l2jmobius.gameserver.model.zone.type;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import com.l2jmobius.Config;
import com.l2jmobius.gameserver.ThreadPoolManager;
import com.l2jmobius.gameserver.model.actor.L2Character;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.zone.L2ZoneType;
import com.l2jmobius.gameserver.model.zone.ZoneId;
import com.l2jmobius.gameserver.network.serverpackets.ExAutoFishAvailable;

/**
 * A fishing zone
 * @author durgus, Darky999
 */
public class L2FishingZone extends L2ZoneType
{
	private final Map<Integer, Future<?>> _task = new HashMap<>();
	int _fishx = 0;
	int _fishy = 0;
	int _fishz = 0;
	
	public L2FishingZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		if (character.isPlayable() || (character.getLevel() > 85))
		{
			character.setInsideZone(ZoneId.FISHING, true);
		}
		
		if (character.isPlayer() && !_task.containsKey(((L2PcInstance) character).getObjectId()))
		{
			_task.put(((L2PcInstance) character).getObjectId(), ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new fishingAvailable((L2PcInstance) character), 500, 2000));
		}
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		if (character.isPlayer())
		{
			character.setInsideZone(ZoneId.FISHING, false);
		}
		_task.remove(character.getObjectId());
		final Future<?> t = _task.get(character.getObjectId());
		if (t != null)
		{
			t.cancel(false);
		}
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
		onExit(character);
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
		onEnter(character);
	}
	
	/*
	 * getWaterZ() this added function returns the Z value for the water surface. In effect this simply returns the upper Z value of the zone. This required some modification of L2ZoneForm, and zone form extentions.
	 */
	public int getWaterZ()
	{
		return getZone().getHighZ();
	}
	
	class fishingAvailable implements Runnable
	{
		private final L2PcInstance player;
		
		fishingAvailable(L2PcInstance pl)
		{
			player = pl;
		}
		
		@Override
		public void run()
		{
			if (Config.ALLOWFISHING && player.isInsideZone(ZoneId.FISHING) && !player.isInsideZone(ZoneId.WATER) && !player.isInBoat() && !player.isInCraftMode() && !player.isInStoreMode() && !player.isTransformed())
			{
				player.sendPacket(new ExAutoFishAvailable(player));
			}
		}
	}
}
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
package org.l2jmobius.gameserver.network.serverpackets.dailymission;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.DailyMissionData;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExConnectedTimeAndGettableReward implements IClientOutgoingPacket
{
	public static final ExConnectedTimeAndGettableReward STATIC_PACKET = new ExConnectedTimeAndGettableReward();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if (!DailyMissionData.getInstance().isAvailable())
		{
			return true;
		}
		
		OutgoingPackets.EX_CONNECTED_TIME_AND_GETTABLE_REWARD.writeId(packet);
		for (int i = 0; i < 16; i++) // TODO : Find what the hell it is
		{
			packet.writeD(0);
		}
		return true;
	}
}

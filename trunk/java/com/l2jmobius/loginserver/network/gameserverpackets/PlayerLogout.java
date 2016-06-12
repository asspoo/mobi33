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
package com.l2jmobius.loginserver.network.gameserverpackets;

import java.util.logging.Logger;

import com.l2jmobius.Config;
import com.l2jmobius.commons.util.network.BaseRecievePacket;
import com.l2jmobius.loginserver.GameServerTable;
import com.l2jmobius.loginserver.GameServerThread;

/**
 * @author -Wooden-
 */
public class PlayerLogout extends BaseRecievePacket
{
	protected static Logger _log = Logger.getLogger(PlayerLogout.class.getName());
	
	/**
	 * @param decrypt
	 * @param server
	 */
	public PlayerLogout(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final String account = readS();
		
		server.removeAccountOnGameServer(account);
		if (Config.DEBUG)
		{
			_log.info("Player " + account + " logged out from gameserver [" + server.getServerId() + "] " + GameServerTable.getInstance().getServerNameById(server.getServerId()));
		}
	}
}

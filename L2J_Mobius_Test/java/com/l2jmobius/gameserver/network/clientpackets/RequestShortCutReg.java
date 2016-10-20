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
package com.l2jmobius.gameserver.network.clientpackets;

import com.l2jmobius.gameserver.enums.ShortcutType;
import com.l2jmobius.gameserver.model.Shortcut;
import com.l2jmobius.gameserver.network.serverpackets.ShortCutRegister;

public final class RequestShortCutReg extends L2GameClientPacket
{
	private static final String _C__3D_REQUESTSHORTCUTREG = "[C] 3D RequestShortCutReg";
	
	private ShortcutType _type;
	private int _id;
	private int _slot;
	private int _page;
	private int _lvl;
	private int _characterType; // 1 - player, 2 - pet
	
	@Override
	protected void readImpl()
	{
		final int typeId = readD();
		_type = ShortcutType.values()[(typeId < 1) || (typeId > 6) ? 0 : typeId];
		final int slot = readD();
		_slot = slot % 12;
		_page = slot / 12;
		_id = readD();
		_lvl = readD();
		if ((typeId == 2) && (_lvl > 10000))
		{
			_lvl = _lvl >> 16;
		}
		_characterType = readD();
	}
	
	@Override
	protected void runImpl()
	{
		if ((getActiveChar() == null) || (_page > 10) || (_page < 0))
		{
			return;
		}
		
		final Shortcut sc = new Shortcut(_slot, _page, _type, _id, _lvl, _characterType);
		getActiveChar().registerShortCut(sc);
		sendPacket(new ShortCutRegister(sc));
	}
	
	@Override
	public String getType()
	{
		return _C__3D_REQUESTSHORTCUTREG;
	}
	
	@Override
	protected boolean triggersOnActionRequest()
	{
		return false;
	}
}

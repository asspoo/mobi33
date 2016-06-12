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
package com.l2jmobius.gameserver.data.xml.impl;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jmobius.commons.util.IGameXmlReader;
import com.l2jmobius.commons.util.IXmlReader;
import com.l2jmobius.gameserver.model.ensoul.EnsoulFee;
import com.l2jmobius.gameserver.model.ensoul.EnsoulOption;
import com.l2jmobius.gameserver.model.ensoul.EnsoulStone;
import com.l2jmobius.gameserver.model.holders.ItemHolder;
import com.l2jmobius.gameserver.model.items.type.CrystalType;

/**
 * @author UnAfraid
 */
public class EnsoulData implements IGameXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(EnsoulData.class.getName());
	private final Map<CrystalType, EnsoulFee> _ensoulFees = new EnumMap<>(CrystalType.class);
	private final Map<Integer, EnsoulOption> _ensoulOptions = new HashMap<>();
	private final Map<Integer, EnsoulStone> _ensoulStones = new HashMap<>();
	
	protected EnsoulData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		parseDatapackDirectory("data/stats/ensoul", true);
		LOGGER.info(getClass().getSimpleName() + ": Loaded: " + _ensoulFees.size() + " fees");
		LOGGER.info(getClass().getSimpleName() + ": Loaded: " + _ensoulOptions.size() + " options");
		LOGGER.info(getClass().getSimpleName() + ": Loaded: " + _ensoulStones.size() + " stones");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, IXmlReader::isNode, ensoulNode ->
		{
			switch (ensoulNode.getNodeName())
			{
				case "fee":
				{
					parseFees(ensoulNode);
					break;
				}
				case "option":
				{
					parseOptions(ensoulNode);
					break;
				}
				case "stone":
				{
					parseStones(ensoulNode);
					break;
				}
			}
		}));
	}
	
	private void parseFees(Node ensoulNode)
	{
		final CrystalType type = parseEnum(ensoulNode.getAttributes(), CrystalType.class, "crystalType");
		final EnsoulFee fee = new EnsoulFee(type);
		forEach(ensoulNode, IXmlReader::isNode, feeNode ->
		{
			switch (feeNode.getNodeName())
			{
				case "first":
				{
					parseFee(feeNode, fee, 0);
					break;
				}
				case "secondary":
				{
					parseFee(feeNode, fee, 1);
					break;
				}
				case "third":
				{
					parseFee(feeNode, fee, 2);
					break;
				}
				case "reFirst":
				{
					parseReFee(feeNode, fee, 0);
					break;
				}
				case "reSecondary":
				{
					parseReFee(feeNode, fee, 1);
					break;
				}
				case "reThird":
				{
					parseReFee(feeNode, fee, 2);
					break;
				}
			}
		});
	}
	
	private void parseFee(Node ensoulNode, EnsoulFee fee, int index)
	{
		final NamedNodeMap attrs = ensoulNode.getAttributes();
		final int id = parseInteger(attrs, "itemId");
		final int count = parseInteger(attrs, "count");
		fee.setEnsoul(index, new ItemHolder(id, count));
		_ensoulFees.put(fee.getCrystalType(), fee);
	}
	
	private void parseReFee(Node ensoulNode, EnsoulFee fee, int index)
	{
		final NamedNodeMap attrs = ensoulNode.getAttributes();
		final int id = parseInteger(attrs, "itemId");
		final int count = parseInteger(attrs, "count");
		fee.setResoul(index, new ItemHolder(id, count));
	}
	
	private void parseOptions(Node ensoulNode)
	{
		final NamedNodeMap attrs = ensoulNode.getAttributes();
		final int id = parseInteger(attrs, "id");
		final String name = parseString(attrs, "name");
		final String desc = parseString(attrs, "desc");
		final int skillId = parseInteger(attrs, "skillId");
		final int skillLevel = parseInteger(attrs, "skillLevel");
		final EnsoulOption option = new EnsoulOption(id, name, desc, skillId, skillLevel);
		_ensoulOptions.put(option.getId(), option);
	}
	
	private void parseStones(Node ensoulNode)
	{
		final NamedNodeMap attrs = ensoulNode.getAttributes();
		final int id = parseInteger(attrs, "id");
		final int slotType = parseInteger(attrs, "slotType");
		final EnsoulStone stone = new EnsoulStone(id, slotType);
		forEach(ensoulNode, "option", optionNode -> stone.addOption(parseInteger(optionNode.getAttributes(), "id")));
		_ensoulStones.put(stone.getId(), stone);
	}
	
	public ItemHolder getEnsoulFee(CrystalType type, int index)
	{
		final EnsoulFee fee = _ensoulFees.get(type);
		return fee != null ? fee.getEnsoul(index) : null;
	}
	
	public ItemHolder getResoulFee(CrystalType type, int index)
	{
		final EnsoulFee fee = _ensoulFees.get(type);
		return fee != null ? fee.getResoul(index) : null;
	}
	
	public EnsoulOption getOption(int id)
	{
		return _ensoulOptions.get(id);
	}
	
	public EnsoulStone getStone(int id)
	{
		return _ensoulStones.get(id);
	}
	
	/**
	 * Gets the single instance of EnsoulData.
	 * @return single instance of EnsoulData
	 */
	public static final EnsoulData getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final EnsoulData _instance = new EnsoulData();
	}
}

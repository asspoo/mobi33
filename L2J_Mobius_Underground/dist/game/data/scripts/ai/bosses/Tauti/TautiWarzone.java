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
package ai.bosses.Tauti;

import java.util.List;

import com.l2jmobius.gameserver.enums.Movie;
import com.l2jmobius.gameserver.model.Location;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.instancezone.Instance;

import instances.AbstractInstance;

/**
 * Tauti Warzone instance zone.
 * @author Mobius
 */
public final class TautiWarzone extends AbstractInstance
{
	// NPCs
	private static final int FINARIA = 33675;
	private static final int ZAHAK = 19266;
	private static final int TELEPORTER = 33678;
	private static final int TAUTI_COMMON = 29233;
	private static final int TAUTI_EXTREME = 29234;
	private static final int TAUTI_COMMON_AXE = 29236;
	private static final int TAUTI_EXTREME_AXE = 29237;
	// Item
	private static final int KEY_OF_DARKNESS = 34899;
	// Teleport
	private static final Location TAUTI_TELEPORT = new Location(-148972, 209879, -10199);
	// Misc
	private static final int TEMPLATE_ID = 218;
	private static final int EXTREME_TEMPLATE_ID = 219;
	private static final int DOOR_1 = 15240001;
	private static final int DOOR_2 = 15240002;
	
	public TautiWarzone()
	{
		super(TEMPLATE_ID, EXTREME_TEMPLATE_ID);
		addTalkId(FINARIA, TELEPORTER);
		addFirstTalkId(FINARIA, TELEPORTER);
		addAttackId(TAUTI_COMMON, TAUTI_EXTREME);
		addKillId(ZAHAK, TAUTI_COMMON_AXE, TAUTI_EXTREME_AXE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "enterEasyInstance":
			{
				enterInstance(player, npc, TEMPLATE_ID);
				startQuestTimer("WORLD_STATUS_CHECK", 5000, null, player, false);
				break;
			}
			case "enterExtremeInstance":
			{
				enterInstance(player, npc, EXTREME_TEMPLATE_ID);
				startQuestTimer("WORLD_STATUS_CHECK", 5000, null, player, false);
				break;
			}
			case "useKey":
			{
				final Instance world = npc.getInstanceWorld();
				if (hasAtLeastOneQuestItem(player, KEY_OF_DARKNESS))
				{
					takeItems(player, KEY_OF_DARKNESS, -1);
					world.setStatus(3);
					return "33678-3.htm";
				}
				return "33678-1.htm";
			}
			case "teleport":
			{
				final Instance world = npc.getInstanceWorld();
				world.setStatus(4);
				world.openCloseDoor(DOOR_2, true);
				for (L2PcInstance member : world.getPlayers())
				{
					member.teleToLocation(TAUTI_TELEPORT, world);
					startQuestTimer("PLAY_OPENING_B_MOVIE", 5000, null, member, false);
				}
				startQuestTimer("SPAWN_TAUTI", 60000, npc, null, false);
				break;
			}
			case "PLAY_OPENING_B_MOVIE":
			{
				playMovie(player, Movie.SC_TAUTI_OPENING_B);
				break;
			}
			case "SPAWN_TAUTI":
			{
				final Instance world = npc.getInstanceWorld();
				world.openCloseDoor(DOOR_1, true);
				world.spawnGroup("boss");
				break;
			}
			case "SPAWN_AXE":
			{
				final L2Npc axe = addSpawn(npc.getId() == TAUTI_EXTREME ? TAUTI_EXTREME_AXE : TAUTI_COMMON_AXE, npc, false, 0, false, npc.getInstanceId());
				axe.setRandomWalking(false);
				axe.setIsImmobilized(true);
				break;
			}
			case "WORLD_STATUS_CHECK":
			{
				if ((player == null) || (player.getInstanceId() == 0))
				{
					return null;
				}
				final Instance world = player.getInstanceWorld();
				if (world.isStatus(0))
				{
					world.setStatus(1);
					world.spawnGroup("room1");
					playMovie(world.getPlayers(), Movie.SC_TAUTI_OPENING);
					for (L2PcInstance member : world.getPlayers())
					{
						takeItems(member, KEY_OF_DARKNESS, -1);
					}
					startQuestTimer("WORLD_STATUS_CHECK", 5000, null, player, false);
				}
				else if (world.isStatus(1))
				{
					if (world.getAliveNpcs(L2MonsterInstance.class).isEmpty())
					{
						world.setStatus(2);
						final List<L2Npc> monsters = world.spawnGroup("room2");
						for (L2Npc monster : monsters)
						{
							monster.setRandomWalking(false);
						}
					}
					startQuestTimer("WORLD_STATUS_CHECK", 5000, null, player, false);
				}
				return null;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		switch (npc.getId())
		{
			case FINARIA:
			{
				htmltext = "33675.htm";
				break;
			}
			case TELEPORTER:
			{
				final int worldStatus = npc.getInstanceWorld().getStatus();
				if (worldStatus > 2)
				{
					htmltext = "33678-3.htm";
				}
				else if ((worldStatus == 2) && hasAtLeastOneQuestItem(player, KEY_OF_DARKNESS))
				{
					htmltext = "33678-2.htm";
				}
				else
				{
					htmltext = "33678-1.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		if (world.isStatus(5) && (npc.getCurrentHp() < (npc.getMaxHp() * 0.15)))
		{
			world.setStatus(6);
			npc.deleteMe();
			startQuestTimer("SPAWN_AXE", 15000, npc, null, false);
			playMovie(world.getPlayers(), Movie.SC_TAUTI_PHASE);
		}
		if (world.isStatus(4))
		{
			world.setStatus(5);
			world.openCloseDoor(DOOR_1, false);
			world.openCloseDoor(DOOR_2, false);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc.getId() == ZAHAK)
		{
			npc.dropItem(killer, KEY_OF_DARKNESS, 1);
		}
		else
		{
			final Instance world = npc.getInstanceWorld();
			playMovie(world.getPlayers(), Movie.SC_TAUTI_ENDING);
			npc.deleteMe();
			world.finishInstance(5 * 60 * 1000);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public void onInstanceCreated(Instance instance, L2PcInstance player)
	{
		if (player != null)
		{
			showHtmlFile(player, instance.getTemplateId() == EXTREME_TEMPLATE_ID ? "PartyEnterExtreme.html" : "PartyEnterCommon.html");
		}
	}
	
	public static void main(String[] args)
	{
		new TautiWarzone();
	}
}
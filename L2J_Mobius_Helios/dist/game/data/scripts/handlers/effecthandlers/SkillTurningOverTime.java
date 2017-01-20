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
package handlers.effecthandlers;

import com.l2jmobius.commons.util.Rnd;
import com.l2jmobius.gameserver.model.StatsSet;
import com.l2jmobius.gameserver.model.effects.AbstractEffect;
import com.l2jmobius.gameserver.model.skills.BuffInfo;
import com.l2jmobius.gameserver.model.stats.Formulas;

/**
 * Skill Turning effect implementation.
 */
public final class SkillTurningOverTime extends AbstractEffect
{
	private final int _chance;
	private final boolean _staticChance;
	
	public SkillTurningOverTime(StatsSet params)
	{
		_chance = params.getInt("chance", 100);
		_staticChance = params.getBoolean("staticChance", false);
		setTicks(params.getInt("ticks"));
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		if ((info.getEffected() == null) || (info.getEffected() == info.getEffector()) || info.getEffected().isRaid())
		{
			return false;
		}
		
		final boolean skillSuccess = _staticChance ? Formulas.calcProbability(_chance, info.getEffector(), info.getEffected(), info.getSkill()) : ((_chance >= 100) || (Rnd.get(100) < _chance));
		if (skillSuccess)
		{
			info.getEffected().breakCast();
		}
		
		return super.onActionTime(info);
	}
}
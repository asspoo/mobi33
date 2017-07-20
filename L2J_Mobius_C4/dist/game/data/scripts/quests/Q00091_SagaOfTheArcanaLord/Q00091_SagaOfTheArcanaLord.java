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
package quests.Q00091_SagaOfTheArcanaLord;

import quests.SagasSuperClass;

/**
 * @author Emperorc
 */
public class Q00091_SagaOfTheArcanaLord extends SagasSuperClass
{
	public static String qn1 = "Q00091_SagaOfTheArcanaLord";
	public static int qnu = 91;
	public static String qna = "Saga of the Arcana Lord";
	
	public Q00091_SagaOfTheArcanaLord()
	{
		super(qnu, qn1, qna);
		NPC = new int[]
		{
			8605,
			8622,
			8585,
			8608,
			8586,
			8646,
			8647,
			8651,
			8654,
			8655,
			8658,
			8608
		};
		Items = new int[]
		{
			7080,
			7604,
			7081,
			7506,
			7289,
			7320,
			7351,
			7382,
			7413,
			7444,
			7110,
			0
		};
		Mob = new int[]
		{
			5313,
			5240,
			5310
		};
		qn = qn1;
		classid = new int[]
		{
			96
		};
		prevclass = new int[]
		{
			0x0e
		};
		X = new int[]
		{
			119518,
			181215,
			181227
		};
		Y = new int[]
		{
			-28658,
			36676,
			36703
		};
		Z = new int[]
		{
			-3811,
			-4812,
			-4816
		};
		Text = new String[]
		{
			"PLAYERNAME! Pursued to here! However, I jumped out of the Banshouren boundaries! You look at the giant as the sign of power!",
			"... Oh ... good! So it was ... let's begin!",
			"I do not have the patience ..! I have been a giant force ...! Cough chatter ah ah ah!",
			"Paying homage to those who disrupt the orderly will be PLAYERNAME's death!",
			"Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...",
			"Why do you interfere others' battles?",
			"This is a waste of time.. Say goodbye...!",
			"...That is the enemy",
			"...Goodness! PLAYERNAME you are still looking?",
			"PLAYERNAME ... Not just to whom the victory. Only personnel involved in the fighting are eligible to share in the victory.",
			"Your sword is not an ornament. Don't you think, PLAYERNAME?",
			"Goodness! I no longer sense a battle there now.",
			"let...",
			"Only engaged in the battle to bar their choice. Perhaps you should regret.",
			"The human nation was foolish to try and fight a giant's strength.",
			"Must...Retreat... Too...Strong.",
			"PLAYERNAME. Defeat...by...retaining...and...Mo...Hacker",
			"....! Fight...Defeat...It...Fight...Defeat...It..."
		};
		registerNPCs();
	}
	
	public static void main(String[] args)
	{
		new Q00091_SagaOfTheArcanaLord();
	}
}
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
package quests.Q00090_SagaOfTheStormScreamer;

import quests.SagasSuperClass;

/**
 * @author Emperorc
 */
public class Q00090_SagaOfTheStormScreamer extends SagasSuperClass
{
	public static String qn1 = "Q00090_SagaOfTheStormScreamer";
	public static int qnu = 90;
	public static String qna = "Saga of the Storm Screamer";
	
	public Q00090_SagaOfTheStormScreamer()
	{
		super(qnu, qn1, qna);
		NPC = new int[]
		{
			7175,
			8627,
			8287,
			8287,
			8598,
			8646,
			8649,
			8652,
			8654,
			8655,
			8659,
			8287
		};
		Items = new int[]
		{
			7080,
			7531,
			7081,
			7505,
			7288,
			7319,
			7350,
			7381,
			7412,
			7443,
			7084,
			0
		};
		Mob = new int[]
		{
			5252,
			5239,
			5256
		};
		qn = qn1;
		classid = new int[]
		{
			110
		};
		prevclass = new int[]
		{
			0x28
		};
		X = new int[]
		{
			161719,
			124376,
			124355
		};
		Y = new int[]
		{
			-92823,
			82127,
			82155
		};
		Z = new int[]
		{
			-1893,
			-2796,
			-2803
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
		new Q00090_SagaOfTheStormScreamer();
	}
}
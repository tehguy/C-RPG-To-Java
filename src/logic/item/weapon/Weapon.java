/*
   JAdventure - A Java-based RPG
   Copyright (C) 2017  TehGuy

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package logic.item.weapon;

import com.google.gson.annotations.Expose;
import logic.item.Item;

public class Weapon extends Item {
    @Expose private int minDamage;
    @Expose private int maxDamage;

    public Weapon(int id, String name, String namePlural, int minDamage, int maxDamage){
        super(id, name, namePlural);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public int getMinDamage(){
        return minDamage;
    }

    public int getMaxDamage(){
        return maxDamage;
    }
}

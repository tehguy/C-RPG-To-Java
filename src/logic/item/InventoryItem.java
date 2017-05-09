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

package logic.item;

import logic.core.World;

public class InventoryItem {
    private int details;
    private int quantity;

    public InventoryItem(int item, int quantity){
        details = item;
        this.quantity = quantity;
    }

    public Item getDetails() {
        return World.ItemByID(details);
    }

    public void setDetails(int details) {
        this.details = details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        World.sendObserverNotification("plr_inv_additem");
    }

    public void incrementQuantity(){
        setQuantity(quantity + 1);
    }
}

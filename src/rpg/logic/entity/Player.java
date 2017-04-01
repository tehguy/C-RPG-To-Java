package rpg.logic.entity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import rpg.logic.core.Location;
import rpg.logic.core.RandomNumberGenerator;
import rpg.logic.core.World;
import rpg.logic.enums.LocationID;
import rpg.logic.item.HealingPotion;
import rpg.logic.item.InventoryItem;
import rpg.logic.item.Item;
import rpg.logic.item.LootItem;
import rpg.logic.item.weapon.Weapon;
import rpg.logic.quests.PlayerQuest;
import rpg.logic.quests.Quest;
import rpg.logic.quests.QuestCompletionItem;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int gold;
    private int expPoints;
    private int level;

    private List<InventoryItem> inventory;
    private List<PlayerQuest> quests;

    private Location currentLocation = null;

    private Weapon currentWeapon = null;

    public Player(int currentHitPoints, int maxHitPoints, int gold, int expPoints, int level) {
        super(currentHitPoints, maxHitPoints);
        setGold(gold);
        setExpPoints(expPoints);
        setLevel(level);
        inventory = new ArrayList<>();
        quests = new ArrayList<>();
    }

    public void fireInitMessages() {
        World.sendObserverNotification("plr_gold");
        World.sendObserverNotification("plr_exp");
        World.sendObserverNotification("plr_curhp");
        World.sendObserverNotification("plr_lvl");
    }

    @Override
    public void setCurrentHitPoints(int hitPoints) {
        super.setCurrentHitPoints(hitPoints);
        World.sendObserverNotification("plr_curhp");
    }

    private void addExperiencePoints(int xp) {
        setExpPoints(expPoints + xp);
    }

    public int getGold() {
        return gold;
    }

    private void setGold(int gold) {
        this.gold = gold;
        World.sendObserverNotification("plr_gold");
    }

    public int getExpPoints() {
        return expPoints;
    }

    private void setExpPoints(int expPoints) {
        this.expPoints = expPoints;
        World.sendObserverNotification("plr_exp");
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
        World.sendObserverNotification("plr_lvl");
    }

    @Contract(pure = true)
    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public List<PlayerQuest> getQuests() {
        return quests;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    private void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        World.sendMessengerObserverNotification("location",
                "\n\n" + currentLocation.getName() + "\n" + currentLocation.getDescription());
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Object currentWeapon) {
        if (currentWeapon instanceof Weapon) {
            this.currentWeapon = (Weapon) currentWeapon;
        }
    }

    @NotNull
    private Boolean hasThisQuest(Quest quest) {
        for (PlayerQuest pq : quests) {
            if (pq.getDetails().getID() == quest.getID()) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    private Boolean completedThisQuest(Quest quest) {
        for (PlayerQuest pq : quests) {
            if (pq.getDetails().getID() == quest.getID()) {
                return pq.isCompleted();
            }
        }

        return false;
    }

    @NotNull
    private Boolean hasAllQuestCompletionItems(Quest quest) {
        for (QuestCompletionItem qci : quest.getQuestCompletionItems()) {
            boolean foundItemInPlayerInv = false;

            for (InventoryItem item : inventory) {
                if (item.getDetails().getID() == qci.getDetails().getID()) {
                    foundItemInPlayerInv = true;

                    if (item.getQuantity() < qci.getQuantity()) {
                        return false;
                    }
                }
            }

            if (!foundItemInPlayerInv) {
                return false;
            }
        }

        return true;
    }

    private void removeQuestCompletionItems(Quest quest) {
        for (QuestCompletionItem qci : quest.getQuestCompletionItems()) {
            for (InventoryItem item : inventory) {
                if (item.getDetails().getID() == qci.getDetails().getID()) {
                    item.setQuantity(item.getQuantity() - qci.getQuantity());
                    break;
                }
            }
        }

        World.sendObserverNotification("plr_inv_additem");
    }

    public void addItemToInventory(Item itemToAdd) {
        for (InventoryItem item : inventory) {
            if (item.getDetails().getID() == itemToAdd.getID()) {
                item.incrementQuantity();
                return;
            }
        }

        inventory.add(new InventoryItem(itemToAdd, 1));

        World.sendObserverNotification("plr_inv_additem");
    }

    private void addQuestToList(PlayerQuest quest) {
        quests.add(quest);
        World.sendObserverNotification("plr_quest");
    }

    private void markQuestAsCompleted(Quest quest) {
        for (PlayerQuest pq : quests) {
            if (pq.getDetails().getID() == quest.getID()) {
                pq.setCompleted();
                return;
            }
        }
    }

    public List<Object> getWeapons() {
        List<Object> weapons = new ArrayList<>();

        for (InventoryItem item : inventory) {
            if (item.getDetails() instanceof Weapon && item.getQuantity() > 0) {
                weapons.add(item.getDetails());
            }
        }

        return weapons;
    }

    public List<Object> getPotions() {
        List<Object> potions = new ArrayList<>();

        for (InventoryItem item : inventory) {
            if (item.getDetails() instanceof HealingPotion && item.getQuantity() > 0) {
                potions.add(item.getDetails());
            }
        }

        return potions;
    }

    @NotNull
    private Boolean hasRequiredItemToEnter(Location location) {
        if (location.getItemRequiredToEnter() == null) {
            return true;
        }

        for (InventoryItem item : getInventory()) {
            if (item.getDetails().getID() == location.getItemRequiredToEnter().getID()) {
                return true;
            }
        }

        return false;
    }

    private void moveTo(Location newLocation) {
        if (!hasRequiredItemToEnter(newLocation)) {
            World.sendMessengerObserverNotification("message",
                    "\nYou must have a " + newLocation.getItemRequiredToEnter().getName()
                            + " to enter this location\n");
            return;
        }

        setCurrentLocation(newLocation);
        setCurrentHitPoints(getMaxHitPoints());

        if (newLocation.getQuestAvailableHere() != null) {
            if (hasThisQuest(newLocation.getQuestAvailableHere())) {
                if (!completedThisQuest(newLocation.getQuestAvailableHere())) {

                    if (hasAllQuestCompletionItems(newLocation.getQuestAvailableHere())) {
                        completeQuest(newLocation.getQuestAvailableHere());
                    }
                }
            } else {
                giveQuestToPlayer(newLocation.getQuestAvailableHere());
            }
        }

        setMonsterForCurrentLocation(newLocation);
    }

    private void setMonsterForCurrentLocation(Location location) {
        if (location.getMonsterLivingHere() != null) {
            World.sendMessengerObserverNotification("message", "You see a "
                    + location.getMonsterLivingHere().getName() + "\n");

            Monster standardMonster = World.MonsterByID(location.getMonsterLivingHere().getID());

            World.setCurrentMonster(new Monster(standardMonster.getID(), standardMonster.getName(), standardMonster.getMaxDamage(),
                    standardMonster.getRewardExperiencePoints(), standardMonster.getRewardGold(),
                    standardMonster.getCurrentHitPoints(), standardMonster.getMaxHitPoints()));

            for (LootItem li : standardMonster.getLootTable()) {
                World.getCurrentMonster().getLootTable().add(li);
            }
        } else {
            World.setCurrentMonster(null);
        }
    }

    private void completeQuest(Quest quest) {
        World.sendMessengerObserverNotification("message", "You complete the \'"
                + quest.getName() + "\' quest.\n");
        removeQuestCompletionItems(quest);

        World.sendMessengerObserverNotification("message", "You receive: \n"
                + Integer.toString(quest.getRewardExperiencePoints())
                + " XP points\n"
                + Integer.toString(quest.getRewardGold()) + " gold\n"
                + quest.getRewardItem().getName() + "\n\n");

        addExperiencePoints(quest.getRewardExperiencePoints());
        setGold(gold + quest.getRewardGold());

        addItemToInventory(quest.getRewardItem());

        markQuestAsCompleted(quest);
    }

    private void giveQuestToPlayer(Quest quest) {
        World.sendMessengerObserverNotification("message", "You receive the \'"
                + quest.getName() + "\' quest.\n"
                + "To complete it, return with:\n");

        for (QuestCompletionItem qci : quest.getQuestCompletionItems()) {
            if (qci.getQuantity() == 1) {
                World.sendMessengerObserverNotification("message",
                        Integer.toString(qci.getQuantity()) + " " + qci.getDetails().getName() + "\n");
            } else {
                World.sendMessengerObserverNotification("message",
                        Integer.toString(qci.getQuantity()) + " " + qci.getDetails().getNamePlural()
                                + "\n");
            }
        }
        World.sendMessengerObserverNotification("message", "\n");

        addQuestToList(new PlayerQuest(quest));
    }

    public void useWeapon(Object cboObject) {
        Weapon weapon;

        if (cboObject instanceof Weapon) {
            weapon = (Weapon) cboObject;
        } else {
            return;
        }

        int damageToMonster = RandomNumberGenerator.NumberBetween(weapon.getMinDamage(),
                weapon.getMaxDamage());

        World.getCurrentMonster().setCurrentHitPoints(World.getCurrentMonster().getCurrentHitPoints() - damageToMonster);

        World.sendMessengerObserverNotification("message",
                "You hit the " + World.getCurrentMonster().getName() + " for " + damageToMonster
                        + " points.\n");

        if (World.getCurrentMonster().getCurrentHitPoints() <= 0) {
            World.sendMessengerObserverNotification("message", "\nYou defeated the "
                    + World.getCurrentMonster().getName() + "\n");

            setExpPoints(getExpPoints() + World.getCurrentMonster().getRewardExperiencePoints());
            World.sendMessengerObserverNotification("message", "You receive "
                    + World.getCurrentMonster().getRewardExperiencePoints() + " experience points.\n");

            setGold(getGold() + World.getCurrentMonster().getRewardGold());
            World.sendMessengerObserverNotification("message", "You receive "
                    + World.getCurrentMonster().getRewardGold() + " gold.\n");

            List<InventoryItem> lootedItems = new ArrayList<>();

            for (LootItem lootItem : World.getCurrentMonster().getLootTable()) {
                if (RandomNumberGenerator.NumberBetween(1, 100) <= lootItem.getDropPercentage()) {
                    lootedItems.add(new InventoryItem(lootItem.getDetails(), 1));
                }
            }

            if (lootedItems.size() == 0) {
                for (LootItem lootItem : World.getCurrentMonster().getLootTable()) {
                    if (lootItem.isDefaultItem()) {
                        lootedItems.add(new InventoryItem(lootItem.getDetails(), 1));
                    }
                }
            }

            for (InventoryItem item : lootedItems) {
                addItemToInventory(item.getDetails());

                if (item.getQuantity() == 1) {
                    World.sendMessengerObserverNotification("message", "You loot " + item.getQuantity()
                            + " " + item.getDetails().getName() + "\n");
                } else {
                    World.sendMessengerObserverNotification("message", "You loot " + item.getQuantity()
                            + " " + item.getDetails().getNamePlural() + "\n");
                }
            }

            World.sendMessengerObserverNotification("message", "\n");

            moveTo(getCurrentLocation());
        } else {
            monsterAttack();
        }
    }

    public void drinkPotion(Object cboObject) {
        HealingPotion potion;

        if (cboObject instanceof HealingPotion) {
            potion = (HealingPotion) cboObject;
        } else {
            return;
        }

        setCurrentHitPoints(getCurrentHitPoints() + potion.getAmountToHeal());

        if (getCurrentHitPoints() > getMaxHitPoints()) {
            setCurrentHitPoints(getMaxHitPoints());
        }

        for (InventoryItem item : getInventory()) {
            if (item.getDetails().getID() == potion.getID()) {
                item.setQuantity(item.getQuantity() - 1);
                break;
            }
        }

        World.sendMessengerObserverNotification("message", "You drink a " + potion.getName() + "\n");

        monsterAttack();
    }

    private void monsterAttack() {
        int damageToPlayer = RandomNumberGenerator.NumberBetween(0, World.getCurrentMonster().getMaxDamage());

        World.sendMessengerObserverNotification("message", "The "
                + World.getCurrentMonster().getName() + " did " + damageToPlayer + " points of damage.\n");

        setCurrentHitPoints(getCurrentHitPoints() - damageToPlayer);

        if (getCurrentHitPoints() <= 0) {
            World.sendMessengerObserverNotification("message", "The "
                    + World.getCurrentMonster().getName() + " killed you.\n");

            moveHome();
        }
    }

    public void moveHome() {
        moveTo(World.LocationByID(LocationID.HOME));
        World.sendObserverNotification("plr_move");
    }

    public void moveNorth() {
        if (currentLocation.getLocationToNorth() != null) {
            moveTo(currentLocation.getLocationToNorth());
            World.sendObserverNotification("plr_move");
        }
    }

    public void moveEast() {
        if (currentLocation.getLocationToEast() != null) {
            moveTo(currentLocation.getLocationToEast());
            World.sendObserverNotification("plr_move");
        }
    }

    public void moveSouth() {
        if (currentLocation.getLocationToSouth() != null) {
            moveTo(currentLocation.getLocationToSouth());
            World.sendObserverNotification("plr_move");
        }
    }

    public void moveWest() {
        if (currentLocation.getLocationToWest() != null) {
            moveTo(currentLocation.getLocationToWest());
            World.sendObserverNotification("plr_move");
        }
    }
}

package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestPriority;
import com.narxoz.rpg.quest.QuestLog;
import java.util.List;

/**
 * Orchestrates a planning session that uses both Iterator and Mediator.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int dispatchesBefore = 0;
        int notificationsBefore = 0;

        if (hall instanceof GuildHall) {
            GuildHall guildHall = (GuildHall) hall;
            dispatchesBefore = guildHall.getTotalDispatches();
            notificationsBefore = guildHall.getTotalNotifications();
        }

        int questsTraversed = 0;
        int localMessagesRouted = 0;

        System.out.println();
        System.out.println("=== CouncilEngine: party review ===");
        for (Hero hero : party) {
            System.out.println("Hero ready: " + hero);
        }

        System.out.println();
        System.out.println("=== CouncilEngine: Iterator #1 - arrival order ===");
        QuestIterator orderedIterator = questLog.ordered();

        while (orderedIterator.hasNext()) {
            Quest quest = orderedIterator.next();
            questsTraversed++;

            System.out.println("Planning quest: " + quest);

            hall.dispatch(GuildHall.SCOUTING, null,
                    "Check road and enemy movement for: " + quest.getTitle());
            localMessagesRouted++;

            hall.dispatch(GuildHall.SUPPLIES, null,
                    "Prepare supplies for reward level " + quest.getRewardGold()
                            + " gold: " + quest.getTitle());
            localMessagesRouted++;

            if (quest.isUrgent() || quest.getPriority().ordinal() >= QuestPriority.HIGH.ordinal()) {
                hall.dispatch(GuildHall.HEALING, null,
                        "High risk mission needs extra medicine: " + quest.getTitle());
                localMessagesRouted++;
            }

            if (quest.getTitle().toLowerCase().contains("cursed")
                    || quest.getTitle().toLowerCase().contains("ruins")) {
                hall.dispatch(GuildHall.LORE, null,
                        "Research old records before starting: " + quest.getTitle());
                localMessagesRouted++;
            }
        }

        System.out.println();
        System.out.println("=== CouncilEngine: Iterator #2 - priority HIGH or higher ===");
        QuestIterator priorityIterator = questLog.priorityAtLeast(QuestPriority.HIGH);

        while (priorityIterator.hasNext()) {
            Quest quest = priorityIterator.next();
            questsTraversed++;

            System.out.println("Priority review: " + quest.getTitle()
                    + " | priority=" + quest.getPriority()
                    + " | reward=" + quest.getRewardGold());

            hall.dispatch(GuildHall.ORDERS, null,
                    "Captain-level review required for: " + quest.getTitle());
            localMessagesRouted++;

            if (quest.isUrgent()) {
                hall.dispatch(GuildHall.CURSE, null,
                        "Urgent danger check for: " + quest.getTitle());
                localMessagesRouted++;
            }
        }

        int finalMessagesRouted = localMessagesRouted;
        int finalMembersNotified = 0;

        if (hall instanceof GuildHall) {
            GuildHall guildHall = (GuildHall) hall;
            finalMessagesRouted = guildHall.getTotalDispatches() - dispatchesBefore;
            finalMembersNotified = guildHall.getTotalNotifications() - notificationsBefore;
        }
        return new CouncilRunResult(questsTraversed, finalMessagesRouted, finalMembersNotified);
    }
}

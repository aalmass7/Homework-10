package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

/**
 * Entry point for Homework 10 — The Adventurers' Guild: Iterator + Mediator.
 *
 * The scaffold prints the banner only; students fill in the guild demo.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        List<Hero> party = List.of(
                new Hero("Durotan", 120, 25, 12),
                new Hero("Garona", 90, 40, 18, 7, 50)
        );

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Clear the Goblin Road", QuestPriority.LOW, 80, false));
        questLog.add(new Quest("Escort the Merchant Caravan", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Recover the Cursed Crown", QuestPriority.HIGH, 250, false));
        questLog.add(new Quest("Defend the Eastern Gate", QuestPriority.URGENT, 400, true));
        questLog.add(new Quest("Explore the Sunken Ruins", QuestPriority.HIGH, 300, false));

        GuildHall hall = new GuildHall();
        Quartermaster quartermaster = new Quartermaster("Orgrim Doomhammer", hall);
        Scout scout = new Scout("Khadgar", hall);
        Healer healer = new Healer("Draka", hall);
        Captain captain = new Captain("Anduin Lothar", hall);
        Loremaster loremaster = new Loremaster("Medivh", hall);

        System.out.println();
        System.out.println("=== Mediator demo: direct colleague links are not used ===");
        captain.issueOrder(GuildHall.ORDERS,
                "Open the war council and prepare all officers.");
        scout.reportRoute(GuildHall.SCOUTING,
                "The road ahead is dangerous, but still passable before sunset.");
        quartermaster.requestSupplies(GuildHall.SUPPLIES,
                "Prepare weapons, torches, rations, rope, and spare arrows.");
        healer.prepareAid(GuildHall.HEALING,
                "Medical kits are ready for wounded fighters.");
        loremaster.shareLore(GuildHall.LORE,
                "The Cursed Crown is linked to an old royal tomb.");

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println();
        System.out.println("=== Main demo: Iterator #3 - reverse arrival order ===");
        printIterator(questLog.reverse());

        System.out.println();
        System.out.println("=== Main demo: Open/Closed Iterator - reward sorted ===");
        printIterator(questLog.rewardSorted());

        System.out.println();
        System.out.println("=== Final CouncilRunResult ===");
        System.out.println(result);
    }

    private static void printIterator(QuestIterator iterator) {
        while (iterator.hasNext()) {
            Quest quest = iterator.next();
            System.out.println(quest);
        }
    }
}

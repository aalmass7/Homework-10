package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topic-based mediator for the Adventurers' Guild war council.
 */
public class GuildHall implements GuildMediator {

    public static final String ORDERS = "orders";
    public static final String SCOUTING = "scouting";
    public static final String SUPPLIES = "supplies";
    public static final String HEALING = "healing";
    public static final String LORE = "lore";
    public static final String CURSE = "curse";
    public static final String HISTORY = "history";

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int totalDispatches;
    private int totalNotifications;

    @Override
    public void register(GuildMember member) {
        if (member == null) {
            return;
        }

        if (member instanceof Captain) {
            addSubscriber(ORDERS, member);
            addSubscriber(SCOUTING, member);
            addSubscriber(SUPPLIES, member);
            addSubscriber(HEALING, member);
            addSubscriber(LORE, member);
            addSubscriber(CURSE, member);
            addSubscriber(HISTORY, member);
        } else if (member instanceof Quartermaster) {
            addSubscriber(ORDERS, member);
            addSubscriber(SUPPLIES, member);
            addSubscriber(SCOUTING, member);
        } else if (member instanceof Scout) {
            addSubscriber(ORDERS, member);
            addSubscriber(SCOUTING, member);
            addSubscriber(LORE, member);
        } else if (member instanceof Healer) {
            addSubscriber(ORDERS, member);
            addSubscriber(HEALING, member);
            addSubscriber(CURSE, member);
        } else if (member instanceof Loremaster) {
            addSubscriber(ORDERS, member);
            addSubscriber(LORE, member);
            addSubscriber(CURSE, member);
            addSubscriber(HISTORY, member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        totalDispatches++;

        String safeTopic = topic == null ? "unknown" : topic;
        String safePayload = payload == null ? "" : payload;
        String senderName = from == null ? "CouncilEngine" : from.getName();

        List<GuildMember> subscribers = subscribersFor(safeTopic);

        System.out.println("[GuildHall] topic='" + safeTopic + "' from " + senderName
                + " -> subscribers=" + subscribers.size() + ": " + safePayload);

        if (subscribers.isEmpty()) {
            System.out.println("[GuildHall] No subscribers for topic '" + safeTopic + "'.");
            return;
        }

        for (GuildMember member : subscribers) {
            if (member != from) {
                member.receive(safeTopic, from, safePayload);
                totalNotifications++;
            }
        }
    }

    public int getTotalDispatches() {
        return totalDispatches;
    }

    public int getTotalNotifications() {
        return totalNotifications;
    }

    protected void addSubscriber(String topic, GuildMember member) {
        List<GuildMember> members = membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>());

        if (!members.contains(member)) {
            members.add(member);
        }
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}

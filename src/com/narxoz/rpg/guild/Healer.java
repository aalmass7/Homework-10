package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for wounds, potions, and recovery plans.
 */
public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "CouncilEngine" : from.getName();

        if (GuildHall.ORDERS.equals(topic)) {
            System.out.println("[Healer " + getName() + "] received order from " + sender
                    + ": packing healing kits. Details: " + payload);
        } else if (GuildHall.HEALING.equals(topic)) {
            System.out.println("[Healer " + getName() + "] received healing topic from " + sender
                    + ": preparing bandages and potions. Details: " + payload);
        } else if (GuildHall.CURSE.equals(topic)) {
            System.out.println("[Healer " + getName() + "] received curse warning from " + sender
                    + ": preparing cleansing herbs. Details: " + payload);
        } else {
            System.out.println("[Healer " + getName() + "] received " + topic
                    + " from " + sender + ": " + payload);
        }
    }
}

package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for gear, supplies, and rewards.
 */
public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "CouncilEngine" : from.getName();

        if (GuildHall.ORDERS.equals(topic)) {
            System.out.println("[Quartermaster " + getName() + "] received order from "
                    + sender + ": preparing standard gear. Details: " + payload);
        } else if (GuildHall.SUPPLIES.equals(topic)) {
            System.out.println("[Quartermaster " + getName() + "] received supply request from "
                    + sender + ": counting rations and rope. Details: " + payload);
        } else if (GuildHall.SCOUTING.equals(topic)) {
            System.out.println("[Quartermaster " + getName() + "] received scouting update from "
                    + sender + ": changing travel supplies. Details: " + payload);
        } else {
            System.out.println("[Quartermaster " + getName() + "] received " + topic
                    + " from " + sender + ": " + payload);
        }
    }
}

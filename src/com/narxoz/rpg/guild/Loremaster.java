package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember{

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "CouncilEngine" : from.getName();

        if (GuildHall.LORE.equals(topic)) {
            System.out.println("[Loremaster " + getName() + "] received lore request from "
                    + sender + ": checking old guild records. Details: " + payload);
        } else if (GuildHall.CURSE.equals(topic)) {
            System.out.println("[Loremaster " + getName() + "] received curse topic from "
                    + sender + ": identifying the ancient curse. Details: " + payload);
        } else if (GuildHall.HISTORY.equals(topic)) {
            System.out.println("[Loremaster " + getName() + "] received history topic from "
                    + sender + ": opening the archive. Details: " + payload);
        } else if (GuildHall.ORDERS.equals(topic)) {
            System.out.println("[Loremaster " + getName() + "] received order from " + sender
                    + ": ready to advise on old legends. Details: " + payload);
        } else {
            System.out.println("[Loremaster " + getName() + "] received " + topic
                    + " from " + sender + ": " + payload);
        }
    }
}

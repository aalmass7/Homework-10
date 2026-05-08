package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for route reports and reconnaissance.
 */
public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "CouncilEngine" : from.getName();

        if (GuildHall.ORDERS.equals(topic)) {
            System.out.println("[Scout " + getName() + "] received order from " + sender
                    + ": checking the safest road. Details: " + payload);
        } else if (GuildHall.SCOUTING.equals(topic)) {
            System.out.println("[Scout " + getName() + "] received scouting topic from " + sender
                    + ": updating the route map. Details: " + payload);
        } else if (GuildHall.LORE.equals(topic)) {
            System.out.println("[Scout " + getName() + "] received lore from " + sender
                    + ": marking dangerous ruins on the map. Details: " + payload);
        } else {
            System.out.println("[Scout " + getName() + "] received " + topic
                    + " from " + sender + ": " + payload);
        }
    }
}

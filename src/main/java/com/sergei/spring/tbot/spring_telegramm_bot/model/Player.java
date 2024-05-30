package com.sergei.spring.tbot.spring_telegramm_bot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("players")
public class Player {

    @Id
    private long chatId;

    private String name;
    private int age;
    private String race;
    private int HP;
    private int AP;

    public Player(final long chatId, final String name, final int age, final String race) {
        super();

        this.chatId = chatId;
        this.name = name;
        this.age = age;
        this.race = race;
        this.HP = 200;
        this.AP = 25;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(final long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(final String race) {
        this.race = race;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(final int HP) {
        this.HP = HP;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(final int AP) {
        this.AP = AP;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("chatId=").append(chatId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", race='").append(race).append('\'');
        sb.append(", HP=").append(HP);
        sb.append(", AP=").append(AP);
        sb.append('}');
        return sb.toString();
    }


}

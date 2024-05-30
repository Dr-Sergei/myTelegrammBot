package com.sergei.spring.tbot.spring_telegramm_bot.repositroy;

import com.sergei.spring.tbot.spring_telegramm_bot.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<Player, Long> {

    @Query("{chatId:'?0'}")
    Player findPlayerById(long chatId);

    public long count();
}

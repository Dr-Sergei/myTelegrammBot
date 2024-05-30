package com.sergei.spring.tbot.spring_telegramm_bot.service;


import com.sergei.spring.tbot.spring_telegramm_bot.config.BotConfig;
import com.sergei.spring.tbot.spring_telegramm_bot.model.Player;
import com.sergei.spring.tbot.spring_telegramm_bot.repositroy.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private BotConfig botConfig;

    @Autowired
    PlayerRepository playerRepository;

    final String HELP_TEXT = "if you have any problems please contact bot administrator\n\n" +
            "You can execute commands from the main menu on the left side\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /madata to see data stored about yourself\n\n" +
            "Type /help to see this message again";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        //menu
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/my_data", "show a users data"));
        listOfCommands.add(new BotCommand("/delete_data", "delete user data"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        listOfCommands.add(new BotCommand("/create_player", "create a new player"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot´s command list: {}", e.getMessage());
        }

    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    greetingMessage(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    helpInfo(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/create_player":
                    createPlayer(chatId, update.getMessage());
                default:
                    sendMessage(chatId, "sorry command not recognized!");
            }
        }
    }

    private void createPlayer(final long chatId, final Message message) {
        Player player = new Player(chatId,getBotUsername(),28,"ork");
    }

    //race buttons implementation
    private InlineKeyboardMarkup getRaceButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("Human").callbackData("race_Human").build());
        row1.add(InlineKeyboardButton.builder().text("Elf").callbackData("race_Elf").build());

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text("Orc").callbackData("race_Orc").build());
        row2.add(InlineKeyboardButton.builder().text("Dwarf").callbackData("race_Dwarf").build());

        buttons.add(row1);
        buttons.add(row2);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);

        return markup;
    }

    private void helpInfo(long chatId, String firstName) {
        String info = HELP_TEXT;
        log.info("\n help info replied to user {}", firstName + "\n");

        sendMessage(chatId, info);
    }

    //taking the firs name and chat id, generating answer and sending it back
    private void greetingMessage(long chatId, String firstName) {
        String answer = "Hi, " + firstName + ", nice to meet you!";

        log.info("\n Replied to user {}", firstName + "\n");
        sendMessage(chatId, answer);
    }

    //executing message send
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("error occurred in TelegramBot.class, method execute(): {}", e.getMessage());
        }

    }
}

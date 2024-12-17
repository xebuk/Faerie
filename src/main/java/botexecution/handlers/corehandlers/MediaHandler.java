package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import logger.BotLogger;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.File;

public class MediaHandler {
    private TelegramClient telegramClient;

    public MediaHandler(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    private File getFrame(String chatId) {
        File frame = new File(Constants.IMAGE_OUTPUT_PATH + chatId + "/output.png");
        return frame;
    }

    public Message sendPic(ChatSession cs) {
        InputFile photo = new InputFile(getFrame(cs.getChatId().toString()));
        SendPhoto pic = new SendPhoto(cs.getChatId().toString(), photo);
        pic.setReplyMarkup(KeyboardFactory.movementBoardGame());
        Message sent = null;
        try {
            sent = telegramClient.execute(pic);
        } catch (TelegramApiException e) {
            BotLogger.severe(e.getMessage());
        }
        return sent;
    }

    public void gamePovUpdater(ChatSession cs, int messageId) {
        InputMediaPhoto photo = new InputMediaPhoto(getFrame(cs.getChatId().toString()), "output.png");
        EditMessageMedia photoEdit = new EditMessageMedia(photo);
        photoEdit.setChatId(cs.getChatId());
        photoEdit.setMessageId(messageId);
        photoEdit.setReplyMarkup(KeyboardFactory.movementBoardGame());
        try {
            telegramClient.execute(photoEdit);
        } catch (TelegramApiException e) {
            BotLogger.severe(e.getMessage());
        }
    }
}

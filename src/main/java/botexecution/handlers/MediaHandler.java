package botexecution.handlers;

import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.DataReader;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MediaHandler {
    private TelegramClient telegramClient;

    public MediaHandler(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public int sendPic(ChatSession cs) {
        InputFile photo = new InputFile(DataReader.getFrame(cs.getChatId().toString()));
        SendPhoto pic = new SendPhoto(cs.getChatId().toString(), photo);
        pic.setReplyMarkup(KeyboardFactory.movementBoardGame());
        Message sent = null;
        try {
            sent = telegramClient.execute(pic);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        assert sent != null;
        return sent.getMessageId();
    }

    public void gamePovUpdater(ChatSession cs, int messageId) {
        InputMediaPhoto photo = new InputMediaPhoto(DataReader.getFrame(cs.getChatId().toString()), "output.png");
        EditMessageMedia photoEdit = new EditMessageMedia(photo);
        photoEdit.setChatId(cs.getChatId());
        photoEdit.setMessageId(messageId);
        photoEdit.setReplyMarkup(KeyboardFactory.movementBoardGame());
        try {
            telegramClient.execute(photoEdit);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

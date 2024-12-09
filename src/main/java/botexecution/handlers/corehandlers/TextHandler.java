package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;
import common.Constants;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

public class TextHandler  {
    private final SilentSender silent;

    private final int MAX_MESSAGE_SIZE = 4096;

    public TextHandler(SilentSender silent) {
        this.silent = silent;
    }

    public SilentSender getSilent() {
        return silent;
    }

    public void placeholder(ChatSession cs) {
        silent.send("Данная функция пока не работает в данное время. Ожидайте обновлений!", cs.getChatId());
    }

    public boolean reportImpossible(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_IMPOSSIBLE, cs.getChatId());
        return false;
    }

    public boolean reportFail(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_FAIL, cs.getChatId());
        return false;
    }

    public void articleMessaging(List<String> article, ChatSession cs) {
        StringBuilder partOfArticle = new StringBuilder();
        int lengthOfMessage = 0;

        for (String paragraph: article) {
            if (lengthOfMessage + paragraph.length() < MAX_MESSAGE_SIZE) {
                partOfArticle.append(paragraph);
                lengthOfMessage = lengthOfMessage + paragraph.length();
            }
            else {
                silent.send(partOfArticle.toString(), cs.getChatId());
                partOfArticle.setLength(0);
                lengthOfMessage = paragraph.length();
                partOfArticle.append(paragraph);
            }
        }
        silent.send(partOfArticle.toString(), cs.getChatId());
    }

    public void articleMessaging(String article, ChatSession cs, ReplyKeyboard function) {
        List<String> slicedArticle = List.of(article.split("\n"));
        StringBuilder partOfArticle = new StringBuilder();
        int lengthOfMessage = 0;

        for (String paragraph: slicedArticle) {
            if (lengthOfMessage + paragraph.length() + 1 < MAX_MESSAGE_SIZE) {
                partOfArticle.append(paragraph).append("\n");
                lengthOfMessage = lengthOfMessage + paragraph.length() + 1;
            }
            else {
                silent.send(partOfArticle.toString(), cs.getChatId());
                partOfArticle.setLength(0);
                lengthOfMessage = paragraph.length();
                partOfArticle.append(paragraph);
            }
        }
        patternExecute(cs, partOfArticle.toString(), function, false);
    }

    public void patternExecute(ChatSession cs, String message) {
        StringBuilder sign = new StringBuilder();
        if (!cs.isPM()) {
            sign.append(cs.username).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        silent.execute(text);
    }

    public void patternExecute(MessageContext ctx, String message) {
        StringBuilder sign = new StringBuilder();
        if (ctx.chatId() < 0) {
            sign.append(ctx.user().getUserName()).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(ctx.chatId().toString(), sign.toString());
        silent.execute(text);
    }

    public void patternExecute(ChatSession cs, String message, ReplyKeyboard function, boolean parseMode) {
        StringBuilder sign = new StringBuilder();
        if (!cs.isPM()) {
            sign.append(cs.username).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        if (function != null) {
            text.setReplyMarkup(function);
        }
        if (parseMode) {
            text.setParseMode("HTML");
            text.disableWebPagePreview();
        }
        silent.execute(text);
    }

    public void patternExecute(ChatSession cs, String username, String message, ReplyKeyboard function, boolean parseMode) {
        StringBuilder sign = new StringBuilder();
        sign.append(username).append("\n").append("----------------------------------").append("\n");
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        if (function != null) {
            text.setReplyMarkup(function);
        }
        if (parseMode) {
            text.setParseMode("HTML");
            text.disableWebPagePreview();
        }
        silent.execute(text);
    }

    public void patternExecute(MessageContext ctx, String message, ReplyKeyboard function, boolean parseMode) {
        StringBuilder sign = new StringBuilder();
        if (ctx.chatId() < 0) {
            sign.append(ctx.user().getUserName()).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(ctx.chatId().toString(), sign.toString());
        if (function != null) {
            text.setReplyMarkup(function);
        }
        if (parseMode) {
            text.setParseMode("HTML");
            text.disableWebPagePreview();
        }
        silent.execute(text);
    }

    public String variantsMessageConfigurator(List<String> variants) {
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < variants.size() + 1; i++) {
            text.append(i).append(". ").append(variants.get(i - 1)).append("\n\n");
        }

        return text.toString();
    }
}

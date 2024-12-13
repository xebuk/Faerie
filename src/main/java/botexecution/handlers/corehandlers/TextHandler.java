package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;

public class TextHandler implements AbilityExtension {
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

    public Optional<Message> patternExecute(ChatSession cs, String message) {
        StringBuilder sign = new StringBuilder();
        if (!cs.isPM()) {
            sign.append(cs.username).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        return silent.execute(text);
    }

    public Optional<Message> patternExecute(MessageContext ctx, String message) {
        StringBuilder sign = new StringBuilder();
        if (ctx.chatId() < 0) {
            sign.append(ctx.user().getUserName()).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(ctx.chatId().toString(), sign.toString());
        return silent.execute(text);
    }

    public Optional<Message> patternExecute(ChatSession cs, String message, ReplyKeyboard function, boolean parseMode) {
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
        return silent.execute(text);
    }

    public Optional<Message> patternExecute(ChatSession cs, String username, String message, ReplyKeyboard function, boolean parseMode) {
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
        return silent.execute(text);
    }

    public Optional<Message> patternExecute(MessageContext ctx, String message, ReplyKeyboard function, boolean parseMode) {
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
        return silent.execute(text);
    }

    public String variantsMessageConfigurator(List<String> variants) {
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < variants.size() + 1; i++) {
            text.append(i).append(". ").append(variants.get(i - 1)).append("\n\n");
        }

        return text.toString();
    }

    public void deleteMessages(ChatSession cs) {
        if (cs.messagesOnDeletion.isEmpty()) {
            return;
        }
        DeleteMessage onDeletion;
        while (!cs.messagesOnDeletion.isEmpty()) {
            onDeletion = new DeleteMessage(cs.getChatId().toString(),
                    cs.messagesOnDeletion.removeFirst().getMessageId());
            silent.execute(onDeletion);
        }
    }

    public Ability sayMofu() {
        Consumer<MessageContext> mofu = ctx -> silent.send("Mofu Mofu!", ctx.chatId());
        //есть в coremessages

        return Ability
                .builder()
                .name("mofu")
                .info("mofu")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(mofu)
                .build();
    }

    public Ability requestArticle() {
        Consumer<MessageContext> search = ctx -> patternExecute(ctx,
                Constants.SEARCH_MESSAGE, KeyboardFactory.searchBoard(), false);
        //есть в coremessages

        return Ability
                .builder()
                .name("search")
                .info("searches article on DnD.su")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(search)
                .build();
    }

    public Ability diceRoll() {
        Consumer<MessageContext> roll = ctx -> patternExecute(ctx,
                Constants.ROLL_MESSAGE, KeyboardFactory.rollVariantsBoard(), false);
        //есть в coremessages

        return Ability
                .builder()
                .name("roll")
                .info("rolls a dice")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(roll)
                .build();
    }
}

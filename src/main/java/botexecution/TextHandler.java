package botexecution;

import common.Constants;
import common.DataReader;
import common.SearchCategories;
import common.SiteParser;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;
import java.util.ArrayList;
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

    public boolean reportIncorrect(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_INCORRECT, cs.getChatId());
        return false;
    }

    public boolean reportFail(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_FAIL, cs.getChatId());
        return false;
    }

    public void rollCustom(ChatSession cs) {
        SendMessage rollVar;
        if (!cs.dicePresets.isEmpty()) {
            cs.checkPresetsSize();
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE_WITH_PRESETS);
            rollVar.setReplyMarkup(KeyboardFactory.rollCustomBoard(cs));
        }
        else {
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE);
        }
        silent.execute(rollVar);
        cs.rollCustom = true;
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

    public String variantsMessageConfigurator(List<String> variants) {
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < variants.size() + 1; i++) {
            text.append(i).append(". ").append(variants.get(i - 1)).append("\n\n");
        }

        return text.toString();
    }

    public boolean searchEngine(ChatSession cs, String entry) {
        ArrayList<String> matches;

        try {
            matches = DataReader.searchArticleIds(cs.sectionId.toString(), entry);
        } catch (IOException e) {
            return reportIncorrect(cs);
        }

        if (matches.isEmpty()) {
            reportFail(cs);
        }

        else if (matches.size() == 2) {
            ArrayList<String> article;
            try {
                switch (cs.sectionId) {
                    case SPELLS:
                        article = SiteParser.SpellsGrabber(matches.getFirst());
                        break;
                    case ITEMS:
                        article = SiteParser.ItemsGrabber(matches.getFirst());
                        break;
                    case BESTIARY:
                        article = SiteParser.BestiaryGrabber(matches.getFirst());
                        break;
                    case RACES:
                        article = SiteParser.RacesGrabber(matches.getFirst());
                        break;
                    case FEATS:
                        article = SiteParser.FeatsGrabber(matches.getFirst());
                        break;
                    case BACKGROUNDS:
                        article = SiteParser.BackgroundsGrabber(matches.getFirst());
                        break;
                    default:
                        reportImpossible(cs);
                        return false;
                }
            } catch (Exception e) {
                return reportIncorrect(cs);
            }

            articleMessaging(article, cs);
            cs.sectionId = SearchCategories.NONE;
            cs.searchSuccess = false;
            cs.title = "";
            return false;
        }

        patternExecute(cs, SiteParser.addressWriter(matches, cs.sectionId.toString()), null, true);
        return true;
    }
}

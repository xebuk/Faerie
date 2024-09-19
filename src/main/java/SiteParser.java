import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class SiteParser {
    private static String url = "https://dnd.su/";

    public static String SpellsItemsBestiaryGrabber(String section, String id) {
        String article;
        try {
            article = TokenReader.searchArticleId(section, id);
        } catch (IOException e) {
            article = id;
        }

        Connection link = Jsoup.connect(url + section + "/" + article);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("ul.params.card__article-body");

        Elements li = body.select("li:not(.subsection.desc)");
        Elements liDescBody = body.select("li.subsection.desc").select("p");

        StringBuilder result = new StringBuilder(name.text() + "\n");

        //System.out.println(name.text());
        for (Element i: li) {
            //System.out.println(i.text());
            result.append(i.text()).append("\n");
        }

        result.append("\n");
        //System.out.println();

        for (Element i : liDescBody) {
            //System.out.println(i.text() + "\n");
            result.append(i.text()).append("\n").append("\n");
        }

        result.append("Информация взята с ").append(url).append(section + "/" + article);
        return result.toString();
    }

    public static void DictWriter(String section) {
        Connection link;
        Document page = null;
        //ArrayList<String> data = new ArrayList<>();
        //data.add(" ");

        for (int i = 1; i < 401; i++) {
            Elements name = null;
            boolean fourofour = false;
            do {
                link = Jsoup.connect(url + section + "/" + i);
                try {
                    page = link.get();
                } catch (IOException e) {
                    fourofour = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");
            } while (name.text().isEmpty());

            if (fourofour) {
                continue;
            }

            //data.add("\"" + name.text() + "\"");
            System.out.println(i + "~ " + name.text());

        }
        //System.out.println(data);
    }
}

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteParser {
    private static String url = "https://dnd.su/";

    public static String SpellsItemsBestiaryGrabber(String section, String id) {
        String article = id;
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

        System.out.println(name.text());
        for (Element i: li) {
            System.out.println(i.text());
            result.append(i.text()).append("\n");
        }
        result.append("\n");
        System.out.println();
        for (Element i : liDescBody) {
            System.out.println(i.text() + "\n");
            result.append(i.text()).append("\n").append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        SiteParser.SpellsItemsBestiaryGrabber("spells", System.console().readLine());
    }
}

package common;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static common.Constants.URL;

public class SiteParser {

    public static ArrayList<String> SpellsItemsBestiaryGrabber(String section, String id) {
        String article;
        try {
            article = DataReader.searchArticleId(section, id);
        } catch (IOException e) {
            article = id;
        }

        Connection link = Jsoup.connect(URL + section + "/" + article);
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

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n");

        //System.out.println(name.text());
        for (Element i: li) {
            //System.out.println(i.text());
            result.add(i.text() + "\n");
        }

        result.add("\n");
        //System.out.println();

        for (Element i : liDescBody) {
            //System.out.println(i.text() + "\n");
            result.add(i.text() + "\n");
            result.add("\n");
        }

        result.add("Информация взята с " + URL + section + "/" + article);
        return result;
    }

    public static void DictWriter(String section) {
        Connection link;
        Document page = null;
        //ArrayList<String> data = new ArrayList<>();
        //data.add(" ");

        for (int i = 1; i < 401; i++) { // 401 - временное число, вскоре заменю на 1601
            Elements name = null;
            boolean pageNotFound = false;
            do {
                link = Jsoup.connect(URL + section + "/" + i);
                try {
                    page = link.get();
                } catch (IOException e) {
                    pageNotFound = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");
            } while (name.text().isEmpty());

            if (pageNotFound) {
                continue;
            }

            //data.add("\"" + name.text() + "\"");
            System.out.println(i + "~ " + name.text());

        }
        //System.out.println(data);
    }
}

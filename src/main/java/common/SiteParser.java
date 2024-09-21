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
        String article = id;

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
            //System.out.println(i.text() + "\n" + "\n");
            result.add(i.text() + "\n" + "\n");
        }

        result.add("Информация взята с " + URL + section + "/" + article);
        return result;
    }

    public static ArrayList<String> RacesGrabber(String id) {
        String article = id;

        Connection link = Jsoup.connect(URL + "race" + "/" + article);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements source = page.select("ul.params.card__article-body");

        Elements body = page.select("div.desc.card__article-body[itemprop=articleBody]");
        Elements p = body.select("p,h3.underlined");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n");
        result.add(source.text() + "\n");

        //System.out.println(name.text());
        //System.out.println(source.text());
        for (Element i: p) {
            //System.out.println(i.text());
            result.add(i.text() + "\n" + "\n");
        }

        result.add("Информация взята с " + URL + "race" + "/" + article);
        return result;
    }

    // Классы слишком длинные для чата, так что пока использую CLASSES_LIST в Constants
    // В будущем, если получится сделать какие-то короткие выдержки, то использую
    public static ArrayList<String> ClassesGrabber(String id) {
        String article = id;

        Connection link = Jsoup.connect(URL + "class" + "/" + article);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements source = page.select("ul.params.card__article-body");

        Elements body = page.select("div.desc.card__article-body[itemprop=articleBody]");
        Elements startInfo = body.select("div[style]").select("div.spoiler_head_body");
        Elements info = body.select("div:not(style),h4,h3,h2,p");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text());
        result.add(source.text() + "\n");

        System.out.println(name.text());
        System.out.println(source.text());
        for (Element i: startInfo) {
            System.out.println(i.text() + "\n");
            result.add(i.text() + "\n");
        }

        for (Element i: info) {
            System.out.println(i.text() + "\n");
            result.add(i.text() + "\n");
        }
        result.add("Информация взята с " + URL + "class" + "/" + article);
        return result;
    }

    public static ArrayList<String> FeatsGrabber(String id) {
        String article = id;

        Connection link = Jsoup.connect(URL + "feats" + "/" + article);
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
        result.add(liDescBody.text() + "\n");

        for (Element i : li) {
            //System.out.println(i.text());
            result.add("  -  " + i.text() + "\n");
        }
        result.add("Информация взята с " + URL + "feats" + "/" + article);
        return result;
    }

    public static ArrayList<String> BackgroundsGrabber(String id) {
        String article = id;

        Connection link = Jsoup.connect(URL + "backgrounds" + "/" + article);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("div[itemprop=description]");

        Elements descBody = body.select("p,h3.smallSectionTitle,div.table-wrapper");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n");

        for (Element i: descBody) {
            result.add(i.text() + "\n" + "\n");
        }
        result.add("Информация взята с " + URL + "feats" + "/" + article);
        return result;
    }

    public static void DictWriter(String section) {
        Connection link;
        Document page = null;
        //ArrayList<String> data = new ArrayList<>();
        //data.add(" ");

        for (int i = 1; i <= 1000; i++) {
            Elements name = null;
            Elements check1;
            boolean pageNotFound = false;
            do {
                link = Jsoup.connect(URL + section + "/" + i);
                try {
                    page = link.get();
                } catch (IOException e) {
                    pageNotFound = true;
                    break;
                }

                check1 = page.select("div");
                if (check1.hasClass("private-card") || check1.hasClass("card__group-homebrew")) {
                    pageNotFound = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");
            } while (name.text().isEmpty());

            if (pageNotFound) {
                continue;
            }

            System.out.println(i + "~ " + name.text());
        }
        //System.out.println(data);
    }

    public static void addressWriter(String section) {
        Connection link;
        Document page = null;

        for (int i = 70; i <= 150; i++) {
            Elements name = null;
            Elements check1;
            boolean pageNotFound = false;
            do {
                link = Jsoup.connect(URL + section + "/" + i);
                try {
                    page = link.get();
                } catch (IOException e) {
                    pageNotFound = true;
                    break;
                }

                check1 = page.select("div");
                if (check1.hasClass("private-card") || check1.hasClass("card__group-homebrew")) {
                    pageNotFound = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");
            } while (name.text().isEmpty());

            if (pageNotFound) {
                continue;
            }

            System.out.println("<a href=\"" + URL + section + "/" + i + "\">" + name.text() + "</a>");
        }
    }

    public static String addressWriter(ArrayList<String> entries, String section) {
        StringBuilder result = new StringBuilder();
        result.append("Results of a search:").append("\n");

        for (int i = 2; i <= entries.size(); i = i + 2) {
            result.append(entries.get(i - 2)).append(" - ").append("<a href=\"").append(URL).append(section).append("/").append(entries.get(i - 2)).append("\">").append(entries.get(i - 1)).append("</a>").append("\n");
        }

        result.append("Send index of an article that you want to get.");
        return result.toString();
    }

    public static void main(String[] args) {
        addressWriter("class");
    }
}

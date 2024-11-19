package common;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static common.Constants.URL;

public class SiteParser {

    public static String searchArticleId(String section, String name) throws IOException {
        try {
            int index = Integer.parseInt(name);
            return name;
        } catch (NumberFormatException ignored) {}

        Path articleIdFilePath = Path.of("../token_dir/searchID/" + section + ".txt");
        List<String> lines = Files.readAllLines(articleIdFilePath);;
        String[] separated;

        LevenshteinDistance env = new LevenshteinDistance();
        int minSimilarityDistance = Integer.MAX_VALUE;
        String resArticleId = "1";

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            int distance;
            if ((int) name.charAt(0) < 123 && (int) name.charAt(0) > 96) {
                distance = env.apply(separated[1].substring(separated[1].indexOf("[") + 1, separated[1].indexOf("]")), name);
            }
            else {
                try {
                    distance = env.apply(separated[1].substring(0, separated[1].indexOf("[")), name);
                } catch (StringIndexOutOfBoundsException e) {
                    distance = env.apply(separated[1].substring(0, name.length()), name);
                }
            }

            if (distance < minSimilarityDistance) {
                minSimilarityDistance = distance;
                resArticleId = separated[0];
            }
        }

        return resArticleId;
    }

    public static ArrayList<String> searchArticleIds(String section, String name) throws IOException {
        ArrayList<String> results = new ArrayList<>();

        Path articleIdFilePath = Path.of("../token_dir/searchID/" + section + ".txt");
        List<String> lines = Files.readAllLines(articleIdFilePath);
        String[] separated;

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            if (separated[1].toLowerCase().contains(name.toLowerCase())) {
                results.add(separated[0]);
                results.add(separated[1]);
            }
        }

        return results;
    }

    public static ArrayList<String> SpellsGrabber(String id) throws IOException {
        String article = searchArticleId("spells", id);

        Connection link = Jsoup.connect(URL + "spells/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("ul.params.card__article-body");

        Elements li = body.select("ul.params.card__article-body > li:not(.subsection.desc)");
        Elements liDescBodyMain = body.select("li.subsection.desc").select("p");
        Elements liDescBodyUl = body.select("li.subsection.desc").select("ul").select("li");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n" + "\n");

        //System.out.println(name.text());
        for (Element i: li) {
            //System.out.println(i.text());
            result.add(i.text() + "\n");
        }

        result.add("\n");
        //System.out.println();

        for (Element i : liDescBodyMain) {
            //System.out.println(i.text() + "\n" + "\n");
            result.add(i.text() + "\n" + "\n");
        }

        for (Element i : liDescBodyUl) {
            //System.out.println(i.text() + "\n" + "\n");
            result.add(i.text() + "\n" + "\n");
        }

        result.add("Информация взята с " + URL + "spells/" + article);
        return result;
    }

    public static ArrayList<String> ItemsGrabber(String id) throws IOException {
        String article = searchArticleId("items", id);;

        Connection link = Jsoup.connect(URL + "items/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("ul.params.card__article-body");

        Elements li = body.select("ul.params.card__article-body > li:not(.subsection.desc)");
        Elements liDescBody = body.select("li.subsection.desc")
                .select("h3.subsection-title,p,li,h2.bigSectionTitle.hide-next.hide-next-h2.active,td");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n" + "\n");

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

        result.add("Информация взята с " + URL + "items/" + article);
        return result;
    }

    public static ArrayList<String> BestiaryGrabber(String id) throws IOException {
        String article = searchArticleId("bestiary", id);

        Connection link = Jsoup.connect(URL + "bestiary/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements check = page.select("div").select("span");
        //System.out.println(check.text());
        Elements html = page.select("*");

        ArrayList<String> result = new ArrayList<>();

        if (check.hasText()) {
            html = page.select("div.card__group-classic");
            result.add("""
                    Классическая версия:
                    
                    """);
        }

        Elements name = html.select("h2.card-title[itemprop=name]");
        Elements body = html.select("ul.params.card__article-body");

        Elements li = body.select("li:not(.subsection.desc)");
        Elements liDescBody = body.select("li.subsection.desc").select("h3.subsection-title,p");

        for (Element i : name) {
            result.add(i.text() + "\n");
        }

        //System.out.println(name.text());
        for (Element i : li) {
            //System.out.println(i.text());
            if (i.hasClass("size-type-alignment")) {
                result.add("\n" + i.text() + "\n");
            } else {
                result.add(i.text() + "\n");
            }
        }

        result.add("\n");
        //System.out.println();

        for (Element i : liDescBody) {
            //System.out.println(i.text() + "\n" + "\n");
            result.add(i.text() + "\n\n");
        }

        if (check.hasText()) {
            result.add("\n");

            html = page.select("div.card__group-multiverse");

            name = html.select("h2.card-title[itemprop=name]");
            body = html.select("ul.params.card__article-body");

            li = body.select("li:not(.subsection.desc)");
            liDescBody = body.select("li.subsection.desc").select("h3.subsection-title,p");

            result.add("""
                    Версия Мультивселенной:
                    
                    """);

            for (Element i : name) {
                result.add(i.text() + "\n");
            }

            //System.out.println(name.text());
            for (Element i : li) {
                //System.out.println(i.text());
                if (i.hasClass("size-type-alignment")) {
                    result.add("\n" + i.text() + "\n");
                } else {
                    result.add(i.text() + "\n");
                }
            }

            result.add("\n");
            //System.out.println();

            for (Element i : liDescBody) {
                //System.out.println(i.text() + "\n" + "\n");
                result.add(i.text() + "\n\n");
            }
        }

        result.add("Информация взята с " + URL + "bestiary/" + article);
        return result;
    }

    public static ArrayList<String> RacesGrabber(String id) throws IOException {
        String article = searchArticleId("race", id);

        Connection link = Jsoup.connect(URL + "race/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

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

        result.add("Информация взята с " + URL + "race/" + article);
        return result;
    }

    // Классы слишком длинные для чата, так что пока использую CLASSES_LIST в Constants
    // В будущем, если получится сделать какие-то короткие выдержки, то использую
    public static ArrayList<String> ClassesGrabber(String id) throws IOException {
        String article = searchArticleId("class", id);

        Connection link = Jsoup.connect(URL + "class/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

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
        result.add("Информация взята с " + URL + "class/" + article);
        return result;
    }

    public static ArrayList<String> FeatsGrabber(String id) throws IOException {
        String article = searchArticleId("feats", id);

        Connection link = Jsoup.connect(URL + "feats/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

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
        result.add("Информация взята с " + URL + "feats/" + article);
        return result;
    }

    public static ArrayList<String> BackgroundsGrabber(String id) throws IOException {
        String article = searchArticleId("backgrounds", id);

        Connection link = Jsoup.connect(URL + "backgrounds/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("div[itemprop=description]");

        Elements descBody = body.select("p,h3.smallSectionTitle,h4.tableTitle,tr");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text() + "\n");

        for (Element i: descBody) {
            result.add(i.text() + "\n" + "\n");
        }
        result.add("Информация взята с " + URL + "feats/" + article);
        return result;
    }

    public static void BackgroundsPersonalityIdealBondFlawGrabber(String id) throws IOException {
        String article = searchArticleId("backgrounds", id);

        Connection link = Jsoup.connect(URL + "backgrounds/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements body = page.select("div[itemprop=description]");

        Elements tables = body.select("tr");

        for (Element i : tables) {
            String result = "\"" + i.text().substring(i.text().indexOf(" ") + 1).trim() + "\",";
            System.out.println(result);
        }
    }

    public static void BackgroundSpecialAbilityGrabber(String id) throws IOException {
        String article = searchArticleId("backgrounds", id);

        Connection link = Jsoup.connect(URL + "backgrounds/" + article);
        Document page;
        do {
            page = link.get();
        } while (!page.hasText());

        Elements body = page.select("div[itemprop=description]");

        Elements ability = body.select("h3.smallSectionTitle,h3.smallSectionTitle + p,p + p");

        for (Element i : ability) {
            String result = "\"" + i.text().trim() + "\",";
            System.out.println(result);
        }
    }

    public static void DictWriter(String section) {
        Connection link;
        Connection.Response response;
        Document page = null;
        //ArrayList<String> data = new ArrayList<>();
        //data.add(" ");

        for (int i = 1; i <= 4000; i++) {
            Elements name = null;
            Elements check1;
            boolean pageNotFound = false;
            do {
                link = Jsoup.connect(URL + section + "/" + i);
                try {
                    response = Jsoup.connect(URL + section + "/" + i).followRedirects(true).execute();
                    if (response.url().toString().contains("homebrew") || Objects.equals(response.url().toString(), "https://dnd.su")) {
                        pageNotFound = true;
                        break;
                    }
                } catch (IOException e) {
                    pageNotFound = true;
                    break;
                }

                try {
                    page = link.get();
                } catch (IOException e) {
                    pageNotFound = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");

                if (name.text().length() > 250) {
                    pageNotFound = true;
                    break;
                }
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

        result.append("Результаты поиска:").append("\n");
//        result.append("Results of a search:").append("\n");

        for (int i = 2; i <= entries.size(); i = i + 2) {
            result.append(entries.get(i - 2)).append(" - ").append("<a href=\"").append(URL).append(section).append("/").append(entries.get(i - 2)).append("\">").append(entries.get(i - 1)).append("</a>").append("\n");
        }

        result.append("Введите индекс или имя статьи, которую вы хотите получить.");
//        result.append("Send an index or a name of an article that you want to get.");
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            BackgroundSpecialAbilityGrabber("770");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package botexecution.handlers;

import botexecution.handlers.corehandlers.DataHandler;
import common.SearchCategories;
import dnd.values.aspectvalues.ItemsIdsDnD;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static common.Constants.BESTIARY;
import static common.Constants.URL;

public class SiteParseHandler {
    private final DataHandler knowledge;

    public SiteParseHandler(DataHandler knowledge) {
        this.knowledge = knowledge;
    }

    public ArrayList<String> spellsGrabber(String id) {
        String article = knowledge.searchArticleId("spells", id);

        Connection link = Jsoup.connect(URL + "spells/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по заклинанию. Попробуйте позже."));
        }

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

    public ArrayList<String> itemsGrabber(String id) {
        String article = knowledge.searchArticleId("items", id);

        Connection link = Jsoup.connect(URL + "items/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по предмету. Попробуйте позже."));
        }

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

    public ArrayList<String> bestiaryGrabber(String id) {
        String article = knowledge.searchArticleId("bestiary", id);

        Connection link = Jsoup.connect(URL + "bestiary/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по существу. Попробуйте позже."));
        }

        Elements check = page.select("div.cards-wrapper");
        Elements html = page.select("*");

        ArrayList<String> result = new ArrayList<>();

        if (check.textNodes().size() > 2) {
            html = page.select("div.card__group-classic");
            result.add("""
                    Классическая версия:
                    
                    """);
        }

        Elements name = html.select("h2.card-title[itemprop=name]");

        Elements li = html.select("ul.params.card__article-body > li:not(.subsection.desc)");
        Elements liDescBody = html.select("ul.params.card__article-body > li.subsection.desc")
                .select("h3.subsection-title,p,li:not(.subsection.desc),table");

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

        if (check.textNodes().size() > 2) {
            result.add("\n");

            html = page.select("div.card__group-multiverse");

            name = html.select("h2.card-title[itemprop=name]");

            li = html.select("ul.params.card__article-body > li:not(.subsection.desc)");
            liDescBody = html.select("ul.params.card__article-body > li.subsection.desc")
                    .select("h3.subsection-title,p,li:not(.subsection.desc),table");

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

    public ArrayList<String> racesGrabber(String id) {
        String article = knowledge.searchArticleId("race", id);

        Connection link = Jsoup.connect(URL + "race/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по расе. Попробуйте позже."));
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

        result.add("Информация взята с " + URL + "race/" + article);
        return result;
    }

    // Классы слишком длинные для чата, так что пока использую CLASSES_LIST в Constants
    // В будущем, если получится сделать какие-то короткие выдержки, то использую
    public ArrayList<String> ClassesGrabber(String id) {
        String article = knowledge.searchArticleId("class", id);

        Connection link = Jsoup.connect(URL + "class/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по классу. Попробуйте позже."));
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
        result.add("Информация взята с " + URL + "class/" + article);
        return result;
    }

    public ArrayList<String> featsGrabber(String id) {
        String article = knowledge.searchArticleId("feats", id);

        Connection link = Jsoup.connect(URL + "feats/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по черте. Попробуйте позже."));
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
        result.add("Информация взята с " + URL + "feats/" + article);
        return result;
    }

    public ArrayList<String> backgroundsGrabber(String id) {
        String article = knowledge.searchArticleId("backgrounds", id);

        Connection link = Jsoup.connect(URL + "backgrounds/" + article);
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по предыстории. Попробуйте позже."));
        }

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

    public ArrayList<String> toolGrabber(ItemsIdsDnD instrument) {
        String id = ItemsIdsDnD.getParserId(instrument);

        Connection link = Jsoup.connect(URL + "articles/inventory/100-tools/");
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по инструменту. Попробуйте позже."));
        }

        Elements contents = page.select(
                "h2.bigSectionTitle.hide-next.hide-next-h2:contains(" + id +
                ") + div.hide-wrapper");

        Elements paragraphs = contents.select("p");
        Elements table = contents.select("td");

        ArrayList<String> result = new ArrayList<>();
        for (Element i : paragraphs) {
            result.add(i.text());
        }
        result.add("Возможные действия:");
        StringBuilder line = new StringBuilder();
        int step = 0;
        for (Element i : table) {
            line.append(i.text()).append(" | ");
            step += 1;
            if (step == 2) {
                step = 0;
                line.setLength(line.length() - 3);
                result.add(line.toString());
                line.setLength(0);
            }
        }
        return result;
    }

    public ArrayList<String> mainFormulasGrabber() {
        Connection link = Jsoup.connect(URL + "articles/newbie/26-main-formulas/");
        Document page;
        try {
            do {
                page = link.get();
            } while (!page.hasText());
        } catch (IOException e) {
            return new ArrayList<>(List.of("Не удалось получить справку по главным формулам. Попробуйте позже."));
        }

        Elements name = page.select("h2.card-title[itemprop=name]");
        Elements body = page.select("div.desc.card__article-body p");

        ArrayList<String> result = new ArrayList<>();
        result.add(name.text());

        for (Element i : body) {
            result.add(i.text());
        }

        result.add("Информация взята с " + URL + "articles/newbie/26-main-formulas/");
        return result;
    }

    public void dictWriter(String section) {
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

    public void addressRefresher(String section) {
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

    public String addressWriter(ArrayList<String> entries, String section) {
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

    public static void main(String[] args) throws IOException {
        DataHandler knowledge = new DataHandler(true);
        SiteParseHandler parse = new SiteParseHandler(knowledge);


    }
}

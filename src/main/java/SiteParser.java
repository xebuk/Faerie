import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteParser {
    private static String url = "https://dnd.su/bestiary/";
    private HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
    private Parser parser = new Parser(treeBuilder);

    public static void main(String[] args) {
        String beast = System.console().readLine();
        Connection link = Jsoup.connect(url + beast);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(page.select("h2.card-title[itemprop=name]"));
        page.select("div.card__body.new-article").forEach(System.out::println);
    }
}

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteParser {
    private static String url = "https://dnd.su/bestiary/";

    public static void main(String[] args) {
        String beast = System.console().readLine();
        Connection link = Jsoup.connect(url + beast);
        Document page;
        try {
            page = link.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(page.title());
    }
}

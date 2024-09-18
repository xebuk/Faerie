import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TokenReader {
    public static String readToken() throws IOException {
        Path tokenFilePath = Path.of("../token_dir/token.txt");
        return Files.readString(tokenFilePath).trim();
    }

    protected static int readCreatorId() throws IOException {
        Path creatorID_FilePath = Path.of("../token_dir/creatorID.txt");
        return parseInt(Files.readString(creatorID_FilePath).trim());
    }

    protected static String searchArticleId(String section, String name) throws IOException {
        Path articleID_FilePath = Path.of("../token_dir/" + section + ".txt");
        List<String> lines;
        for (int i = 0; i < 300; i++) {
            lines = Files.readAllLines(articleID_FilePath);
            String[] sep;
            for (String articleId: lines) {
                sep = articleId.trim().split("~ ");
                if (sep[1].toLowerCase().contains(name.toLowerCase())) {
                    //System.out.println(sep[0]);
                    //System.out.println(sep[1]);
                    return sep[0];
                }
            }
        }
        return "1";
    }
}

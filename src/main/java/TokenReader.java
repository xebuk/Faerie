import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TokenReader {
    public static String readToken() throws IOException {
        Path tokenFilePath = Paths.get("../token_dir/token.txt");
        return Files.readString(tokenFilePath).trim();
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenReader {
    protected static String readToken() throws IOException {
        Path tokenFilePath = Path.of("../token_dir/token.txt");
        return Files.readString(tokenFilePath).trim();
    }
}

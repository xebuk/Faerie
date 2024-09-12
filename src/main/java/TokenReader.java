import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenReader {
    protected static String readToken() throws IOException {
        return Files.readString(Path.of("../token_dir/token.txt")).trim();
    }
}

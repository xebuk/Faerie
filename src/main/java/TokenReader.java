import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenReader {
    protected static String readToken() throws IOException {
        Path tokenFilePath = Path.of("../token_dir/token.txt");
        return Files.readString(tokenFilePath).trim();
    }

    protected static int readCreatorId() throws IOException {
        Path creatorID_FilePath = Path.of("../token_dir/creatorID.txt");
        return Integer.parseInt(Files.readString(creatorID_FilePath).trim());
    }
}

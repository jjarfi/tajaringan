package Baterei;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public interface SystemCommandHandler
{
    default String getCommandOutput (List<String> command) throws IOException
    {
        try (InputStream stream = new ProcessBuilder(command).start().getInputStream())
        {
            return new String(stream(), StandardCharsets.UTF_8);
        }
    }

    public byte[] stream();
}

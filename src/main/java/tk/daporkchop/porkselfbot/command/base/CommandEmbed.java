package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.yaml.snakeyaml.Yaml;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.YMLParser;

import java.awt.*;

/**
 * Created by daporkchop on 4/8/2017.
 */
public class CommandEmbed extends Command {
    public CommandEmbed() {
        super("embed");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            EmbedBuilder builder = new EmbedBuilder();

            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(8));

            builder.setTitle(yml.get("title.text", "error"), yml.get("title.url", "http://google.com"));

            evt.getMessage().editMessage(builder.build()).queue();
        } catch (PermissionException e) {
            if (e.getPermission().ordinal() == Permission.MESSAGE_EMBED_LINKS.ordinal())    {
                evt.getMessage().editMessage("*Missing permission to send embeds!*").queue();
            }
        }
    }

    @Override
    public String getUsage() {
        return "..embed";
    }

    @Override
    public String getUsageExample()	{
        return "..embed";
    }
}

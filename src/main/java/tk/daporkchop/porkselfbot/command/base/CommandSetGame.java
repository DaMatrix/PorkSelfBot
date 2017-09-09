package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.YMLParser;

public class CommandSetGame extends Command {
    public CommandSetGame() {
        super("setgame");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(10));

            String name = yml.get("name", PorkSelfBot.INSTANCE.jda.getPresence().getGame().getName());
            String url = yml.get("url", PorkSelfBot.INSTANCE.jda.getPresence().getGame().getUrl());

            PorkSelfBot.INSTANCE.setGame(name, url);

            PorkSelfBot.INSTANCE.config.set("game.name", name);
            PorkSelfBot.INSTANCE.config.set("game.url", url);

            evt.getMessage().editMessage("**Changed game to:**\nName: " + name + (url != null && !url.isEmpty() ? "\nURL: " + url : "")).queue();
        } catch (YAMLException e)   {
            evt.getMessage().editMessage("Bad YML formatting!").queue();
        }
    }

    @Override
    public String getUsage() {
        return ",,setgame";
    }

    @Override
    public String getUsageExample()	{
        return ",,setgame";
    }
}

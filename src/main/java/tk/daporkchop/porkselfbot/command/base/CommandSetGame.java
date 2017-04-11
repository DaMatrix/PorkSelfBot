package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.impl.GameImpl;
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

            String name = yml.get("name", "PorkSelfBot");
            String url = yml.get("url", "");
            boolean isStream = yml.getBoolean("stream", false);
            Game.GameType type = isStream ? Game.GameType.TWITCH : Game.GameType.DEFAULT;

            PorkSelfBot.INSTANCE.jda.getPresence().setGame(new GameImpl(name, url, type));

            evt.getMessage().editMessage("**Changed game to:**\nName: " + name + (url != null && !url.isEmpty() ? "\nURL: " + url : "") + "\nType: " + (isStream ? "TWITCH" : "DEFAULT")).queue();
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

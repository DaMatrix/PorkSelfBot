package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

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
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.BLUE);
        builder.setTitle("PorkSelfBot ping...", "http://www.daporkchop.tk/");

        builder.addField("**Ping:**", PorkSelfBot.INSTANCE.jda.getPing() + "ms", false);

        evt.getMessage().editMessage(builder.build()).queue();
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

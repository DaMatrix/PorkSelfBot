package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

public class CommandPing extends Command {

    public CommandPing() {
        super("ping");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.BLUE);

            builder.addField("**PorkSelfBot Ping:**", PorkSelfBot.INSTANCE.jda.getPing() + "ms", false);

            evt.getMessage().editMessage(builder.build()).queue();
        } catch (PermissionException e) {
            if (e.getPermission().ordinal() == Permission.MESSAGE_EMBED_LINKS.ordinal())    {
                //send as raw text
                evt.getMessage().editMessage("**PorkSelfBot Ping:**\n\n`" + PorkSelfBot.INSTANCE.jda.getPing() + "ms`").queue();
            }
        }
    }

    @Override
    public String getUsage() {
        return ",,ping";
    }

    @Override
    public String getUsageExample()	{
        return ",,ping";
    }
}
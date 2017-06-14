package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;
import java.util.List;

public class CommandEnableSpammer extends Command {
    public static final EmbedBuilder message;

    static {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Toggled spammer!", null);
        message = builder;
    }

    public CommandEnableSpammer() {
        super("spam");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        List<String> temp = PorkSelfBot.INSTANCE.spamChannels;
        if (temp.contains(evt.getChannel().getId())) {
            temp.remove(evt.getChannel().getId());
        } else {
            temp.add(evt.getChannel().getId());
        }
        PorkSelfBot.INSTANCE.config.set("spamchannels", temp);
        PorkSelfBot.INSTANCE.spamChannels = temp;
        evt.getMessage().editMessage(CommandEnableSpammer.message.build()).queue();
    }

    @Override
    public String getUsage() {
        return ",,spam";
    }

    @Override
    public String getUsageExample() {
        return ",,spam";
    }
}

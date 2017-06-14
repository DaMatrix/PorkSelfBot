package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

public class CommandSave extends Command {

    public static final EmbedBuilder message;

    static {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Saved config!", null);
        message = builder;
    }
    public CommandSave() {
        super("save");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        PorkSelfBot.INSTANCE.config.set("spamchannels", PorkSelfBot.INSTANCE.spamChannels);
        PorkSelfBot.INSTANCE.config.save();
        evt.getMessage().editMessage(CommandSave.message.build()).queue();
    }

    @Override
    public String getUsage() {
        return ",,save";
    }

    @Override
    public String getUsageExample() {
        return ",,save";
    }
}

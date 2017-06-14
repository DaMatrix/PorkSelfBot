package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

public class CommandSave extends Command {
    public CommandSave() {
        super("save");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        PorkSelfBot.INSTANCE.config.set("spamchannels", PorkSelfBot.INSTANCE.spamChannels);
        PorkSelfBot.INSTANCE.config.save();
        evt.getMessage().editMessage("Saved config!").queue();
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

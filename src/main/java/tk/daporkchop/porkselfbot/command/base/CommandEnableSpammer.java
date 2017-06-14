package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.util.List;

public class CommandEnableSpammer extends Command {
    public CommandEnableSpammer() {
        super("spam");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        List<String> temp = PorkSelfBot.INSTANCE.config.getStringList("spamchannels");
        if (temp.contains(evt.getChannel().getId())) {
            temp.remove(evt.getChannel().getId());
        } else {
            temp.add(evt.getChannel().getId());
        }
        PorkSelfBot.INSTANCE.config.set("spamchannels", temp);
        evt.getMessage().editMessage("Toggled spammer").queue();
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

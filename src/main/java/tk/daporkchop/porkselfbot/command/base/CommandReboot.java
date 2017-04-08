package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

/**
 * Created by joeyr on 4/8/2017.
 */
public class CommandReboot extends Command {
    public CommandReboot()  {
        super("reboot");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        evt.getMessage().editMessage("PorkSelfBot rebooting...").queue();
    }

    @Override
    public String getUsage() {
        return ",,reboot";
    }

    @Override
    public String getUsageExample()	{
        return ",,reboot";
    }
}

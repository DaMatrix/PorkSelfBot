package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.command.Command;

public class CommandPOST extends Command {
    public CommandPOST() {
        super("post");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {

    }

    @Override
    public String getUsage() {
        return ",,post";
    }

    @Override
    public String getUsageExample()	{
        return ",,post";
    }
}

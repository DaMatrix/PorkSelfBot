package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

public class CommandReboot extends Command {
    public CommandReboot()  {
        super("reboot");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        evt.getMessage().editMessage("PorkSelfBot rebooting...").queue();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e)    {
            //shut up java
        }
        PorkSelfBot.INSTANCE.jda.shutdown();
        System.exit(0);
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

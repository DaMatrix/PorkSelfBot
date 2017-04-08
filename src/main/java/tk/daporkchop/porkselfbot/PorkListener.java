package tk.daporkchop.porkselfbot;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tk.daporkchop.porkselfbot.command.CommandRegistry;

/**
 * Created by daporkchop on 05.03.17.
 */
public class PorkListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            //bots don't matter to us!
            return;
        }
        String message = event.getMessage().getRawContent();

        if (message.startsWith(".."))    {
            CommandRegistry.runCommand(event, message);
        } else if (event.getChannelType().ordinal() == ChannelType.PRIVATE.ordinal()) {
            if (event.getAuthor().getId().equals("226975061880471552"))   {
                switch (message)    {
                    case ",,instareboot":
                        PorkBot.INSTANCE.jda.shutdown();
                        System.exit(0);
                        break;
                }
            }
        }
    }
}

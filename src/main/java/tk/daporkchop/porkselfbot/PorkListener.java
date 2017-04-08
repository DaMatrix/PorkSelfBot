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
        if (!event.getAuthor().getId().equals(PorkSelfBot.INSTANCE.jda.getSelfUser().getId())) {
            //only self user execute commands
            return;
        }
        String message = event.getMessage().getRawContent();

        if (message.startsWith(",,"))    {
            CommandRegistry.runCommand(event, message);
        }
    }
}

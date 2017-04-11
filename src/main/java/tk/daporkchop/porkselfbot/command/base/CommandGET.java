package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.HTTPUtils;

import java.io.IOException;

public class CommandGET extends Command {
    public CommandGET() {
        super("get");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            UrlValidator validator = new UrlValidator();
            if (validator.isValid(args[1])) {
                String data = HTTPUtils.performGetRequest(HTTPUtils.constantURL(args[1]));

                evt.getMessage().editMessage("GET result from `" + args[1] + "`:\n\n```html\n" + data + "\n```").queue();
            } else {
                evt.getMessage().editMessage("Invalid URL: `" + args[1] + "`").queue();
            }
        } catch (IndexOutOfBoundsException e)   {
            this.sendErrorMessage(evt, "Not enough arguments!");
        } catch (IOException e) {
            this.sendErrorMessage(evt, "IOException lol");
        }
    }

    @Override
    public String getUsage() {
        return ",,get <address>";
    }

    @Override
    public String getUsageExample()	{
        return ",,get http://www.daporkchop.tk/";
    }
}

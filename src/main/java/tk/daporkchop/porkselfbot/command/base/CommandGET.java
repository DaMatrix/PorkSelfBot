package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.HTTPUtils;
import tk.daporkchop.porkselfbot.util.YMLParser;

import java.io.IOException;

public class CommandGET extends Command {
    public CommandGET() {
        super("get");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] a, String message) {
        try {
            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(6));
            String url = yml.get("url", null);

            if (url == null)    {
                throw new IndexOutOfBoundsException();
                //sends the error message
            }

            UrlValidator validator = new UrlValidator();
            if (validator.isValid(url)) {
                String data = HTTPUtils.performGetRequest(HTTPUtils.constantURL(url));

                String toSend = "GET result from `" + url + "`:\n\n```html\n" + data + "\n```";

                if (toSend.length() < 2000) {
                    evt.getMessage().editMessage(toSend).queue();
                } else {
                    evt.getMessage().editMessage("*Output too long!*").queue();
                }
            } else {
                evt.getMessage().editMessage("Invalid URL: `" + url + "`").queue();
            }
        } catch (IndexOutOfBoundsException e)   {
            this.sendErrorMessage(evt, "Not enough arguments!");
        } catch (IOException e) {
            this.sendErrorMessage(evt, "IOException lol");
        } catch (YAMLException e)   {
            evt.getMessage().editMessage("Bad YML formatting!").queue();
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

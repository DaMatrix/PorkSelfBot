package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.HTTPUtils;
import tk.daporkchop.porkselfbot.util.YMLParser;

import java.io.IOException;
import java.net.URL;

public class CommandPOST extends Command {
    public CommandPOST() {
        super("post");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(7));
            String url = yml.get("url", null);
            String content = yml.get("content", null);

            if (url == null)    {
                throw new IndexOutOfBoundsException();
                //sends the error message
            }

            boolean doAuth = yml.getBoolean("doAuth", false);
            String contentType = yml.get("contentType", "text/plain");
            String authKey = yml.get("authKey", null);

            UrlValidator validator = new UrlValidator();
            if (validator.isValid(url)) {
                URL urI = HTTPUtils.constantURL(url);

                if (doAuth) {
                    if (authKey == null)    {
                        evt.getMessage().editMessage("Auth key not given!").queue();
                        return;
                    }

                    String data = HTTPUtils.performPostRequestWithAuth(urI, content, contentType, authKey);

                    String toSend = "POST result from `" + url + "` (with auth):\n\n```html\n" + data + "\n```";

                    if (toSend.length() < 2000) {
                        evt.getMessage().editMessage(toSend).queue();
                    } else {
                        evt.getMessage().editMessage("*Output too long!*").queue();
                    }
                } else {
                    String data = HTTPUtils.performPostRequest(urI, content, contentType);

                    String toSend = "POST result from `" + url + "`:\n\n```html\n" + data + "\n```";

                    if (toSend.length() < 2000) {
                        evt.getMessage().editMessage(toSend).queue();
                    } else {
                        evt.getMessage().editMessage("*Output too long!*").queue();
                    }
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
        return ",,post";
    }

    @Override
    public String getUsageExample()	{
        return ",,post";
    }
}

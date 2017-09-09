/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

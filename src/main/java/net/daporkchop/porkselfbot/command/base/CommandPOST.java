/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.porkselfbot.command.base;

import net.daporkchop.porkselfbot.command.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;
import org.yaml.snakeyaml.error.YAMLException;
import net.daporkchop.porkselfbot.util.HTTPUtils;
import net.daporkchop.porkselfbot.util.YMLParser;

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

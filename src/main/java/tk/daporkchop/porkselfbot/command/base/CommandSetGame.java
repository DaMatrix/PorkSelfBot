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
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.YMLParser;

public class CommandSetGame extends Command {
    public CommandSetGame() {
        super("setgame");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(10));

            String name = yml.get("name", PorkSelfBot.INSTANCE.jda.getPresence().getGame().getName());
            String url = yml.get("url", PorkSelfBot.INSTANCE.jda.getPresence().getGame().getUrl());

            PorkSelfBot.INSTANCE.setGame(name, url);

            PorkSelfBot.INSTANCE.config.set("game.name", name);
            PorkSelfBot.INSTANCE.config.set("game.url", url);

            evt.getMessage().editMessage("**Changed game to:**\nName: " + name + (url != null && !url.isEmpty() ? "\nURL: " + url : "")).queue();
        } catch (YAMLException e)   {
            evt.getMessage().editMessage("Bad YML formatting!").queue();
        }
    }

    @Override
    public String getUsage() {
        return ",,setgame";
    }

    @Override
    public String getUsageExample()	{
        return ",,setgame";
    }
}

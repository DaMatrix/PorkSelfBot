/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

public class CommandPing extends Command {

    public CommandPing() {
        super("ping");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.BLUE);

            builder.addField("**PorkSelfBot Ping:**", PorkSelfBot.INSTANCE.jda.getPing() + "ms", false);

            evt.getMessage().editMessage(builder.build()).queue();
        } catch (PermissionException e) {
            if (e.getPermission().ordinal() == Permission.MESSAGE_EMBED_LINKS.ordinal())    {
                //send as raw text
                evt.getMessage().editMessage("**PorkSelfBot Ping:**\n\n`" + PorkSelfBot.INSTANCE.jda.getPing() + "ms`").queue();
            }
        }
    }

    @Override
    public String getUsage() {
        return ",,ping";
    }

    @Override
    public String getUsageExample()	{
        return ",,ping";
    }
}
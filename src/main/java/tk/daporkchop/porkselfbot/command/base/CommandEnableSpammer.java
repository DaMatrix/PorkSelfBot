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
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;
import java.util.List;

public class CommandEnableSpammer extends Command {
    public static final EmbedBuilder message;

    static {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Toggled spammer!", null);
        message = builder;
    }

    public CommandEnableSpammer() {
        super("spam");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        List<String> temp = PorkSelfBot.INSTANCE.spamChannels;
        if (temp.contains(evt.getChannel().getId())) {
            temp.remove(evt.getChannel().getId());
        } else {
            temp.add(evt.getChannel().getId());
        }
        PorkSelfBot.INSTANCE.config.set("spamchannels", temp);
        PorkSelfBot.INSTANCE.spamChannels = temp;
        evt.getMessage().editMessage(CommandEnableSpammer.message.build()).queue();
    }

    @Override
    public String getUsage() {
        return ",,spam";
    }

    @Override
    public String getUsageExample() {
        return ",,spam";
    }
}

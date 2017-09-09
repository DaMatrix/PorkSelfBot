/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.porkselfbot.command.base;

import net.daporkchop.porkselfbot.PorkSelfBot;
import net.daporkchop.porkselfbot.command.Command;
import net.daporkchop.porkselfbot.util.YMLParser;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandMassDM extends Command {

    public CommandMassDM() {
        super("massdm");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        YMLParser yml = new YMLParser();
        yml.loadRaw(message.substring(7));
        String toSend = yml.getString("msg", null);
        if (toSend == null) {
            evt.getMessage().editMessage("No message!").queue();
            return;
        }

        int type = yml.getInt("type", 0);
        List<User> users = new ArrayList<>();
        switch (type) {
            case 0:
                for (Member member : evt.getTextChannel().getMembers()) {
                    users.add(member.getUser());
                }
                break;
            case 1:
                for (Member member : evt.getGuild().getMembers()) {
                    users.add(member.getUser());
                }
                break;
            case 2:
                List<Long> ids = yml.getLongList("ids");
                for (long l : ids) {
                    users.add(PorkSelfBot.INSTANCE.jda.getUserById(l));
                }
                break;
        }
        evt.getMessage().editMessage("Sending message to " + users.size() + " users...").complete();
        for (User user : users) {
            PrivateChannel channel = user.openPrivateChannel().complete();
            channel.sendMessage(toSend).complete();
        }
        evt.getMessage().editMessage("Sent to " + users.size() + " users!").queue();
    }

    @Override
    public String getUsage() {
        return ",,massdm";
    }

    @Override
    public String getUsageExample() {
        return ",,massdm";
    }
}
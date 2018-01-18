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
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandGenInvite extends Command {

    public CommandGenInvite() {
        super("geninvite");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        if (args.length < 2) {
            evt.getMessage().editMessage("Server ID needed!").queue();
        }
        long id = Long.parseLong(args[1]);
        Guild guild = PorkSelfBot.INSTANCE.jda.getGuildById(id);
        if (guild == null) {
            evt.getMessage().editMessage("No such guild!");
        } else {
            Invite invite = guild.getDefaultChannel().createInvite().setMaxUses(1).setTemporary(false).complete();
            evt.getMessage().editMessage("Created invite for guild: " + invite.getURL()).queue();
        }
    }

    @Override
    public String getUsage() {
        return ",,geninvite";
    }

    @Override
    public String getUsageExample() {
        return ",,geninvite";
    }
}
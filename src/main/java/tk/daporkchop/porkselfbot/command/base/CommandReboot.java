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
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;

import java.awt.*;

public class CommandReboot extends Command {
    public CommandReboot()  {
        super("reboot");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        evt.getMessage().editMessage("PorkSelfBot rebooting...").queue();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e)    {
            //shut up java
        }
        PorkSelfBot.INSTANCE.jda.shutdown();
        System.exit(0);
    }

    @Override
    public String getUsage() {
        return ",,reboot";
    }

    @Override
    public String getUsageExample()	{
        return ",,reboot";
    }
}

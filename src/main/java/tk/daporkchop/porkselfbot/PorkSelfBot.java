/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tk.daporkchop.porkselfbot;

import com.google.common.cache.CacheBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import tk.daporkchop.porkselfbot.command.CommandRegistry;
import tk.daporkchop.porkselfbot.command.base.*;
import tk.daporkchop.porkselfbot.util.YMLParser;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by daporkchop on 05.03.17.
 */
public class PorkSelfBot {

    public static PorkSelfBot INSTANCE;
    public static Logger logger;

    public YMLParser config;
    public JDA jda;
    public List<String> spamChannels;

    /**
     * The bot's main cache!
     */
    public ConcurrentMap<Object, Object> botCache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build().asMap();

    public PorkSelfBot()    {
        logger.info("Starting PorkSelfBot...");
        try {
            jda = new JDABuilder(AccountType.CLIENT)
                    .setToken(getToken())
                    .addEventListener(new PorkListener())
                    .buildBlocking();
        } catch (LoginException e)  {
            e.printStackTrace();
            System.exit(0);
        } catch (InterruptedException e)    {
            e.printStackTrace();
            System.exit(0);
        } catch (RateLimitedException e)    {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args)  {
        logger = Logger.getLogger("PorkSelfBot");
        INSTANCE = new PorkSelfBot();
        INSTANCE.start();
    }

    public static String getToken() {
        File f = new File(System.getProperty("user.dir") + "/discordtoken.txt");
        String token = "";

        if (!f.exists()) {
            try {
                PrintWriter writer = new PrintWriter(f.getAbsolutePath(), "UTF-8");
                Scanner s = new Scanner(System.in);

                logger.info("Please enter your discord bot token");
                token = s.nextLine();
                writer.println(token);
                logger.info("Successful. Starting...");

                s.close();
                writer.close();
            } catch (FileNotFoundException e) {
                logger.severe("impossible error kek");
                e.printStackTrace();
                System.exit(0);
            } catch (UnsupportedEncodingException e) {
                logger.severe("File encoding not supported!");
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            try {
                Scanner s = new Scanner(f);

                token = s.nextLine();

                s.close();
            } catch (FileNotFoundException e) {
                logger.severe("impossible error kek");
                e.printStackTrace();
                System.exit(0);
            }
        }

        return token.trim();
    }

    public void start() {
        config = new YMLParser("config.yml", YMLParser.YAML);
        spamChannels = config.getStringList("spamchannels");

        jda.getPresence().setStatus(OnlineStatus.IDLE);
        setGame(config.getString("game.name", "PorkSelfBot"), config.getString("game.url"));

        CommandRegistry.registerCommand(new CommandPing());
        CommandRegistry.registerCommand(new CommandReboot());
        CommandRegistry.registerCommand(new CommandEmbed());
        CommandRegistry.registerCommand(new CommandSetGame());
        CommandRegistry.registerCommand(new CommandSetStatus());
        CommandRegistry.registerCommand(new CommandGET());
        CommandRegistry.registerCommand(new CommandPOST());
        CommandRegistry.registerCommand(new CommandSave());
        CommandRegistry.registerCommand(new CommandEnableSpammer());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (String channelID : spamChannels) {
                    TextChannel channel = jda.getTextChannelById(channelID);
                    channel.sendMessage(UUID.randomUUID().toString()).queue();
                }
            }
        }, 0, 60000);
    }

    public void sendMessage(String msg, MessageReceivedEvent event) {

    }

    public void setGame(String name, String url) {
        jda.getPresence().setGame(url == null ? Game.of(name) : Game.of(name, url));
    }
}

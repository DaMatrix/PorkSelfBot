package tk.daporkchop.porkselfbot;

import com.google.common.cache.CacheBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.impl.GameImpl;
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
                    .addListener(new PorkListener())
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
        jda.getPresence().setGame(new GameImpl(config.getString("game.name", "PorkSelfBot"), config.getString("game.url", ""), config.getBoolean("game.stream", false) ? Game.GameType.TWITCH : Game.GameType.DEFAULT));

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
}

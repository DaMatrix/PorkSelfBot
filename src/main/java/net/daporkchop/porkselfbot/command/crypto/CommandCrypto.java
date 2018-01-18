package net.daporkchop.porkselfbot.command.crypto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.daporkchop.porkselfbot.command.Command;
import net.daporkchop.porkselfbot.util.HTTPUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;

public class CommandCrypto extends Command {
    private static JsonParser parser = new JsonParser();

    public CommandCrypto() {
        super("crypto");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] split, String rawContent) {
        if (split.length == 2) {
            try {
                String symbol = split[1].toUpperCase();
                if (!symbol.endsWith("BTC")) {
                    symbol += "BTC";
                }
                if (symbol.startsWith("BTC")) {
                    symbol = "BTCUSDT";
                }
                String urlSymbol = split[1].toUpperCase();
                if (!urlSymbol.endsWith("BTC")) {
                    urlSymbol += "_BTC";
                }
                if (urlSymbol.startsWith("BTC")) {
                    urlSymbol = "BTC_USDT";
                }
                String mainJson = HTTPUtils.performGetRequest(HTTPUtils.constantURL("https://api.binance.com/api/v1/ticker/24hr?symbol=" + symbol));
                JsonObject element = parser.parse(mainJson).getAsJsonObject();
                EmbedBuilder builder = new EmbedBuilder();
                builder.setFooter("Provided by Binance", "https://www.cryptocompare.com/media/1383947/bnb.png");
                if (element.has("msg")) {
                    builder.setColor(Color.RED);
                    builder.setTitle("Unable to find currency pair: " + urlSymbol);
                } else {
                    builder.setColor(Color.YELLOW);
                    builder.setTitle(urlSymbol, "https://www.binance.com/tradeDetail.html?symbol=" + urlSymbol);
                    builder.addField("Current price", element.get("askPrice").getAsString() + (symbol.endsWith("BTC") ? " BTC" : " USD"), true);
                    builder.addField("High", element.get("highPrice").getAsString() + (symbol.endsWith("BTC") ? " BTC" : " USD"), true);
                    builder.addField("Low", element.get("lowPrice").getAsString() + (symbol.endsWith("BTC") ? " BTC" : " USD"), false);
                    builder.addField("24-hour change", element.get("priceChange").getAsString() + (symbol.endsWith("BTC") ? " BTC" : " USD"), true);
                    builder.addField("", element.get("priceChangePercent").getAsString() + "%", true);
                    builder.addField("Volume", element.get("quoteVolume").getAsString() + (symbol.endsWith("BTC") ? " BTC" : " USD"), true);
                }
                evt.getMessage().editMessage(builder.build()).queue();
            } catch (IOException e) {
                evt.getMessage().editMessage("IOException").queue();
            }
        } else {
            evt.getMessage().editMessage("No arguments!").queue();
        }
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getUsageExample() {
        return null;
    }
}

package com.bot.message.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.bot.message.support.LineMessagingClientSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.net.URI;

import static com.bot.message.support.LineMessagingClientSupport.createUri;

@Slf4j
@LineMessageHandler
public class LineMessageHandlerController {

    @Value("${app.message.host}")
    protected String messageAppHost;

    @Autowired
    protected LineMessagingClient lineMessagingClient;

    public LineMessageHandlerController() {
        super();
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
        final Source source = event.getSource();
        final String text = content.getText();

        Log.info("Event Source {}", source);
        String senderId = source.getSenderId();
        String userId = source.getUserId();

        Log.info("senderId >> {}", senderId);
        Log.info("userId >> {}", userId);

        Log.info("Got text message from replyToken:{} text:{} emojis:{}", replyToken, text, content.getEmojis());

        // 純文字回覆
        if ("testa".equals(text)) {

            // 圖卡回覆
            String botURI1 = "https://www.cwb.gov.tw/V8/C/?openExternalBrowser=1";
            Action uriActionToBot1 = new URIAction("氣象局", URI.create(botURI1), null);
            CarouselColumn carouselColumnToBot1 = new CarouselColumn(createUri(messageAppHost, "/static/images/line008.png"), "氣象局", "氣象局描述", List.of(uriActionToBot1));


            String botURI2 = "https://tw.appledaily.com/home/?openExternalBrowser=1";
            Action uriActionToBot2 = new URIAction("蘋果日報", URI.create(botURI2), null);
            CarouselColumn carouselColumnToBo2 = new CarouselColumn(createUri(messageAppHost, "/static/images/line008.png"), "蘋果日報", "蘋果日報描述", List.of(uriActionToBot2));

            // Template合起來
            CarouselTemplate carouselTemplate = new CarouselTemplate(List.of(carouselColumnToBot1, carouselColumnToBo2));
            TemplateMessage templateMessage = new TemplateMessage("Template描述", carouselTemplate);

            LineMessagingClientSupport.replay(lineMessagingClient, replyToken,
                    List.of(templateMessage), true);
        } else {
            LineMessagingClientSupport.replyText(lineMessagingClient, replyToken, "你好,我是機器人:" + text);
        }

    }

    @EventMapping
    public void handleUnfollowEvent(UnfollowEvent event) {
        Log.info("unfollowed this bot: {}", event);
    }

    @EventMapping
    public void handleUnknownEvent(UnknownEvent event) {
        Log.info("Got an unknown event!!!!! : {}", event);
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        Log.info("followed this bit: {}", event);
    }

}
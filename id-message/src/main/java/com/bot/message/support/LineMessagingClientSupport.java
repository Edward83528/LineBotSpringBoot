package com.bot.message.support;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
public final class LineMessagingClientSupport {

    public static URI createUri(String host, String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .host(host)
                .path(path)
                .build()
                .toUri();
    }

    public static URI createUri(String host, String path, String parameter, String value) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .host(host)
                .path(path)
                .queryParam(parameter, value)
                .build()
                .toUri();
    }

    // 純文字回覆
    public static void replyText(LineMessagingClient lineMessagingClient,
                                 String replyToken, String messageToUser) {
        TextMessage textMessage = new TextMessage(messageToUser);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        try {
            lineMessagingClient.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // 圖卡回復
    public static void replay(LineMessagingClient lineMessagingClient, @NonNull String replyToken,
                              @NonNull List<Message> messages, boolean notificationDisabled) throws RuntimeException {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            Log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            Log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static String displayName(LineMessagingClient lineMessagingClient, @NonNull String userId) {
        try {
            UserProfileResponse userProfileResponse = lineMessagingClient
                    .getProfile(userId)
                    .get();
            return userProfileResponse.getDisplayName();
        } catch (InterruptedException | ExecutionException e) {
            Log.error("", e);
            return "";
        }
    }


    public static void multicastMessage(LineMessagingClient lineMessagingClient,
                                        @NonNull Set<String> laineIds,
                                        @NonNull List<Message> messages) throws RuntimeException {
        try {

            BotApiResponse apiResponse = lineMessagingClient
                    .multicast(new Multicast(laineIds, messages))
                    .get();
            Log.info("Sent multicastMessage: {}", apiResponse);

        } catch (InterruptedException | ExecutionException e) {
            Log.error("", e);
            throw new RuntimeException(e);
        }
    }

    private LineMessagingClientSupport() {
        super();
    }

}
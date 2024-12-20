import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatGptService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "chat gpt api key"; // I have my own api key, but for security purpose I have replaced with dummy value

    public String getNextTask(String completedTask) throws IOException, InterruptedException {
        String nextTask = "OpenAI does not provide a \"completely free\" API for GPT completions; their APIs, including for GPT-4 and GPT-3.5, operate on a pay-as-you-go model.";
        try {
            OkHttpClient client = new OkHttpClient();

            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are an assistant that helps users plan their tasks.");

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "I just completed the task: \"" + completedTask + "\". Suggest the next logical task.");

            JSONArray messages = new JSONArray();
            messages.put(systemMessage);
            messages.put(userMessage);

            JSONObject json = new JSONObject();
            json.put("model", "gpt-4o-mini-2024-07-18");
            json.put("messages", messages);
            json.put("temperature", 0.7);

            RequestBody body = RequestBody.create(
                    json.toString(), MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            int retryCount = 0;
            while (retryCount < 3) {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject responseBody = new JSONObject(response.body().string());
                        nextTask = responseBody.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content")
                                .trim();
                    } else if (response.code() == 429) {
                        TimeUnit.SECONDS.sleep(1);
                        retryCount++;
                    } else {
                        throw new IOException("Error: " + response.code() + " - " + response.message());
                    }
                }
            }
            throw new IOException("Rate limit exceeded after retries.");
        } catch (Exception e) {
            System.err.println("Exception occurred at ChatGptService: " + e.getMessage());
        }

        return nextTask;
    }
}

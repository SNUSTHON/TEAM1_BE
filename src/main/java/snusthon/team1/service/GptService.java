package snusthon.team1.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GptService {

    private final OpenAiService openAiService;

    public GptService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public List<String> generateRelatedTopics(String content) {
        String systemPrompt = "당신은 주어진 주제에 대해 생각을 확장하는 데 도움을 주는 AI 어시스턴트입니다. " +
                "주어진 주제와 관련하여 다음 세 가지 유형의 의견을 생성해야 합니다:\n" +
                "1. 비슷한 의견 2개\n2. 중립적인 의견 1개\n3. 반대되는 의견 2개\n\n" +
                "각 의견은 간결한 한 문장으로 제시하되, 원본 주제의 어투와 최대한 비슷하게 유지해야 합니다. " +
                "또한, 사용자의 생각 확장에 도움이 될 만한 내용을 자유롭게 추가할 수 있습니다.\n\n" +
                "예시:\n" +
                "원본 주제: \"무한대와 관련된 해커톤 아이디어\"\n" +
                "비슷한 의견:\n" +
                "- \"변하지 않는 것과 관련된 해커톤 아이디어\"\n" +
                "- \"영원함을 주제로 한 해커톤 프로젝트\"\n" +
                "중립적인 의견:\n" +
                "- \"지속 가능성에 초점을 맞춘 해커톤 concept\"\n" +
                "반대되는 의견:\n" +
                "- \"유한한 것에만 관련된 해커톤 아이디어\"\n" +
                "- \"한정적 자원을 활용한 해커톤 주제\"\n\n" +
                "다른 예시:\n" +
                "원본 주제: \"기술 발전은 인간의 삶의 질을 향상시킨다.\"\n" +
                "비슷한 의견:\n" +
                "- \"과학 기술의 진보는 인간의 일상 생활을 더 편리하게 만든다.\"\n" +
                "- \"의료 기술의 발전은 수명을 연장하고 건강한 삶을 유지하는 데 기여한다.\"\n" +
                "중립적인 의견:\n" +
                "- \"기술 발전은 긍정적인 측면과 부정적인 측면을 모두 가지고 있다.\"\n" +
                "반대되는 의견:\n" +
                "- \"기술 발전은 인간의 일자리 감소와 소외를 초래한다.\"\n" +
                "- \"과도한 기술 의존은 인간관계의 질을 떨어뜨리고 사회적 소외를 심화시킨다.\"\n\n" +
                "이제 주어진 주제에 대해 위의 지침을 따라 의견을 생성해 주세요.";

        String userPrompt = String.format("다음 주제에 대해 세 가지 유형의 의견을 생성해 주세요: '%s'", content);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", systemPrompt),
                        new ChatMessage("user", userPrompt)
                ))
                .maxTokens(300)
                .n(1)
                .temperature(0.8)
                .build();

        String response = openAiService.createChatCompletion(chatCompletionRequest)
                .getChoices().get(0).getMessage().getContent();

        return parseResponse(response);
    }

    private List<String> parseResponse(String response) {
        List<String> opinions = new ArrayList<>();
        String[] lines = response.split("\n");
        for (String line : lines) {
            if (line.matches("[-•]\\s.*")) {
                opinions.add(line.replaceFirst("[-•]\\s*", "").trim());
            }
        }
        return opinions;
    }
}
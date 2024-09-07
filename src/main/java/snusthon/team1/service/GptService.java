package snusthon.team1.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GptService {

    public List<String> generateRelatedTopics(String content) {
        return List.of(
                content + "랑 유사한 의견",
                content + "랑 중립적인 의견",
                content + "랑 반대되는 의견"
        );
    }
}
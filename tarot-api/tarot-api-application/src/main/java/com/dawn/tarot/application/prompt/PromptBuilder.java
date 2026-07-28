package com.dawn.tarot.application.prompt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dawn.tarot.domain.model.DrawnCard;
import com.dawn.tarot.domain.model.Spread;
import com.dawn.tarot.domain.model.SpreadPosition;

/**
 * 两次 LLM 调用的 Prompt 构造。
 */
@Component
public class PromptBuilder {

    /** 选牌型:系统提示,约束输出 JSON */
    public String spreadSystemPrompt(List<Spread> spreads) {
        String catalog = spreads.stream()
                .map(s -> String.format("- code=%s, 名称=%s, 张数=%d, 适用=%s",
                        s.getCode(), s.getName(), s.getCardCount(), s.getDescription()))
                .collect(Collectors.joining("\n"));
        return """
                你是一位专业的塔罗牌占卜师。请根据用户的问题,从下列可选牌型中挑选最合适的一种。
                可选牌型:
                %s

                要求:
                1. 只能从上面的 code 中选择一个。
                2. 严格只返回一个 JSON 对象,不要包含任何解释、markdown 代码块标记或多余文字。
                3. JSON 格式为: {"spreadCode": "选中的code", "reason": "简短的选择理由"}
                """.formatted(catalog);
    }

    public String spreadUserPrompt(String question) {
        return "用户的问题是:" + question;
    }

    /** 解读:系统提示 */
    public String interpretSystemPrompt() {
        return """
                你是一位经验丰富的塔罗占卜师。请根据用户的问题、牌型和每个位置的牌面(含正逆位)进行解读。
                风格要求:
                1. 语气平和真诚,像一位值得信赖的朋友在认真交谈。
                2. 不要使用华丽辞藻、比喻或排比等修辞手法,不要有动作描述，用平实的话把意思说清楚。
                3. 先说整体印象(2-3句),再逐位置解读,最后给出实际可行的建议。
                4. 每个位置的解读控制在3-4句话,总体不超过500字。
                5. 直接输出正文,不要使用 markdown 标题或编号列表。
                """;
    }

    public String interpretUserPrompt(String question, Spread spread, List<DrawnCard> drawnCards) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户的问题:").append(question).append("\n");
        sb.append("使用的牌型:").append(spread.getName())
          .append("(").append(spread.getDescription()).append(")\n");
        sb.append("各位置抽到的牌:\n");
        List<SpreadPosition> positions = spread.getPositions();
        for (DrawnCard card : drawnCards) {
            String label = positions.stream()
                    .filter(p -> p.getIndex().equals(card.getPositionIndex()))
                    .map(SpreadPosition::getLabel)
                    .findFirst().orElse("位置" + card.getPositionIndex());
            sb.append(String.format("- 【%s】%s(%s)\n",
                    label, card.getName(), card.isReversed() ? "逆位" : "正位"));
        }
        return sb.toString();
    }
}

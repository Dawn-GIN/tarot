package com.dawn.tarot.domain.llm;

import java.util.function.Consumer;

/**
 * LLM 调用端口。基础设施层用通义千问实现。
 */
public interface LlmClient {

    /** 普通(阻塞)调用,返回完整文本 */
    String complete(String systemPrompt, String userPrompt);

    /**
     * 流式调用,每收到一个增量文本片段回调一次。
     *
     * @param onChunk 增量文本回调
     * @param onError 出错回调
     * @param onComplete 完成回调
     */
    void stream(String systemPrompt, String userPrompt,
                Consumer<String> onChunk,
                Consumer<Throwable> onError,
                Runnable onComplete);
}

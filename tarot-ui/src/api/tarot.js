// 后端占卜接口封装。开发环境经 vite proxy 转发 /api 到 localhost:8080。

async function postJson(url, body) {
  const resp = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });
  const json = await resp.json();
  if (json.code !== '0000') {
    throw new Error(json.message || '请求失败');
  }
  return json.data;
}

/** 开始占卜:返回 { sessionId, spread } */
export function startDivination(question) {
  return postJson('/api/tarot/start', { question });
}

/** 抽一张牌:返回 { card, drawnCount, totalCount, complete } */
export function drawCard(sessionId) {
  return postJson('/api/tarot/draw', { sessionId });
}

/** 查询单张牌详情(含正逆位含义) */
export async function getCard(id) {
  const resp = await fetch(`/api/tarot/cards/${id}`);
  const json = await resp.json();
  if (json.code !== '0000') {
    throw new Error(json.message || '查询卡牌失败');
  }
  return json.data;
}

/**
 * 流式解读。通过 EventSource 接收 SSE。
 * @returns 取消函数,调用可提前关闭连接
 */
export function interpretStream(sessionId, { onChunk, onDone, onError }) {
  const source = new EventSource(
    `/api/tarot/interpret?sessionId=${encodeURIComponent(sessionId)}`
  );

  source.addEventListener('message', (e) => {
    onChunk?.(e.data);
  });

  source.addEventListener('done', () => {
    source.close();
    onDone?.();
  });

  source.addEventListener('error', (e) => {
    source.close();
    onError?.(e);
  });

  return () => source.close();
}

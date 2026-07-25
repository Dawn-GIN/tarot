# 项目概述：
LLM作为占卜师，为用户提供塔罗牌占卜实时互动

# 用户角色：
需要塔罗牌占卜服务的普通用户

# 核心用户流程：
## 1.注册：
首次登陆需要注册，提供邮箱，接收验证码，设置密码
## 2.登录：
用邮箱和密码登录
## 3.占卜流程：
### 3.1.用户在提问框中输入问题，点击开始占卜；
### 3.2.LLM根据用户问题选择牌型；
### 3.3.页面展示牌型和抽卡；
### 3.4.用户从所有卡牌中逐张抽取，按抽取顺序依次放入对应位置，直到满足牌型所需数量；
### 3.5.全部抽取结束后一起翻开卡牌，用户点击开始解牌；
### 3.6.LLM根据用户问题和抽牌结果进行解读；
### 3.7.展示给用户，结束

# 功能列表：
p0：占卜流程
p0：注册，持久化用户身份信息
p0：登录
p0：用户占卜历史数据持久化（异步落库）
p0：每日占卜次数限制（签到获得次数，每人每天签到获得1次机会）
p2：针对本次占卜与LLM进行长时间对话

# 页面/接口描述：
### 页面
1. 登录/注册页
   - 输入邮箱、密码、验证码
   - 操作：注册、登录
2. 占卜主页
   - 输入框：用户输入问题
   - 按钮：开始占卜
   - 展示区：牌型布局 + 卡牌（背面）
   - 操作：逐张点击卡牌抽卡（抽满后一起翻开） → 点击"开始解牌"
   - 解读区：SSE 流式展示 LLM 解读结果
3. 历史记录页（P2）
   - 列表展示过往占卜记录（问题、时间、牌型）
   - 点击可查看完整解读
### 接口
| 接口 | 方法 | 说明 | 入参 | 出参 |
|------|------|------|------|------|
| /api/auth/register | POST | 注册 | email, password, code | token |
| /api/auth/login | POST | 登录 | email, password | token |
| /api/auth/send-code | POST | 发送验证码 | email | success |
| /api/credit/sign | POST | 每日签到，获得1次占卜机会 | - | todayQuota |
| /api/credit/balance | GET | 查询剩余占卜次数 | - | remaining |
| /api/tarot/start | POST | 开始占卜（校验次数→扣减→LLM选牌型） | question | sessionId, spreadType, cardCount, positions[] |
| /api/tarot/draw | POST | 逐张抽卡 | sessionId | card(name, image, reversed, position) |
| /api/tarot/interpret | GET(SSE) | 解牌，流式返回 | sessionId | SSE stream(解读文本) |
| /api/tarot/history | GET | 历史记录 | page, size | list[] |

### 关键点
- /api/tarot/start 会先校验次数→扣减次数→再调用 LLM 返回结构化 JSON（牌型、张数、位置含义），次数不足则拒绝
- /api/tarot/draw 是纯后端随机抽牌逻辑，不调用 LLM；用户每次调用抽一张，后端按顺序分配到对应位置，抽满后返回标记
- /api/tarot/interpret 是第二次 LLM 调用——SSE 流式推送
- /api/credit/sign 调用大营销项目的签到接口，返还1次占卜机会
- sessionId 用来关联一次完整的占卜会话（问题 → 牌型 → 抽卡 → 解读），存在 Redis 里
- 占卜结果异步落库（MQ/异步线程），不阻塞用户体验

# 非功能需求：
1.注册和登录需要防止机器人
2.LLM API在部署时通过环境变量设置
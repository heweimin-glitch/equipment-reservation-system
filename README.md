# 实验设备预约系统

基于**微信小程序 + Spring Boot** 的高校实验室设备预约管理平台，支持设备管理、预约申请、审批审核、库存管理，并集成 **DeepSeek AI** 助手。

---

## 技术栈

| 层级 | 技术 |
|------|------|
| 前端 | 微信小程序原生开发 |
| 后端 | Spring Boot 4 + MyBatis |
| 数据库 | MySQL |
| 安全 | BCrypt 密码加密 + JWT 无状态认证 |
| AI | DeepSeek API（意图识别 + 智能问答） |
| 图片存储 | 腾讯云 COS |

---

## 功能模块

### 普通用户
- 浏览设备列表，按学科分类筛选
- 查看设备详情（规格、存放位置、库存）
- 提交预约申请（选择时间、用途、审批人）
- 查看当前预约 / 历史记录
- 取消预约 / 归还设备
- 修改密码
- **AI 助手**：自然语言查询设备、预约记录

### 管理员
- 设备管理：入库 / 出库 / 新增设备
- 用户管理：创建普通用户 / 同类别管理员
- 预约审核：同意 / 拒绝（含拒绝原因）
- 查看管辖范围内的预约记录

### AI 助手
- 自然语言查询设备详情（"数字示波器怎么样"）
- 查询空闲设备列表（"有哪些可用设备"）
- 查询个人预约记录（"我的预约"）
- 智能对话指导预约流程

---

## 快速开始

### 1. 数据库

```bash
mysql -u root -p < DataBase/Equipment_Booking_D.sql
```

### 2. 后端启动

```bash
cd BackEnd/reservation-system

# 设置环境变量
export MySQLPassWord=your_mysql_password
export DEEPSEEK_API_KEY=sk-xxxxxxxx

# 启动
./mvnw spring-boot:run
```

默认端口 `8080`，可在 `application.properties` 中修改 `server.port`。

### 3. 小程序

1. 打开[微信开发者工具](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)
2. 导入 `FrontEnd/miniprogram-1` 目录
3. 修改 `utils/config.js` 中的 `baseUrl` 为后端实际地址
4. 编译运行

---

## 核心 API

| 接口 | 方法 | 说明 | 认证 |
|------|------|------|:--:|
| `/user/login` | GET | 用户登录，返回 JWT Token | — |
| `/user/updatePassword` | POST | 修改密码 | JWT |
| `/device/list` | GET | 设备列表 | JWT |
| `/device/detail` | GET | 设备详情 | JWT |
| `/reservation/add` | POST | 新增预约 | JWT |
| `/reservation/current` | GET | 当前预约 | JWT |
| `/reservation/history` | GET | 历史预约 | JWT |
| `/reservation/approve` | POST | 审批通过 | JWT |
| `/reservation/reject` | POST | 拒绝预约 | JWT |
| `/ai/chat` | POST | AI 对话 | JWT |

---

## 预约状态流转

```
用户提交
    ↓
  [0] 待审核 ──管理员审批──→ [3] 使用中 ──用户归还──→ [4] 已完成
    ↓                            ↓
    管理员拒绝                   用户取消
    ↓                            ↓
  [1] 未通过                   [2] 已取消
```

---

## 项目结构

```
equipment-reservation-system/
├── FrontEnd/miniprogram-1/        # 微信小程序
│   ├── pages/                     # 10 个页面
│   │   ├── login/                 # 登录
│   │   ├── index/                 # 首页（设备列表）
│   │   ├── device/                # 设备详情
│   │   ├── deviceappointment/     # 预约表单
│   │   ├── CurrentReservation/    # 当前预约
│   │   ├── HistoricalReservation/ # 历史预约
│   │   ├── my/                    # 个人中心
│   │   ├── Ai/                    # AI 助手
│   │   ├── DeviceManage/          # 设备管理（管理员）
│   │   ├── UserManage/            # 用户管理（管理员）
│   │   └── changepassword/        # 修改密码
│   └── utils/
│       ├── config.js              # 全局配置
│       └── request.js             # HTTP 封装（自动携带 JWT）
├── BackEnd/reservation-system/    # Spring Boot 后端
│   └── src/main/java/com/example/reservationsystem/
│       ├── controller/            # REST 接口
│       ├── service/               # 业务逻辑
│       ├── mapper/                # MyBatis 数据层
│       ├── entity/                # 数据实体
│       ├── dto/                   # 数据传输对象
│       ├── config/                # Spring 配置
│       ├── interceptor/           # JWT 认证拦截器
│       └── util/                  # 工具类
└── DataBase/                      # 数据库脚本
```

---

## 设计亮点

- **BCrypt 密码加密**：密码哈希存储，兼容历史明文密码自动升级
- **JWT 无状态认证**：请求拦截器统一校验，前端 `request.js` 自动携带 Token
- **DeepSeek 意图解析**：用户自然语言 → 结构化意图（DETAIL/LIST/RECORD/CHAT）→ 分发业务
- **库存事务安全**：审批通过时检查库存 + 扣减，归还时自动恢复
- **学科分类映射**：设备 ID 前 2 位编码学科，自动匹配管理员管辖范围

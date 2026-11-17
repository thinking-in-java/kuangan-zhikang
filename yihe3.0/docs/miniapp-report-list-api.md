# 小程序「我的体检报告」列表接口

开发联调地址：`http://192.168.1.105:18080`。上线时使用已配置的 HTTPS 业务域名。

全部接口携带微信登录返回的请求头：

```http
Authorization: Bearer <token>
```

须先完成职工身份绑定。后端从 Token 和有效绑定取得职工身份；**不要传 employeeId、recordId、工号或姓名**。接口仅返回本人所在数据范围的当前报告版本，已移除关联及旧版本不进入列表。

## 1. 已审核列表

```http
GET /ehr/miniapp/reports/approved?page=1&pageSize=10
```

包含医生已审核通过的报告，区分 `approved`（待归档）、`archived`（已归档）。识别成功不等于审核通过。

## 2. 处理中／待审核列表

```http
GET /ehr/miniapp/reports/pending?page=1&pageSize=10
```

包含尚未审核通过的报告，并显示识别、退回、失败等具体状态。

## 3. 全部报告列表

```http
GET /ehr/miniapp/reports?page=1&pageSize=10
```

也可用统一入口 `GET /ehr/miniapp/reports?status=approved` 或 `status=pending`。默认 `status=all`。

`page` 为 1–100000 的整数，默认 1；`pageSize` 为 1–50 的整数，默认 10。按本版本创建时间倒序，同一时间按 reportId 倒序。不要用体检日期当上传时间。

## 返回格式

以下为结构示例，非真实用户数据：

```json
{
  "code": 0,
  "data": {
    "status": "pending",
    "page": 1,
    "pageSize": 10,
    "total": 1,
    "hasMore": false,
    "counts": {"all": 3, "approved": 2, "pending": 1},
    "items": [{
      "reportId": "报告UUID",
      "revision": 1,
      "title": "年度体检报告.pdf",
      "fileName": "年度体检报告.pdf",
      "mimeType": "application/pdf",
      "fileSize": 234753,
      "pageCount": 11,
      "uploadedAt": "2026-11-15 12:23:47.717000",
      "examDateRaw": "2026年09月18日",
      "examDateVerified": null,
      "institutionRaw": null,
      "reviewedAt": null,
      "reviewStatus": "needs_review",
      "reviewDecision": null,
      "tab": "pending",
      "status": "pending_review",
      "statusText": "待审核",
      "approved": false,
      "published": false,
      "candidate": true,
      "jobId": "任务UUID",
      "stage": "extracted",
      "queueState": "succeeded",
      "progressStage": "finished",
      "progressPercent": 100,
      "errorCode": null,
      "errorMessage": null,
      "canRetry": false,
      "detailPath": "/ehr/miniapp/reports/报告UUID/1"
    }]
  }
}
```

- `total` 是当前标签总数；`counts` 是本人全部、已审核、待审核三个标签的数量，`all=approved+pending`。
- 空列表返回 HTTP 200、`code=0`、`items=[]`、`total=0`，不算错误。
- `uploadedAt`、`reviewedAt` 为 UTC 时间，前端转北京时间显示；`examDateVerified` 是日期，无须时区转换。
- 日期及机构允许为 null，页面展示“待核对／未识别”，不要用当天日期或虚构机构补齐。
- 列表只返回报告元信息，不包含未审核的医学指标和结论。详情沿用 `GET /ehr/miniapp/reports/{reportId}/{revision}`，未审核详情会标记候选。
- `approved=true` 表示已通过医生审核，`published=true` 表示已归档发布。这两个含义不同。
- 已归档历史版本如有新的修订，列表只显示最新修订；历史详情仍按原版本地址和权限查询。
- 失败重试仍由后台管理，小程序不调用管理员重试接口。重新上传按钮沿用 `POST /ehr/miniapp/reports`。

| status | 所在标签 | 文案 |
|---|---|---|
| approved | 已审核 | 已审核·待归档 |
| archived | 已审核 | 已审核·已归档 |
| queued | 处理中／待审核 | 等待识别 |
| processing | 处理中／待审核 | 正在识别 |
| pending_review | 处理中／待审核 | 待审核 |
| returned | 处理中／待审核 | 已退回·待完善 |
| rejected | 处理中／待审核 | 审核未通过 |
| failed | 处理中／待审核 | 识别失败 |
| cancelled | 处理中／待审核 | 识别已停止 |

## 小程序调用

```javascript
const paths = {
  all: '/ehr/miniapp/reports',
  approved: '/ehr/miniapp/reports/approved',
  pending: '/ehr/miniapp/reports/pending'
};
wx.request({
  url: baseUrl + paths[activeTab],
  method: 'GET',
  header: { Authorization: 'Bearer ' + wx.getStorageSync('token') },
  data: { page: 1, pageSize: 10 },
  success({statusCode, data}) {
    if (statusCode === 401) return goLogin();
    if (statusCode === 403) return goEmployeeBinding();
    if (statusCode !== 200 || data.code !== 0) return showError(data.msg);
    // 按 data.data.items 渲染卡片，counts 渲染角标，hasMore 控制加载更多。
    renderReports(data.data);
  }
});
```

401：Token 无效或过期；403：未绑定职工；409：绑定档案已停用；422：参数不合法。仅上传成功后刷新列表；识别中可定时刷新当前页或查询报告详情。

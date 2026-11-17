# 小程序微信登录、自动注册与页面分流

后端地址：`http://192.168.1.105:18080`。小程序AppID：`wx23dee6836f09f2f5`。
AppSecret仅保存在A端私有配置，不放入小程序代码、Postman或本文。

## 1. 获取配置

`GET /ehr/miniapp/config`，无需登录。读取wechatConfigured及consentVersion。配置成功时wechatConfigured=true，可开启微信登录按钮。

## 2. 一键登录并自动注册

`POST /ehr/miniapp/wechat/login`，Content-Type为application/json。

```json
{"code":"wx.login返回的新code","privacyAccepted":true,"consentVersion":"2026-11-v1"}
```

协议版本从config读取；privacyAccepted来自用户勾选状态。后端向微信核验code，首次成功自动创建微信用户，已有用户复用账号，无需另调注册接口。

首次登录且未完成职工认证：

```json
{"code":0,"data":{
  "miniUserId":"微信用户UUID","isNewUser":true,
  "bindingState":"UNBOUND","canEnterHome":false,"needIdentityVerification":true,
  "nextAction":"VERIFY_EMPLOYEE","token":"后端会话令牌","tokenType":"Bearer",
  "expiresIn":7200,"expiresAt":"UTC过期时间","consentVersion":"2026-11-v1"
}}
```

已认证并绑定的用户：

```json
{"code":0,"data":{
  "miniUserId":"微信用户UUID","isNewUser":false,
  "bindingState":"BOUND","canEnterHome":true,"needIdentityVerification":false,
  "nextAction":"ENTER_HOME","token":"后端会话令牌","tokenType":"Bearer",
  "expiresIn":7200,"expiresAt":"UTC过期时间","consentVersion":"2026-11-v1",
  "employee":{"employeeId":"职工UUID","recordId":"档案UUID","name":"后台姓名","employeeNo":"工号","organization":"单位","team":"班组","position":"岗位","phoneMasked":"脱敏电话"}
}}
```

**按canEnterHome/nextAction跳转，不按isNewUser判断认证是否完成。** 已有微信用户但尚未绑定时，isNewUser=false、canEnterHome=false，仍需身份认证。PENDING_CONFIRMATION继续资料确认或重新核验；CORRECTION_PENDING进入更正审核等待页，不能提前进入个人健康数据页面。

## 3. 认证及登录后的接口

携带 `Authorization: Bearer <token>`。

| 方法 | 地址 | 用途 |
|---|---|---|
| GET | `/ehr/miniapp/me` | 恢复会话、查询是否已绑定及是否可进首页 |
| POST | `/ehr/miniapp/binding/verify` | 姓名、工号、个人企业验证码核验 |
| POST | `/ehr/miniapp/binding/confirm` | 用户核对后台资料后确认绑定 |
| GET | `/ehr/miniapp/health-record` | 本人健康档案 |
| POST | `/ehr/miniapp/reports` | 已绑定用户上传原始报告 |
| GET | `/ehr/miniapp/reports` | 本人全部报告列表 |
| GET | `/ehr/miniapp/reports/approved` | 本人已审核报告列表 |
| GET | `/ehr/miniapp/reports/pending` | 本人处理中／待审核报告列表 |
| DELETE | `/ehr/miniapp/session` | 退出并吊销当前会话 |

报告列表分页、返回字段及小程序调用示例见 [miniapp-report-list-api.md](miniapp-report-list-api.md)。

verify请求：

```json
{"name":"企业登记姓名","employeeNo":"职工工号","verificationCode":"企业后台为该职工生成的验证码"}
```

verify返回employee预览和confirmationToken。显示后台资料，用户勾选与本人一致后调用confirm：

```json
{"confirmationToken":"verify返回的凭据","confirmed":true}
```

confirm成功返回canEnterHome=true、nextAction=ENTER_HOME。验证码逐人签发，不能使用固定公共验证码。

## 4. 小程序调用示例

```javascript
// 前提：用户已主动勾选协议；config来自GET /ehr/miniapp/config。
wx.login({success({code}) {
  wx.request({
    url: baseUrl + '/ehr/miniapp/wechat/login', method: 'POST',
    data: {code, privacyAccepted: userCheckedAgreement, consentVersion: config.consentVersion},
    success({statusCode, data}) {
      if (statusCode !== 200 || data.code !== 0) return showError(data.msg);
      const result = data.data;
      wx.setStorageSync('token', result.token);
      if (result.canEnterHome) return goHome();
      if (result.nextAction === 'WAIT_FOR_REVIEW') return goIdentityReview();
      return goIdentityVerification();
    }
  });
}});
```

goHome等方法由小程序开发者替换为实际页面导航。登录失败时重新调用wx.login获取code；后端不会返回AppSecret、OpenID或session_key。

401重新登录；403未认证/无权限；422参数或协议不满足；429限流；503微信配置或上游不可用。

当前HTTP局域网地址用于开发联调，发布时换成实际HTTPS业务域名。流程参考[微信code2Session官方接口](https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html)。完整认证字段见 [miniapp-wechat-binding-api.md](miniapp-wechat-binding-api.md)，Postman见 [miniapp-wechat-binding.postman_collection.json](miniapp-wechat-binding.postman_collection.json)。

## 运维与验证边界

本机默认私有文件 `F:\upload\.config\wechat-miniapp.json`，仅当前服务用户、管理员和SYSTEM可访问，不在仓库和静态资源目录中。环境变量EHR_WECHAT_APP_ID、EHR_WECHAT_APP_SECRET成对配置时优先使用环境变量；EHR_WECHAT_CREDENTIALS_FILE可指定其他文件。仅real模式读取默认私有文件，synthetic不自动读取正式密钥。修改文件后需重启服务。

真实用户注册仍需该小程序wx.login取得的有效code。无效code网络探测和离线状态检查不能替代真实用户端到端登录验收。

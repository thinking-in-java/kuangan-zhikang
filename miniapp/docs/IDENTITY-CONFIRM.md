# 个人信息确认与行内编辑

点击“信息有误？修改信息”后，上方姓名、工号、性别、出生日期、所属单位、班组、岗位工种和手机号直接进入可编辑状态，不弹出修改申请框。取消修改恢复原档案；每次编辑清除核对勾选，需重新核对后点击底部“确认并进入矿安智康”。手机号不回填掩码到输入框，留空保留原值，替换需填写完整手机号；返回资料和页面只展示脱敏号码。

统一请求 POST /api/v1/auth/confirm-worker：{profileVersion,confirmed:true,updates?:{name,employeeNo,gender,birthDate,organization,team,job,phone}}。只发送变化的字段。后端应在同一操作内验证归属、版本和可修改字段，保存资料及确认状态，再返回更新后的 Profile（identityConfirmed:true、新 profileVersion、phoneMasked）。姓名和工号的修改必须由后端核验身份匹配，不能用这些字段切换账号、职工ID、医生角色或访问权限。旧的修改申请接口已从前端移除。

真实确认接口仍待后端实现及联调；当前既有演示模式中的 Mock 已支持保存修改与确认，不代表企业后台已真实保存。

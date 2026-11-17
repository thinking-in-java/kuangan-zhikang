# PDF 上传失败修复（2026-11-15）

## 故障原因

服务日志报 `NoClassDefFoundError: org/bouncycastle/asn1/ASN1Primitive`。
失败文件为 234753 字节、11 页 PDF，带加密权限标记但无需打开密码。
原部署只有旧 Bouncy Castle 1.38，无法满足 iText 加密信息解析需要。
异常发生在原件入库及创建 OCR 任务之前，前端收到通用 HTTP 500。
旧解析器构造失败时还可能持有 Windows 文件句柄，导致暂存文件清理失败。

## 修复

- iText 更新为 5.5.13.4，显式引入匹配的 Bouncy Castle 1.78.1 组件，排除旧重复依赖。
- PDF 元信息读取改用有大小上限的字节读取，避免构造失败时泄漏文件句柄。
- 允许无需打开密码的权限保护 PDF；需要打开密码或损坏的 PDF 返回明确 HTTP 422。
- 保留原始上传字节，不移除权限、不重写 PDF、不启用 `unethicalreading`。
- 上传全过程 finally 清理暂存文件，事务回滚继续负责清理已移动但未入库的原件。
- 后台和小程序继续使用同一个上传服务，接口和 OCR 契约不变。

## 验证与部署

实际失败文件：读取 11 页成功，处理前后 SHA-256 相同；未为正式职工创建新报告。
13 项隔离上传回归通过，覆盖可读取的加密 PDF、需密码 PDF、损坏文件、幂等、原件下载一致性及事务回滚清理。
正式服务 11 项只读检查、小程序 13 项配置及拒绝非法请求检查通过。
后台及小程序 WAR 均重新构建成功，正式服务运行于 18080，隔离测试服务已停止。

5 个旧失败暂存文件完整移入私有目录 `F:/upload/.config/failed-uploads/pdf-upload-failure-20261115`，未删除原件。
详细验证结果：`database/pdf-upload-verification.json`、`database/pdf-upload-fix-diagnosis.json`。

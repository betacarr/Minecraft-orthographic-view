## Orthographic View (Fabric / 1.19.4)

一个为 Minecraft Fabric 客户端添加 **可切换正交视角 + 可调缩放** 的小模组。

---

### 功能简介

- **正交视角渲染**：替换默认透视投影，提供类似平行投影的效果（无近大远小）。  
- **快捷键切换**：
  - `O`：开启 / 关闭正交视角。
  - `[`：在正交模式下放大（视角更近，看得更少）。
  - `]`：在正交模式下缩小（视角更远，看得更多）。
- **动态缩放**：内部使用 `orthographicScale` 控制可视范围，自动限制在 \[1.0, 200.0]，避免异常拉伸。

按键绑定可在游戏内“按键设置”中搜索 **“Orthographic View”** 分类进行修改。

---

### 环境要求

- **Minecraft**：1.19.4
- **Fabric Loader**：>= 0.14.21
- **Fabric API**：适配 1.19.4 的版本（推荐在 Fabric 官网或 Mod 平台上选择对应版本）
- **Java**：17

---

### 安装与使用

1. 确保已经安装好 **Fabric Loader 1.19.4** 和 **Fabric API**。
2. 下载构建好的模组 Jar（或自己构建，见下文 “本地构建”）：  
   - `build/libs/orthographic-view-1.0.0+mc1.19.4.jar`
3. 将 Jar 文件放入：
   - 单人游戏：`<你的 .minecraft 目录>/mods`
   - 服务端：`<server 根目录>/mods`（本模组目前只对客户端渲染生效）
4. 启动游戏后：
   - 进入世界，按 `O` 切换正交视角；
   - 使用 `[` / `]` 调整缩放，屏幕顶部会显示当前缩放数值。

---

### 本地构建

克隆仓库并在项目根目录执行：

```bash
./gradlew build
```

构建成功后，在 `build/libs` 下可以看到：

- `orthographic-view-1.0.0+mc1.19.4.jar`（发布用）  
- `orthographic-view-1.0.0+mc1.19.4-sources.jar`（源码包，可选）

---

### 项目结构简要说明

- `src/main/resources/fabric.mod.json`  
  模组元数据（id、名称、描述、依赖等）。
- `src/client/java/com/example/ExampleModClient.java`  
  客户端入口，注册按键并实现按键逻辑与缩放调整。
- `src/client/java/com/example/mixin/client/OrthographicProjectionMixin.java`  
  Mixin 注入 `GameRenderer#getBasicProjectionMatrix`，在启用时使用正交投影矩阵。
- `src/main/resources/assets/orthographic_view/lang/*.json`  
  中英文本地化字符串（按键名称、分类等）。

---

### 许可证

本项目基于 Fabric 官方示例模组模板，模板部分遵循 **CC0-1.0** 协议。  
你可以自由学习、修改和在遵守 CC0 条款的前提下再分发本仓库内容。

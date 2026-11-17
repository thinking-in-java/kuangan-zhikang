# 医生手臂动作素材

使用内置 `image_gen` 工具编辑原画，保留 RGBA 透明通道。程序只取校正图中的手臂区域，脸、发型和主体继续使用原图。素材全部存放于 `yihe-admin/src/main/resources/static/assets/images/`。

| 文件 | 用途 |
| --- | --- |
| [digital-doctor-female-rest.png](../yihe-admin/src/main/resources/static/assets/images/digital-doctor-female-rest.png) | 女医生自然垂手 |
| [digital-doctor-female-mid.png](../yihe-admin/src/main/resources/static/assets/images/digital-doctor-female-mid.png) | 女医生中间姿势 |
| [digital-doctor-male-rest.png](../yihe-admin/src/main/resources/static/assets/images/digital-doctor-male-rest.png) | 男医生自然垂手 |
| [digital-doctor-male-mid.png](../yihe-admin/src/main/resources/static/assets/images/digital-doctor-male-mid.png) | 男医生中间姿势 |
| digital-doctor-female-clean.png / digital-doctor-male-clean.png | 补齐原始抬手后方的白大褂；仅局部取样 |

原图 `digital-doctor-female.png` / `digital-doctor-male.png` 没有覆盖。画面左侧的自由手是人物自身的右手；画面右侧的左手持平板，保持原位。三种姿势沿同一网格对应，并使用预乘 Alpha 插值，避免将原图手掌直接翻转。

## 本次垂手与过渡姿势的提示词

### 女医生垂手

Edit target: the provided pink-haired female chibi doctor. Create a registered animation corrective REST pose for this exact same character. Change ONLY the FREE ARM on the LEFT SIDE OF THE IMAGE (her own RIGHT arm). It must hang naturally downward along the outside of her white coat, elbow gently bent, wrist straight, fingers relaxed pointing downward, thumb anatomically correct toward the body, palm facing her thigh / slightly backward in a natural neutral resting position, visible side or back of hand, NOT a palm-up hand rotated upside down. Keep shoulder attachment correct, continuous full-length white sleeve ending at wrist, anatomically correct right hand with 5 natural fingers, no broken/reversed wrist, no mirrored left hand. The other arm on IMAGE RIGHT must keep holding the tablet exactly as now. Keep the exact original identity, face, pink bob hairstyle, smile, eyes, head size and position, coat, scrubs, legs, shoes, tablet, camera, front-facing body, lighting, quality and render style unchanged. Full body, keep the same exact 1024x1536 canvas registration and margins, head and shoes at exactly the original locations. Background must be genuinely transparent with no glow, no platform, no shadow, no text. This is one complete full-body pose, not a collage.

### 男医生垂手

Edit target: the provided brown-haired glasses male chibi doctor. Create a registered animation corrective REST pose of this exact character. Change ONLY the FREE ARM on the LEFT SIDE OF THE IMAGE (his own RIGHT arm). It hangs naturally downward along the outside of his white coat, elbow slightly bent, wrist straight, relaxed fingers pointing down, anatomically correct thumb toward body, hand showing its side/back in a natural neutral rest. NOT a palm-up hand rotated upside down. Continuous natural full-length white sleeve ending at wrist, correct right hand, no reversed/mirrored hand or bent-back wrist. The arm on IMAGE RIGHT keeps holding the tablet exactly as now. Keep exact original face, glasses, hairstyle, head size/location, body, coat, scrubs, legs, shoes, tablet, front camera and high-quality 3D render style unchanged. Full body on same exact 1024x1536 canvas registration and margins, head and shoes remain in the exact original positions. Genuine transparent background, no glow or shadow or platform, no text. One complete pose, not a collage.

### 女医生中间姿势

Image 1 is the edit target: pink-haired chibi female doctor greeting with a raised free arm on image-left. Image 2 is the exact same character's relaxed rest pose for identity and pose reference. Create ONE registered full-body INTERMEDIATE pose, midway through lifting that same free arm from her side to the greeting in image1. Change ONLY her free arm on IMAGE LEFT (her anatomical RIGHT arm). Shoulder remains attached at the same place. Upper arm hangs nearly downward, elbow at waist height a little outside her coat. Forearm points outward to image-left and diagonally DOWNWARD about 25 degrees below horizontal; wrist near her HIP height. Hand in a natural oblique side view, relaxed open fingers gently pointing left-down, thumb anatomically correct toward torso/upward, neutral straight wrist. Full-length white sleeve to wrist, elbow bent naturally around 100 degrees. NOT a mirrored opposite hand, not an upside-down palm, no reversed elbow. This hand will lift from rest to open palm greeting; it must be anatomically coherent. Keep face/hair/head, body/legs/shoes, tablet and tablet-holding arm EXACTLY in original image1 positions, proportions, quality and lighting. Same exact 1024x1536 registration and margins. Genuine transparent background, no glow/shadow/platform, no text or extra figures.

### 男医生中间姿势

Image1 edit target male chibi doctor with glasses greeting, image2 same character at rest. Create ONE registered full-body intermediate arm-lift pose. Change ONLY free arm on IMAGE LEFT, his anatomical RIGHT arm. Upper arm nearly downward, elbow a little outside coat at waist. Forearm points left and diagonally DOWNWARD 25 degrees below horizontal, wrist around hip height. Relaxed open hand with palm partly facing viewer and obliquely upward, fingers pointing left-down, thumb upward toward body side, a natural correct RIGHT hand, straight wrist, full-length white coat sleeve ending at wrist, natural elbow bend. This is halfway from lowered resting hand to raised greeting. Do not rotate or mirror the palm incorrectly, no backward wrist. Leave head/face/glasses/hair/body/legs/shoes/tablet/tablet-holding arm exactly as image1, same proportions and position, same polished 3D render. Same exact 1024x1536 canvas registration and margins. Genuinely transparent background, no glow, no shadow, no text or collage.

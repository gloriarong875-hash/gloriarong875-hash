const PLACEHOLDER_IMG = "https://picsum.photos/seed/gloria-archive/1200/800";

const projectList = document.getElementById("project-list");
const projectWindow = document.getElementById("project-window");
const mainMedia = document.getElementById("window-main-media");
const mediaColumn = document.getElementById("media-column");
const projectText = document.getElementById("project-text");
const windowTitle = document.getElementById("window-title");
const windowCategory = document.getElementById("window-category");
const moreButton = document.getElementById("more-button");
const textButton = document.getElementById("text-button");
const closeButton = document.getElementById("close-button");
const closeButtonExpanded = document.getElementById("close-button-expanded");
const hoverPreview = document.getElementById("hover-preview");

let activeIndex = 0;
let activeMediaObserver = null;
let previewCarouselInterval = null;
let previewCarouselIndex = 0;

const projects = [
    {
        category: "Visual Design",
        title: "My First Lamp",
        year: "2024",
        cover: "images/main_image.jpg",
        preview: "images/main_image.jpg",
        summary: "第一盏手工灯具，以长灯笼的结构和氛围作为起点。",
        details: {
            background: "这是一个以中国长灯笼为灵感的手工灯具项目。我希望把熟悉的传统意象转化成更适合日常空间的物件，让传统形态以更轻的方式进入生活。",
            problem: "灯具不只是造型，它同时受到材料、骨架、透光方式和空间尺度的影响。项目需要在手作质感和稳定结构之间找到平衡。",
            method: "我从拆解灯笼结构开始，尝试用竹、藤、木和纸重新组织骨架与灯面，再通过多次测试调整比例、透光强度和整体姿态。",
            role: "我负责概念构思、结构推敲、材料选择、手工制作和过程记录。",
            result: "最终形成一盏有柔和光感和手作痕迹的灯，也让我更理解材料实验和结构推敲在物件设计中的重要性。"
        },
        media: [
            { src: "images/main_image.jpg", caption: "成品主图", size: "wide" },
            { src: "images/detail_image_2.jpg", caption: "三视图与结构草图" },
            { src: "images/loop_image_3.jpg", caption: "灵感与造型推导" },
            { src: "images/IMG_7642(1).png", caption: "材料细节", size: "wide" }
        ]
    },
    {
        category: "Visual Design",
        title: "Pattern Re-Creation",
        year: "2025",
        cover: "images/我似一座流动的坟墓.jpg",
        preview: "images/我似一座流动的坟墓.jpg",
        summary: "传统图案再创造，通过观察和重构形成新的图形语言。",
        details: {
            background: "这是一个传统图案再创造项目，关注纹样背后的结构、秩序和象征意味。",
            problem: "传统图案容易被当作装饰素材直接使用，但我希望理解其生成逻辑，而不是只复制表面形态。",
            method: "我从对称、重复、色彩和层级关系入手，拆解传统图像中的构成方法，再将其转译为新的画面关系。",
            role: "我负责资料观察、图案拆解、画面重构、绘制和最终呈现。",
            result: "项目产出多件可延展的图案再创造作品，也让我意识到图案是一套关于秩序、观看节奏和文化记忆的系统。"
        },
        media: [
            { src: "images/我似一座流动的坟墓.jpg", caption: "作品主图", size: "wide" },
            { src: "images/7766.jpg", caption: "小画 01" },
            { src: "images/小画_01.png", caption: "小画 02" }
        ]
    },
    {
        category: "Visual Design",
        title: "Shadows in Motion",
        year: "2024",
        cover: "images/图片444.png",
        preview: "images/图片444.png",
        summary: "从物体、阴影和空间构成出发的形态观察练习。",
        details: {
            background: "这是从物体、阴影和空间构成出发的小型视觉练习。",
            problem: "同一个日常物体在不同角度、光线和组合方式下会产生完全不同的视觉结构，我想把这种变化整理出来。",
            method: "项目从类似菠萝的形态开始，延展到阴影、平面和空间关系，再通过图纸和构成画面整理观察结果。",
            role: "我负责观察、拍摄、草图绘制、构成整理和视觉转译。",
            result: "最终形成构成画面和过程图纸，也训练了我把日常形态看作结构而不是表面的能力。"
        },
        media: [
            { src: "images/图片555.png", caption: "形态观察 01" },
            { src: "images/图片444.png", caption: "形态观察 02" },
            { src: "images/空间构成作品_03.png", caption: "空间构成 01", size: "wide" },
            { src: "images/空间构成作品_07.png", caption: "空间构成 02" },
            { src: "images/3322.png", caption: "过程图纸" }
        ]
    },
    {
        category: "Visual Design",
        title: "Photography",
        year: "2024",
        cover: "images/打翻-戎誉.jpg",
        preview: "images/打翻-戎誉.jpg",
        summary: "日常行走中的摄影练习，用图像保存光、质感和偶然场景。",
        details: {
            background: "这是我在日常行走中积累的摄影练习，也是个人视觉观察的一部分。",
            problem: "摄影对我来说不是单纯记录，而是训练对光线、空间、质感和瞬间关系的敏感度。",
            method: "我通常从一个细小的视觉触发点开始，再快速调整构图和时机，记录当下的光线、动作或空间关系。",
            role: "我独立完成拍摄、筛选、后期整理和图像归档。",
            result: "最终形成一个可为后续设计提供灵感的图像档案，也让我确认观察本身就是一种设计方法。"
        },
        media: [
            { src: "images/打翻-戎誉.jpg", caption: "摄影作品 01", size: "wide" },
            { src: "images/激流-戎誉.jpg", caption: "摄影作品 02" },
            { src: "images/胶卷-戎誉.jpg", caption: "摄影作品 03" },
            { src: "images/建筑-戎誉.jpg", caption: "摄影作品 04" }
        ]
    },
    {
        category: "Visual Design",
        title: "RISO Print Study",
        year: "2025",
        cover: "images/实物图.jpg",
        preview: "images/实物图.jpg",
        summary: "孔版印刷体验，关注分色、套印和纸面质感。",
        details: {
            background: "这是一次关于分色、套印和纸面质感的孔版印刷实验。",
            problem: "数字图像在进入印刷流程后，会受到色版、纸张和误差的共同影响。我想观察工艺如何改变图像气质。",
            method: "我将画面拆分成不同色版，通过多次套印观察偏移、透明度和混色效果。",
            role: "我参与分色、测试印刷、实物记录和过程整理。",
            result: "项目最终形成实物印刷成品和过程记录，也让我意识到工艺过程本身可以成为视觉语言。"
        },
        media: [
            { src: "images/实物图.jpg", caption: "印刷实物", size: "wide" },
            { src: "images/微信图片_20250621235535.png", caption: "过程记录", size: "wide" }
        ]
    },
    {
        category: "Brand Design",
        title: "Bianjing Fair Service Design",
        year: "2025",
        cover: "images/实物模型.jpg",
        preview: "images/实物模型.jpg",
        summary: "围绕《逆水寒》的文化体验服务设计。",
        details: {
            background: "这是围绕《逆水寒》文化体验展开的服务设计项目，包含市集体验、服务蓝图、增强现实互动和展览输出。",
            problem: "游戏文化如果只停留在线上内容，很难转化为可参与、可传播的线下体验。项目需要让用户从观看进入互动。",
            method: "我们从服务蓝图出发，把关键节点转译为展览模型、展板和互动内容，并通过视频与现场展示验证体验流程。",
            role: "我参与用户路径梳理、触点设计、展板制作、增强现实展示和汇报材料整理。",
            result: "最终完成展板、模型照片、服务蓝图和增强现实视频，也让我更理解服务设计要把叙事、流程、空间和技术体验放在同一条用户路径里。"
        },
        video: "videos/市集全貌展示视频.mp4",
        media: [
            { src: "images/展板.png", caption: "项目展板", size: "wide" },
            { src: "images/市集服务蓝图.jpg", caption: "服务蓝图", size: "wide" },
            { src: "images/ar互动.jpg", caption: "增强现实互动" },
            { src: "images/实物模型.jpg", caption: "展览模型" },
            { src: "videos/小游戏AR演示视频.mp4", caption: "互动演示视频", type: "video", size: "wide" }
        ]
    },
    {
        category: "Brand Design",
        title: "Communication Practice",
        year: "2024-2025",
        cover: "images/9a818d008e92e4d80e7753cb588d457.jpg",
        preview: "images/9a818d008e92e4d80e7753cb588d457.jpg",
        summary: "支教和校园活动中的宣传策划、招募传播与组织实践。",
        details: {
            background: "这是基于支教和校园活动的宣传策划实践。",
            problem: "真实传播项目需要同时面对内容清晰度、视觉排版、发布时间和组织执行，不能只做单张好看的图。",
            method: "项目从受众分析到内容策划、视觉排版和发布节奏推进，同时配合志愿者招募与组织管理。",
            role: "我负责招募宣传、推文排版、短视频制作和团队协作。",
            result: "项目产出多篇推文和短视频，并支持志愿者招募与活动落地。我学到传播设计要清晰、及时并且能落地。"
        },
        media: [
            { src: "images/detail_image_1.jpg", caption: "传播物料", size: "wide" },
            { src: "images/9a818d008e92e4d80e7753cb588d457.jpg", caption: "项目记录" }
        ]
    },
    {
        category: "Interaction Design",
        title: "AR Interaction Demo",
        year: "2025",
        cover: "images/3aaef7bf97a1ac0e18f4e82016b1044.jpg",
        preview: "images/3aaef7bf97a1ac0e18f4e82016b1044.jpg",
        summary: "服务设计展览中的增强现实互动演示。",
        details: {
            background: "这是服务设计展览中的增强现实互动演示。",
            problem: "观众如果只阅读展板，很容易停留在被动接收状态。我希望通过主动扫描和观看，让用户进入项目内容。",
            method: "项目将增强现实内容与展板视觉关联，并通过扫码、播放和现场测试调整展示效果。",
            role: "我参与互动配置、视觉素材衔接和演示录制。",
            result: "最终形成可用于展示的增强现实演示视频，也让我意识到交互应该服务叙事，而不是只增加技术形式。"
        },
        video: "videos/做的小ar.mp4",
        media: [
            { src: "videos/小游戏AR演示视频.mp4", caption: "互动演示视频", type: "video", size: "wide" },
            { src: "images/3aaef7bf97a1ac0e18f4e82016b1044.jpg", caption: "互动触点" },
            { src: "images/ef1c63008b961684711d4a16c22e552.jpg", caption: "展览现场" }
        ]
    }
];

const storyLabels = [
    ["项目背景", "background"],
    ["问题", "problem"],
    ["设计方法", "method"],
    ["个人职责", "role"],
    ["结果", "result"]
];

function mediaType(src, explicitType) {
    if (explicitType) return explicitType;
    return /\.(mp4|mov|webm)$/i.test(src) ? "video" : "image";
}

function renderMedia(item, eager = false) {
    const type = mediaType(item.src, item.type);
    if (type === "video") {
        return `
            <video class="fade-media" controls muted loop playsinline ${eager ? "autoplay" : "preload=\"metadata\""}>
                <source src="${item.src}">
                Your browser does not support the video tag.
            </video>
        `;
    }

    return `<img class="fade-media" src="${item.src}" alt="${item.caption || ""}" loading="${eager ? "eager" : "lazy"}" onerror="this.onerror=null;this.src='${PLACEHOLDER_IMG}';">`;
}

function renderDirectory() {
    const categories = ["Visual Design", "Brand Design", "Interaction Design"];
    const html = categories.map((category) => {
        const items = projects.filter((project) => project.category === category);
        if (!items.length) return "";

        const rows = items.map((project) => {
            const index = projects.indexOf(project);
            return `
                <button class="project-row" type="button" data-index="${index}">
                    <span class="project-number">${String(index + 1).padStart(2, "0")}</span>
                    <span class="project-name">${project.title}</span>
                    <span class="project-year">${project.year}</span>
                </button>
            `;
        }).join("");

        return `
            <section class="category-block">
                <div class="category-title">${category}<span class="category-count">${String(items.length).padStart(2, "0")}</span></div>
                <div class="project-list">${rows}</div>
            </section>
        `;
    }).join("");

    projectList.innerHTML = html;

    document.querySelectorAll(".project-row").forEach((row) => {
        row.addEventListener("click", () => openProject(Number(row.dataset.index)));
        row.addEventListener("mouseenter", (event) => handleProjectHover(row, event));
        row.addEventListener("mousemove", moveHoverPreview);
        row.addEventListener("mouseleave", clearProjectHover);
    });
}

function handleProjectHover(row, event) {
    const list = row.closest(".project-list");
    list.classList.add("is-hovering");
    row.classList.add("hovered");

    const project = projects[Number(row.dataset.index)];
    if (!project) return;
    hoverPreview.innerHTML = `<img src="${project.preview || project.cover}" alt="">`;
    hoverPreview.classList.add("active");
    moveHoverPreview(event);
}

function moveHoverPreview(event) {
    if (!hoverPreview.classList.contains("active")) return;
    const width = hoverPreview.offsetWidth || 250;
    const height = hoverPreview.offsetHeight || 188;
    const gap = 18;
    let left = event.clientX + gap;
    let top = event.clientY + gap;

    if (left + width + gap > window.innerWidth) {
        left = event.clientX - width - gap;
    }

    if (top + height + gap > window.innerHeight) {
        top = window.innerHeight - height - gap;
    }

    hoverPreview.style.left = `${Math.max(gap, left)}px`;
    hoverPreview.style.top = `${Math.max(gap, top)}px`;
}

function clearProjectHover() {
    document.querySelectorAll(".project-list.is-hovering").forEach((list) => list.classList.remove("is-hovering"));
    document.querySelectorAll(".project-row.hovered").forEach((row) => row.classList.remove("hovered"));
    hoverPreview.classList.remove("active");
}

function openProject(index) {
    activeIndex = index;
    const project = projects[index];
    clearProjectHover();
    stopPreviewCarousel();

    windowTitle.textContent = project.title;
    windowCategory.textContent = `${project.category} / ${project.year}`;
    mainMedia.innerHTML = renderMedia({ src: project.video || project.cover, caption: project.title }, true);
    mediaColumn.innerHTML = renderProjectMedia(project);
    projectText.innerHTML = renderProjectText(project);

    projectWindow.classList.add("active");
    projectWindow.classList.remove("expanded");
    moreButton.textContent = "(More...)";
    observeFadeMedia();
}

function renderProjectMedia(project) {
    return `
        <div class="media-grid">
            ${project.media.map((item) => `
                <figure class="media-figure ${item.size === "wide" ? "wide" : ""}">
                    ${renderMedia(item)}
                    <figcaption>${item.caption || ""}</figcaption>
                </figure>
            `).join("")}
        </div>
    `;
}

function renderProjectText(project) {
    return `
        <div class="project-heading">
            <h2>${project.title}</h2>
            <p>${project.summary}</p>
        </div>
        ${storyLabels.map(([label, key]) => `
            <section class="story-block">
                <div class="story-label">${label}</div>
                <p>${project.details[key]}</p>
            </section>
        `).join("")}
    `;
}

function toggleMore() {
    const shouldExpand = !projectWindow.classList.contains("expanded");
    projectWindow.classList.toggle("expanded", shouldExpand);
    moreButton.textContent = shouldExpand ? "(Less...)" : "(More...)";
    if (shouldExpand) {
        startPreviewCarousel(projects[activeIndex]);
    } else {
        stopPreviewCarousel();
        const project = projects[activeIndex];
        mainMedia.innerHTML = renderMedia({ src: project.video || project.cover, caption: project.title }, true);
    }
    observeFadeMedia();
}

function closeProject() {
    stopPreviewCarousel();
    if (projectWindow.classList.contains("expanded")) {
        projectWindow.classList.remove("expanded");
        moreButton.textContent = "(More...)";
        setTimeout(() => projectWindow.classList.remove("active"), 180);
        return;
    }

    projectWindow.classList.remove("active");
    setTimeout(() => {
        moreButton.textContent = "(More...)";
    }, 250);
}

function getPreviewCarouselItems(project) {
    const mediaItems = (project.media || [])
        .filter((item) => mediaType(item.src, item.type) === "image")
        .map((item) => ({ src: item.src, caption: item.caption || project.title }));

    return [{ src: project.cover, caption: project.title }, ...mediaItems]
        .filter((item, index, items) => items.findIndex((candidate) => candidate.src === item.src) === index);
}

function startPreviewCarousel(project) {
    stopPreviewCarousel();
    const items = getPreviewCarouselItems(project);
    if (items.length < 2) return;

    previewCarouselIndex = 0;
    previewCarouselInterval = setInterval(() => {
        previewCarouselIndex = (previewCarouselIndex + 1) % items.length;
        mainMedia.innerHTML = renderMedia(items[previewCarouselIndex], true);
        observeFadeMedia();
    }, 2400);
}

function stopPreviewCarousel() {
    if (previewCarouselInterval) {
        clearInterval(previewCarouselInterval);
        previewCarouselInterval = null;
    }
}

function observeFadeMedia() {
    if (activeMediaObserver) {
        activeMediaObserver.disconnect();
    }

    activeMediaObserver = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                entry.target.classList.add("in-view");
            }
        });
    }, {
        root: projectWindow.classList.contains("expanded") ? projectWindow : null,
        threshold: 0.14
    });

    document.querySelectorAll(".fade-media").forEach((item) => activeMediaObserver.observe(item));
}

moreButton.addEventListener("click", toggleMore);
textButton.addEventListener("click", toggleMore);
closeButton.addEventListener("click", closeProject);
closeButtonExpanded.addEventListener("click", closeProject);

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
        closeProject();
    }
});

renderDirectory();

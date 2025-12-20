// *** 默认占位图，用于图片加载失败或没有定义路径的项目 ***
const PLACEHOLDER_IMG = "https://picsum.photos/seed/default/800/500";

// --- 元素获取 & 全局变量 ---
const viewer = document.getElementById('project-viewer');
const mediaContentContainer = document.getElementById('media-content-container'); // NEW CONTAINER
const viewerTitle = document.getElementById('viewer-title');
const viewerArtist = document.getElementById('viewer-artist');
const btnMore = document.getElementById('btn-more');
const detailContentArea = document.getElementById('detail-content-area');

let carouselInterval = null;
let currentLoopImages = [];
let currentImgIndex = 0;
let currentMediaElement = null; // 用于追踪当前的媒体元素（img 或 video）

// --- 模拟数据 (请确保项目路径存在) ---
const projects = [
    { 
        title: "My First Lamp", 
        artist: "24.7.21", 
        img: "images/main_image.jpg", 
        loopImages: [
            "images/main_image.jpg",
            "images/loop_image_2.jpg",
        ],
        detailContent: {
            titleBlock: "Glo's first handcrafted lamp.",
            mainText: "The design draws inspiration from traditional Chinese elongated red lanterns, deconstructed structurally and primarily crafted from wood and herbaceous plants. Bamboo and rattan form the framework, overlaid with paper to diffuse a soft, hazy glow.",
            images: [
                { src: "images/detail_image_2.jpg", caption: "Three-view drawings" },
                { src: "images/loop_image_3.jpg", caption: "Creative inspiration." },
                { src: "images/IMG_7642(1).png", caption: "The flip side of elegance." },
            ]
        }
    },
    { 
        title: "I am like a moving tomb", 
        artist: "24.11.7---",
        img: "images/我似一座流动的坟墓.jpg",
        loopImages: [
            "images/我似一座流动的坟墓.jpg",
            "images/7dce6a88a35b75f738e0deec2de877f.jpg",
        ],
        detailContent: {
            titleBlock: "Sarcasm IS A BODY'S NATUAL DEFENSE AGAINST STUPIDITY", 
            mainText: "The regression of civilisation will ultimately affect every single one of us. If I stand idly by today, might I not say tomorrow: ‘Had I dared to speak out, my circumstances might be different now?’", 
            images: [
                { src: "images/7766.jpg", caption: "小画1" },
                { src: "images/小画_01.png", caption: "小画2" }
            ] 
        }
    },
    { 
        title: "Shadows in Motion", 
        artist: "24.3.7---",
        img: "images/图片444.png",
        loopImages: [
            "images/图片555.png",
            "images/图片444.png",
            "images/图片.jpg",
        ],
        detailContent: {
            titleBlock: "Starting from the pineapple", 
            mainText: "Inspired by the pineapple, observing its various forms and shadows, creating small works.", 
            images: [
                { src: "images/空间构成作品_03.png", caption: "参考1" },
                { src: "images/空间构成作品_07.png", caption: "参考2" },
                { src: "images/3322.png", caption: "图纸" }
            ] 
        }
    },
    { 
        title: "Photography", 
        artist: "24.9.30---",
        img: "images/打翻-戎誉.jpg",
        loopImages: [
            "images/打翻-戎誉.jpg",
            "images/激流-戎誉.jpg",
            "images/胶卷-戎誉.jpg",
        ],
        detailContent: {
            titleBlock: "Camera in hand", 
            mainText: "In my spare time, I like to go out for a stroll and capture moments with my camera.", 
            images: [
                { src: "images/戎誉3230101316 (7 - 14).jpg", caption: "1" },
                { src: "images/戎誉3230101316 (19 - 22).jpg", caption: "2" },
                { src: "images/打翻-戎誉.jpg", caption: "3" }
            ] 
        }
    },

    {
        title: "Service Innovation Design",
        artist: "25.1.1",
        img: "images/实物模型.jpg", 
        videoUrl: "videos/市集全貌展示视频.mp4", 
        loopImages: [], 
        detailContent: {
            titleBlock: "This project uses a video instead of a carousel.",
            mainText: "Service Innovation Design for the Game 'Nishuihan'",
            images: [
                { src: "images/展板.png", caption: "1" },
                { src: "images/05443e5ae51e39ada834f87970ef3b7.jpg", caption: "2" },
                { src: "images/3aaef7bf97a1ac0e18f4e82016b1044.jpg", videoSrc: "videos/小游戏AR演示视频.mp4", caption: "Detail Video 2" },
            ]
        }
    },
    
    // ... 其它项目 (使用占位图作为演示)
    { title: "Delicta-Epsum", artist: "Artist C", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Adamantes est The End", artist: "Artist D", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Sine Titulo", artist: "Artist E", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Ategire et Claudere", artist: "Artist F", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Sine Titulo", artist: "Artist G", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Façad: Saccharum", artist: "Artist H", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Nos Sumus (spatium interius)", artist: "Artist I", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Micare", artist: "Artist J", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    { title: "Omnia Ubique Simul", artist: "Artist K", img: PLACEHOLDER_IMG, detailContent: { titleBlock: "Title", mainText: "Default text.", images: [] }, loopImages: [PLACEHOLDER_IMG] },
    
    // --- Work 12: 左侧主视频演示 (保留) ---
    {
        title: "Video Demo Project",
        artist: "25.1.1",
        img: "images/video_poster.jpg", 
        videoUrl: "videos/sample_video.mp4", 
        loopImages: [], 
        detailContent: {
            titleBlock: "This project uses a video instead of a carousel.",
            mainText: "The video below demonstrates the main media area functionality. The video should autoplay (muted) and loop.",
            images: []
        }
    },
    
    // --- Work 13: 右侧详情页视频演示 (保留) ---
    {
        title: "Detail Video Showcase",
        artist: "25.1.2",
        img: "images/detail_video_poster.jpg", 
        loopImages: [PLACEHOLDER_IMG], 
        detailContent: {
            titleBlock: "Detail Page Video Test.",
            mainText: "This project demonstrates the ability to include video clips directly within the right-hand detail content stack, alongside images.",
            images: [
                // 图片
                { src: "images/detail_image_2.jpg", caption: "Static Image 1" },
                // 视频 (注意: 使用 videoSrc 属性, src 用作视频封面)
                { src: "images/video_poster.jpg", videoSrc: "videos/sample_detail_video_1.mp4", caption: "Detail Video 1" }, 
                // 图片
                { src: "images/loop_image_3.jpg", caption: "Static Image 2" },
                // 视频
                { src: "images/video_poster2.jpg", videoSrc: "videos/sample_detail_video_2.mp4", caption: "Detail Video 2" },
            ]
        }
    }
];

// --- 辅助函数：渲染右侧详情内容 (支持视频) ---
function renderDetails(content) {
    if (!content || !content.titleBlock) {
        detailContentArea.innerHTML = '';
        return;
    }

    // 1. 顶部左右结构 (标题块 + 正文块)
    const textContentHtml = `
        <div class="detail-top-section">
            <div class="detail-title-block">${content.titleBlock}</div>
            <div class="detail-main-text">${content.mainText.split('. ').map(p => p.trim() ? `<p>${p}.</p>` : '').join('')}</div>
        </div>
    `;

    // 2. 底部图片堆叠 (支持图片和视频)
    let imagesHtml = '';
    if (content.images && content.images.length > 0) {
        content.images.forEach(item => {
            let mediaTag;
            const caption = item.caption || '';
            
            if (item.videoSrc) {
                // 如果有 videoSrc 属性，渲染 <video> 标签
                mediaTag = `
                    <video controls loop muted poster="${item.src || PLACEHOLDER_IMG}">
                        <source src="${item.videoSrc}" type="video/mp4">
                        Your browser does not support the video tag.
                    </video>
                `;
            } else {
                // 否则渲染 <img> 标签 (使用 src)
                mediaTag = `
                    <img src="${item.src}" alt="${caption}" onerror="this.onerror=null;this.src='${PLACEHOLDER_IMG}';">
                `;
            }
            
            imagesHtml += `
                <div class="image-block">
                    ${mediaTag}
                    <div class="image-caption">${caption}</div>
                </div>
            `;
        });
    }
    const imageStackHtml = `<div class="detail-image-stack">${imagesHtml}</div>`;
    
    detailContentArea.innerHTML = textContentHtml + imageStackHtml;
}

// --- 轮播控制 (保持不变) ---
function stopCarousel() {
    if (carouselInterval) {
        clearInterval(carouselInterval);
        carouselInterval = null;
    }
}

function startCarousel() {
    stopCarousel();
    if (currentLoopImages.length > 1 && currentMediaElement && currentMediaElement.tagName === 'IMG') {
        carouselInterval = setInterval(() => {
            currentImgIndex = (currentImgIndex + 1) % currentLoopImages.length;
            currentMediaElement.src = currentLoopImages[currentImgIndex];
            currentMediaElement.onerror = function() { this.onerror=null; this.src=PLACEHOLDER_IMG; }; 
        }, 2500);
    }
}

// --- 交互控制 (保持不变) ---

function openProject(index) {
    const data = projects[index % projects.length];
    
    // 1. 清理并设置媒体内容
    mediaContentContainer.innerHTML = '';
    stopCarousel();
    currentMediaElement = null;

    if (data.videoUrl) {
        // --- 视频处理 (左侧主区域) ---
        const videoElement = document.createElement('video');
        videoElement.setAttribute('controls', '');
        videoElement.setAttribute('autoplay', '');
        videoElement.setAttribute('loop', '');
        videoElement.setAttribute('muted', '');
        videoElement.setAttribute('poster', data.img || PLACEHOLDER_IMG);
        videoElement.style.cssText = 'width: 100%; height: auto; display: block;';
        
        const sourceElement = document.createElement('source');
        sourceElement.setAttribute('src', data.videoUrl);
        sourceElement.setAttribute('type', 'video/mp4');
        
        videoElement.appendChild(sourceElement);
        videoElement.appendChild(document.createTextNode('您的浏览器不支持视频标签。'));
        mediaContentContainer.appendChild(videoElement);
        
        videoElement.play().catch(error => {});

    } else {
        // --- 图片/轮播图处理 (左侧主区域) ---
        const imgElement = document.createElement('img');
        imgElement.src = data.img || PLACEHOLDER_IMG;
        imgElement.alt = "Project Image";
        imgElement.onerror = function() { this.onerror=null; this.src=PLACEHOLDER_IMG; };
        
        mediaContentContainer.appendChild(imgElement);
        currentMediaElement = imgElement;

        currentLoopImages = data.loopImages && data.loopImages.length > 0 ? data.loopImages : [data.img];
        currentImgIndex = 0;
        startCarousel();
    }

    // 2. 更新文字内容和详情页
    viewerTitle.innerText = data.title;
    viewerArtist.innerText = data.artist;
    renderDetails(data.detailContent); 

    // 3. 显示 Viewer
    viewer.classList.add('active');
    
    // 确保 More 状态是关闭的
    viewer.classList.remove('expanded');
    btnMore.innerText = "(More...)";
}

function toggleMore() {
    if (viewer.classList.contains('expanded')) {
        viewer.classList.remove('expanded');
        btnMore.innerText = "(More...)";
    } else {
        viewer.classList.add('expanded');
        btnMore.innerText = "(Less...)";
    }
    // 滚动到顶部，确保用户看到右侧详情
    document.querySelector('.viewer-right').scrollTo(0, 0); 
    
    // 当详情页展开时，尝试让其中的所有视频开始播放
    if (viewer.classList.contains('expanded')) {
        document.querySelectorAll('.detail-image-stack video').forEach(video => {
            // 确保静音，以便在详情页展开时浏览器允许播放
            video.muted = true;
            video.play().catch(e => {
                // console.log("Detail video autoplay blocked:", e);
            });
        });
    }
}

function closeProject() {
    viewer.classList.remove('active');
    stopCarousel();
    setTimeout(() => {
        viewer.classList.remove('expanded');
        btnMore.innerText = "(More...)";
    }, 400);
}

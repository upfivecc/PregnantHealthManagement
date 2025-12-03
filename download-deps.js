const fs = require('fs');
const path = require('path');
const https = require('https');

// 创建目录
const cssDir = path.join(__dirname, 'src', 'main', 'resources', 'static', 'lib', 'css');
const jsDir = path.join(__dirname, 'src', 'main', 'resources', 'static', 'lib', 'js');

if (!fs.existsSync(cssDir)) {
  fs.mkdirSync(cssDir, { recursive: true });
}

if (!fs.existsSync(jsDir)) {
  fs.mkdirSync(jsDir, { recursive: true });
}

// 下载文件的函数
function downloadFile(url, dest) {
  return new Promise((resolve, reject) => {
    console.log(`正在下载 ${url} 到 ${dest}`);
    
    const file = fs.createWriteStream(dest);
    https.get(url, (response) => {
      if (response.statusCode !== 200) {
        reject(new Error(`下载失败，状态码: ${response.statusCode}`));
        return;
      }
      
      response.pipe(file);
      file.on('finish', () => {
        file.close();
        console.log(`下载完成: ${dest}`);
        resolve();
      });
    }).on('error', (err) => {
      fs.unlink(dest, () => {}); // 删除部分下载的文件
      reject(err);
    });
  });
}

// 定义要下载的文件
const filesToDownload = [
  {
    url: 'https://cdn.jsdelivr.net/npm/element-ui@2.15.13/lib/theme-chalk/index.css',
    dest: path.join(cssDir, 'element-ui.css')
  },
  {
    url: 'https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js',
    dest: path.join(jsDir, 'vue.js')
  },
  {
    url: 'https://cdn.jsdelivr.net/npm/element-ui@2.15.13/lib/index.js',
    dest: path.join(jsDir, 'element-ui.js')
  },
  {
    url: 'https://cdn.jsdelivr.net/npm/axios@0.27.2/dist/axios.min.js',
    dest: path.join(jsDir, 'axios.min.js')
  }
];

// 执行下载
async function downloadAllFiles() {
  try {
    for (const file of filesToDownload) {
      await downloadFile(file.url, file.dest);
    }
    console.log('所有文件下载完成！');
  } catch (error) {
    console.error('下载过程中出现错误:', error.message);
  }
}

// 开始下载
downloadAllFiles();
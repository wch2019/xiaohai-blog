// 提取文件后缀的函数
export function getFileExtension(fileName) {
  // 使用正则表达式匹配字符串中的文件后缀
  const match = fileName.match(/\.([a-z0-9]+)$/i)
  if (match) {
    return match[1] // 返回匹配到的文件后缀
  } else {
    return null // 如果未找到文件后缀，则返回 null
  }
}

export function getFileAddress(inputValue) {
  return process.env.VUE_APP_BASE_API_FILE + inputValue
}

// Markdown 格式
export function getMarkdownAddress(fileName, filePath) {
  return '![' + fileName + '](' + filePath + ')'
}

export function createFileDownload(filename, content) {
  const blob = new Blob([content], { type: 'application/octet-stream' })
  const url = window.URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.style.display = 'none'
  a.href = url
  a.download = filename // 设置文件名
  document.body.appendChild(a)
  a.click()
  window.URL.revokeObjectURL(url)
}

export function downloadFile(filename, fileURL) {
  const link = document.createElement('a')
  link.style.display = 'none'
  link.href = fileURL
  link.download = filename // 设置文件名
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

// 验证是否是图片类型
export function VerifyIsPictureType(name) {
  const acceptedImageTypes = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
  return acceptedImageTypes.indexOf(name) !== -1
}

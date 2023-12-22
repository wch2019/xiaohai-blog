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

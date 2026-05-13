import { IMG_BASE } from './config.js'

/**
 * 将相对路径转为完整URL
 * @param {string} path - 图片路径，如 /uploads/pet-1.jpg
 * @returns {string} 完整URL
 */
export function getImageUrl(path) {
	if (!path) return '/static/logo.png'
	if (path.startsWith('http')) return path
	return IMG_BASE + path
}

/**
 * 将相对路径转为完整URL（数组）
 */
export function getImageUrls(paths) {
	if (!paths || !paths.length) return []
	return paths.map(p => getImageUrl(p))
}

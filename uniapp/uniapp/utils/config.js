/**
 * 环境配置
 * 切换环境只需修改 ENV 的值：
 *   'dev'  → 开发环境（连接本地后端）
 *   'prod' → 生产环境（连接线上后端）
 */
const ENV = 'prod'

/** 各环境后端地址 */
const BASE_URL_MAP = {
	dev: 'http://localhost:8080',
	prod: 'https://wkctqvunclym.sealosbja.site'
}

/** 当前环境的图片基础地址 */
const IMG_BASE_MAP = {
	dev: 'http://localhost:8080',
	prod: 'https://wkctqvunclym.sealosbja.site'
}

export const BASE_URL = BASE_URL_MAP[ENV]
export const IMG_BASE = IMG_BASE_MAP[ENV]
export default { BASE_URL, IMG_BASE }

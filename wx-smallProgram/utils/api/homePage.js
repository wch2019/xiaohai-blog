import instance from '../../utils/request'

// 首页内容
export function reqSwiperData(data){
	return instance.request({
		url: '/home/show/articles',
		method: 'get',
		data
	})
}
// 标签
export function getCategory(data){
	return instance.request({
		url: '/home/show/category',
		method: 'get',
	})
}

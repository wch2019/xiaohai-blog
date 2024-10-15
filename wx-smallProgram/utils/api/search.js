import instance from '../../utils/request'

// 首页内容
export function getSearch(data){
    return instance.request({
        url: '/home/show/search',
        method: 'get',
        data
    })
}

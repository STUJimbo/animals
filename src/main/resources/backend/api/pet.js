// 查询列表接口
const getDishPage = (params) => {
  return $axios({
    url: '/pet/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteDish = (ids) => {
  return $axios({
    url: '/pet',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
const editDish = (params) => {
  return $axios({
    url: '/pet',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addDish = (params) => {
  return $axios({
    url: '/pet',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryDishById = (id) => {
  return $axios({
    url: `/pet/${id}`,
    method: 'get'
  })
}

// 获取分类列表
const getCategoryList = (params) => {
  return $axios({
    url: '/type/list',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const dishStatusByStatus = (params) => {
  return $axios({
    url: `/pet/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}

const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  permission: state => state.user.permission,
  menu: state => state.user.menu,
  roles: state => state.user.roles,
  userId: state => state.user.userId,
  dict: state => state.dict.dict,
  permission_routes: state => state.permission.routes
}
export default getters

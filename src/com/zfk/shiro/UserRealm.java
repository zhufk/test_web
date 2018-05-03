package com.zfk.shiro;

import com.zfk.service.UserService;

public class UserRealm {//extends AuthorizingRealm {
//    // 用户对应的角色信息与权限信息都保存在数据库中，通过UserService获取数据
//    private UserService userService = new UserServiceImpl();
//
//    /**
//     * 提供用户信息返回权限信息
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = (String) principals.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        // 根据用户名查询当前用户拥有的角色
//        Set<Role> roles = userService.findRoles(username);
//        Set<String> roleNames = new HashSet<String>();
//        for (Role role : roles) {
//            roleNames.add(role.getRole());
//        }
//        // 将角色名称提供给info
//        authorizationInfo.setRoles(roleNames);
//        // 根据用户名查询当前用户权限
//        Set<Permission> permissions = userService.findPermissions(username);
//        Set<String> permissionNames = new HashSet<String>();
//        for (Permission permission : permissions) {
//            permissionNames.add(permission.getPermission());
//        }
//        // 将权限名称提供给info
//        authorizationInfo.setStringPermissions(permissionNames);
//
//        return authorizationInfo;
//    }
//
//    /**
//     * 提供账户信息返回认证信息
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String username = (String) token.getPrincipal();
//        User user = userService.findByUsername(username);
//        if (user == null) {
//            // 用户名不存在抛出异常
//            throw new UnknownAccountException();
//        }
//        if (user.getLocked() == 0) {
//            // 用户被管理员锁定抛出异常
//            throw new LockedAccountException();
//        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),
//                user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
//        return authenticationInfo;
//    }
}
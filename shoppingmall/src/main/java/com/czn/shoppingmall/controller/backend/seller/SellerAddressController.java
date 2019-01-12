package com.czn.shoppingmall.controller.backend.seller;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Address;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IAddressService;
import com.czn.shoppingmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/seller/address/")
public class SellerAddressController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IAddressService iAddressService;

    @RequestMapping("add")
    public ServerResponse add(Address address, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("不是商家用户,不能在商家端添加地址");
        }
        address.setUserId(currentUser.getId());
        address.setRole(currentUser.getRole());
        return iAddressService.add(address);
    }

    @RequestMapping("query")
    public ServerResponse select(Integer addressId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("不是商家用户,不能查看商家地址");
        }
        return iAddressService.select(addressId,currentUser.getId());
    }

    @RequestMapping("list")
    public ServerResponse list(HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("不是商家用户,不能查看商家地址");
        }
        return iAddressService.list(currentUser.getId());
    }

    @RequestMapping("update")
    public ServerResponse update(Address address, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("不是商家用户,不能更新商家地址");
        }
        address.setUserId(currentUser.getId());
        address.setRole(currentUser.getRole());
        return iAddressService.update(address);
    }

    @RequestMapping("delete")
    public ServerResponse delete(Integer addressId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("不是商家用户,不能删除商家地址");
        }
        return iAddressService.delete(addressId, currentUser.getRole(), currentUser.getId());
    }
}

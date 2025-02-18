package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.YonghuEntity;
import com.cl.entity.view.YonghuView;

import com.cl.service.YonghuService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;
import com.cl.entity.WxLoginParam;
import com.cl.utils.WechatUtil;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 用户
 * 后端接口
 * @author 
 * @email 
 * @date 2024-11-15 11:20:22
 */
@RestController
@RequestMapping("/yonghu")
public class YonghuController {
    @Autowired
    private YonghuService yonghuService;



	@Autowired
	private TokenService tokenService;
	
	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", username));
        if(u==null || !u.getMima().equals(password)) {
            return R.error("账号或密码不正确");
        }
		String token = tokenService.generateToken(u.getId(), username,"yonghu",  "用户" );
		return R.ok().put("token", token);
	}


    /**
     * 微信登录
     */
    @RequestMapping(value = "/wx/login")
    @IgnoreAuth
    public R wxLogin(@RequestBody WxLoginParam param) {
        String token = null;
        //接收小程序发送的code
        com.alibaba.fastjson.JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(param.getCode());

        //接收微信接口服务  获取返回的参数
        String openId = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        if (StringUtils.isBlank(openId) && StringUtils.isBlank(sessionKey)) {
            return R.error("接口请求失败！");
        }

        com.alibaba.fastjson.JSONObject object = WechatUtil.getUserInfo(param.getEncryptedData(), sessionKey, param.getIv());
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(object));

        //校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(param.getRawData() + sessionKey);
        if (!param.getSignature().equals(signature2)) {
            return R.error("签名校验失败");
        }

        YonghuEntity user = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("openid", openId));
        if (user == null) {
            return R.error("请登录账号绑定微信后再进行微信登录。");
        } else {
            //已绑定，登录成功
            token = tokenService.generateToken(user.getId(), user.getZhanghao(),"yonghu", "用户");
        }

        return R.ok().put("token", token);
    }

    /**
     * 微信账号绑定
     */
    @RequestMapping(value = "/wx/bind")
    public R wxBind(@RequestBody WxLoginParam param , HttpServletRequest request){
        //接收小程序发送的code
        com.alibaba.fastjson.JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(param.getCode());

        //接收微信接口服务  获取返回的参数
        String openId = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        if (StringUtils.isBlank(openId) && StringUtils.isBlank(sessionKey)) {
            return R.error("接口请求失败！");
        }

        com.alibaba.fastjson.JSONObject object = WechatUtil.getUserInfo(param.getEncryptedData(), sessionKey, param.getIv());
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(object));

        //校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(param.getRawData() + sessionKey);
        if (!param.getSignature().equals(signature2)) {
            return R.error("签名校验失败");
        }
        String rawData = param.getRawData();
        com.alibaba.fastjson.JSONObject rawDataJson = com.alibaba.fastjson.JSON.parseObject(rawData);
        YonghuEntity user = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("openid", openId));
        if (user == null) {
            Long id = (Long)request.getSession().getAttribute("userId");
            user = yonghuService.selectById(id);
            if(user!=null) {
                user.setOpenid(openId);
            }
            yonghuService.updateById(user);
        } else {
            return R.error("账号已被绑定");
        }
        return R.ok("绑定成功");
    }

    /**
     * 微信账号解绑
     */
    @RequestMapping(value = "/wx/unbind")
    public R wxUnbind(HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("userId");
        YonghuEntity user = yonghuService.selectById(id);
        if(StringUtils.isNotBlank(user.getOpenid())) {
            user.setOpenid("");
        } else {
            return R.error("账号已解绑");
        }
        yonghuService.updateById(user);
        return R.ok("解绑成功");
    }
	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody YonghuEntity yonghu){
    	//ValidatorUtils.validateEntity(yonghu);
                            YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", yonghu.getZhanghao()));
                                                                                                                                                            		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		yonghu.setId(uId);
        yonghuService.insert(yonghu);
        return R.ok();
    }

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        return R.ok().put("data", yonghuService.selectView(new EntityWrapper<YonghuEntity>().eq("id", id)));
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        u.setMima("123456");
        yonghuService.updateById(u);
        return R.ok("密码已重置为：123456");
    }




    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YonghuEntity yonghu,
		HttpServletRequest request){
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();

		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));

        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,YonghuEntity yonghu, 
		HttpServletRequest request){
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();

		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YonghuEntity yonghu){
       	EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
        return R.ok().put("data", yonghuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YonghuEntity yonghu){
        EntityWrapper< YonghuEntity> ew = new EntityWrapper< YonghuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
		YonghuView yonghuView =  yonghuService.selectView(ew);
		return R.ok("查询用户成功").put("data", yonghuView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
		yonghu = yonghuService.selectView(new EntityWrapper<YonghuEntity>().eq("id", id));
        return R.ok().put("data", yonghu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
		yonghu = yonghuService.selectView(new EntityWrapper<YonghuEntity>().eq("id", id));
        return R.ok().put("data", yonghu);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("zhanghao", yonghu.getZhanghao()))>0) {
            return R.error("账号已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(yonghu);
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", yonghu.getZhanghao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		yonghu.setId(new Date().getTime());
        yonghuService.insert(yonghu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("zhanghao", yonghu.getZhanghao()))>0) {
            return R.error("账号已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(yonghu);
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", yonghu.getZhanghao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		yonghu.setId(new Date().getTime());
        yonghuService.insert(yonghu);
        return R.ok();
    }

     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        YonghuEntity yonghu = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("zhanghao", username));
        return R.ok().put("data", yonghu);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yonghu);
        yonghuService.updateById(yonghu);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        yonghuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






    /**
     * 总数量
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,YonghuEntity yonghu, HttpServletRequest request){
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
        int count = yonghuService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        return R.ok().put("data", count);
    }



}

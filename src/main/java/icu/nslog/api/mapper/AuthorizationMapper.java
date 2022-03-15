package icu.nslog.api.mapper;

import icu.nslog.api.bean.OpenApiAuthorizationDetails;
import icu.nslog.api.bean.UserAuthorizationDetails;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Mapper
public interface AuthorizationMapper {

    @Select("select * from user where username=#{username}")
    UserAuthorizationDetails qryUserEntityByUsername(@Param("username")String username);

//    @Select("SELECT u.id, u.username , u.password , u.source, a.app_id , a.secret, u.accountNonLocked, " +
//            "u.credentialsNonExpired , u.credentialsNonExpired , u.enabled FROM access a left join `user` u " +
//            "on a.user_id = u.id where a.app_id = #{appId}")
    @Select("select a.id, a.app_id, a.secret, u.accountNonExpired, u.accountNonLocked, u.credentialsNonExpired, " +
            "u.enabled FROM access a join `user` u on u.id = a.user_id " +
            "where a.app_id = #{appId}")
    @Results(value = {
            @Result(column = "app_id", property = "appId"),
    })
    UserAuthorizationDetails qryUserEntityByUserId(@Param("userId")String userId);

    @Select("SELECT r1.name FROM `role` r1 join user_role r2 on r1.id = r2.role_id WHERE r2.user_id = #{userId}")
    List<String> qryUserRolesByUserId(@Param("userId") BigInteger userId);


    @Select("select a.id, a.app_id, a.secret, u.accountNonExpired, u.accountNonLocked, u.credentialsNonExpired, " +
            "u.enabled FROM access a join `user` u on u.id = a.user_id " +
            "where a.app_id = #{appId}")
    @Results(value = {
            @Result(column = "app_id", property = "appId"),
    })
    OpenApiAuthorizationDetails qryBasicAuthorizationByAppId(@Param("appId")String appId);

    @Select("select name from access_role ar join `role` r on ar.role_id=r.id where access_id = #{accessId}")
    List<String> qryOpenApiRolesByAccessId(@Param("accessId") BigInteger accessId);
}

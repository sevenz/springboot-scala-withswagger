package com.bob.scala.webapi.controller

import javax.validation.Valid
import javax.validation.constraints.{Max, Min, NotNull}

import io.swagger.annotations._
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._

@ApiModel("用户基本属性")
case class User(@ApiModelProperty("姓名") name: String,
                @ApiModelProperty("年纪") age: Int,
                @ApiModelProperty("地址") address: String,
                @ApiModelProperty("性别") sex: Int)

@ApiModel("添加用户基本信息参数")
case class AddUserParam() {
  @ApiModelProperty("姓名")
  @NotNull
  var name: String = _;
  @ApiModelProperty("年纪")
  @Min(value = 1l, message = "年纪最少不能少于1")
  @Max(value = 100l, message = "年纪最大不能大于100")
  var age: Int = _;
  @ApiModelProperty("地址")
  @NotNull
  var address: String = _;
  @ApiModelProperty("性别")
  var sex: Int = _;
}

class BusinessExceptionResponse


/**
 * Created by bob on 16/2/27.
 */
@RestController
@RequestMapping(value = Array("users/v1"))
@Api(value = "用户相关接口", description = "用户相关接口")
class UserController {

  @RequestMapping(value = Array("lists"), method = Array(RequestMethod.GET))
  @ApiOperation(value = "列出系统中所有用户信息")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(Array(
    new ApiResponse(code = 401, message = "无权限操作", response = classOf[BusinessExceptionResponse]),
    new ApiResponse(code = 404, message = "没有处理器", response = classOf[BusinessExceptionResponse]),
    new ApiResponse(code = 204, message = "记录不存在")))
  def lists(): java.util.List[User] = {
    val aUser = new User("c", 4, "a44", 4)
    val aList = List(new User("a", 1, "a11", 1), new User("b", 2, "b22", 2), new User("c", 3, "c33", 3))
    aList.+:(aUser).asJava
  }

  @RequestMapping(value = Array("lists/{name}"), method = Array(RequestMethod.GET))
  @ApiOperation("根据姓名查找用户")
  def findByName(@PathVariable("name") @ApiParam("用户姓名") name: String): User = {
    User(name, 4, "a44", 4)
  }

  @RequestMapping(value = Array("lists"), method = Array(RequestMethod.POST))
  @ApiOperation("创建一个用户")
  def createUser(@Valid @RequestBody param: AddUserParam): User = {
    User(param.name, param.age, param.address,
      param.age)
  }
}
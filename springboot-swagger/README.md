# springboot集成swagger2
访问地址：http://localhost:8080/swagger-ui.html

### swagger常用注解API说明
* @Api：修饰整个类，描述Controller的作用
* @ApiOperation：描述一个类的一个方法，或者说一个接口
* @ApiIgnore：使用该注解忽略这个API
* @ApiImplicitParam：一个请求参数 用在@ApiImplicitParams的方法里边
* @ApiImplicitParams 多个请求参数 用在controller的方法上


##### @Api
Api 用在类上，说明该类的作用。可以标记一个Controller类做为swagger 文档资源，使用方式：
```
@Api(value = "/user", description = "Operations about user")
```
与Controller注解并列使用。 属性配置：
* value	url的路径值
* tags	如果设置这个值、value的值会被覆盖
* description	对api资源的描述
* basePath	基本路径可以不配置
* position	如果配置多个Api 想改变显示的顺序位置
* produces	For example, "application/json, application/xml"
* consumes	For example, "application/json, application/xml"
* protocols	Possible values: http, https, ws, wss.
* authorizations	高级特性认证时配置
* hidden	配置为true 将在文档中隐藏

在SpringMvc中的配置如下：
```
@Controller
@RequestMapping(value = "/api/pet", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
@Api(value = "/pet", description = "Operations about pets")
public class PetController {

}
```

##### @ApiImplicitParam
* value 接收参数的意义描述
* name 接收参数名
* required 参数是否必填
* defaultValue 默认值
* dataType 参数的数据类型 只作为标志说明，并没有实际验证
* paramType 查询参数类型
  * path 以地址的形式提交数据
  * query 直接跟参数完成自动映射赋值
  * body 以流的形式提交 仅支持POST
  * header 参数在request headers 里边提交
  * form 以form表单的形式提交 仅支持POST


### 参考
[SpringBoot非官方教程 | 第十一篇：springboot集成swagger2，构建优雅的Restful API](http://blog.csdn.net/forezp/article/details/71023536)

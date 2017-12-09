# springboot-i18n

## 页面元素国际化
首先我们先定义国际化资源文件，spring boot默认就支持国际化的，而且不需要你过多的做什么配置，只需要在resources/下定义国际化配置文件即可，注意名称必须以messages开发。

我们定义如下几个文件：
* messages.properties （默认，当找不到语言的配置的时候，使用该文件进行展示）。
* messages_zh_CN.properties（中文）
* messages_en_US.properties（英文）

具体的代码如下：

messages.properties
```
hello=hello
```
messages_en_US.properties
```
hello=hello
```
messages_zh_CN.properties
```
hello=你好
```
配置好之后，动态模板页使用#{key}的方式进行使用messages中的字段信息
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>玩转spring boot——国际化</title>
</head>
<body>
<h1>玩转spring boot——国际化</h1>
<br />
<br />
<a href="/?lang=en_US">English(US)</a>
<a href="/?lang=zh_CN">简体中文</a>
<br />
<h3 th:text="#{hello}"></h3>
</body>
</html>
```

## springboot 国际化原理说明
**第一个问题，为什么命名必须是messages开头，需要看一个源码文件：**

org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration：
```这里提取部分代码：

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link MessageSource}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Eddú Meléndez
 */
@Configuration
@ConditionalOnMissingBean(value = MessageSource.class, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Conditional(ResourceBundleCondition.class)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.messages")
public class MessageSourceAutoConfiguration {
 
    private static final Resource[] NO_RESOURCES = {};
 
    /**
     * Comma-separated list of basenames, each following the ResourceBundle convention.
     * Essentially a fully-qualified classpath location. If it doesn't contain a package
     * qualifier (such as "org.mypackage"), it will be resolved from the classpath root.
     */
    private String basename = "messages";
 
    /**
     * Message bundles encoding.
     */
    private Charset encoding = Charset.forName("UTF-8");
 
    /**
     * Loaded resource bundle files cache expiration, in seconds. When set to -1, bundles
     * are cached forever.
     */
    private int cacheSeconds = -1;
 
    /**
     * Set whether to fall back to the system Locale if no files for a specific Locale
     * have been found. if this is turned off, the only fallback will be the default file
     * (e.g. "messages.properties" for basename "messages").
     */
    private boolean fallbackToSystemLocale = true;
```
看到没有，如果我们没有在application.properties中配置spring.messages属性，那么使用默认的messages，好了这个问题就这么简单解决了。

**第二问题：为什么我看到的是中文（或者英文）呢？**

为了让web应用程序支持国际化，必须识别每个用户的首选区域，并根据这个区域显示内容。
在Spring MVC应用程序中，用户的区域是通过区域解析器来识别的，它必须是实现LocaleResolver接口。
Spring MVC提供了几个LocaleResolver实现，让你可以按照不同的条件来解析区域。
除此之外，你还可以实现这个接口创建自己的区域解析器。
如果没有做特殊的处理的话，Spring采用的默认区域解析器是AcceptHeaderLocaleResolver。
它通过检验HTTP请求的头部信息accept-language来解析区域。
这个头部是由用户的web浏览器底层根据底层操作系统的区域设置进行设定的。
> 请注意，这个区域解析器无法改变用户的区域，因为它无法修改用户操作系统的区域设置。
> 各种浏览器可以修改区域语音，具体方法自行百度。


## 修改默认messages配置前缀
我们在上面说了，默认的文件名称前缀是messages_xx.properties，那么如何修改这个名称和路径呢？

这个也很简单，只需要修改application.properties文件即可加入如下配置：
```
########################################################
### i18n setting.
########################################################
#指定message的basename，多个以逗号分隔，如果不加包名的话，默认从classpath路径开始，默认: messages
spring.messages.basename=i18n/messages
#设定加载的资源文件缓存失效时间，-1的话为永不过期，默认为-1
spring.messages.cache-seconds= 3600
#设定Message bundles的编码，默认: UTF-8
#spring.messages.encoding=UTF-8
```
上面各个参数都注释很清楚了，这里不多说了，那么我们这里是把文件放到了i18n下，
那么我们在resources下新建目录i18n，然后复制我们创建的messages_xxx.properties文件到此目录下。
为了区分这是读取了新的文件，我们可以在每个文件中—i18n以进行区分。

## 代码中如何获取国际化信息
以上讲的是在模板文件进行国际化，那么在代码中如何获取到messages中的值呢，这个比较简单，只要在需要的地方注入类：
```
import org.springframework.context.MessageSource;
...
@Autowired
private MessageSource messageSource;
```

那么怎么使用了，在使用前我们需要先知道一个知识点，如何得到当前请求的Locale

那么怎么获取呢，有两种获取方式：

第一种方式是：
```
Locale locale = LocaleContextHolder.getLocale();
```
第二种方式是：
```
Locale locale= RequestContextUtils.getLocale(request);
```
个人喜好第一种方式，因为不需要什么参数就可以获取到，第二种方式依赖于当前的request请求对象。

有了当前请求的Locale剩下的就简单了：
```
String msg = messageSource.getMessage("world", null,locale);
```

通过以上代码的其中一种方式就可以获取到messages_xxx.properties文件配置的welcome属性值了。

## 区域解析器之AcceptHeaderLocaleResolver；
看到上面，大家会认为国际化也就到此了，但是我告诉大家这之后才是spring的强大之处呢，考虑的多么周到呢。
   
我们在之前说过，我们只所以可以看到国际化的效果是因为有一个区域解析器在进行处理。默认的区域解析器就是AcceptHeaderLocaleResolver。我们简单说明这个解析器：
   
Spring采用的默认区域解析器是AcceptHeaderLocaleResolver。它通过检验HTTP请求的accept-language头部来解析区域。这个头部是由用户的web浏览器根据底层操作系统的区域设置进行设定。请注意，这个区域解析器无法改变用户的区域，因为它无法修改用户操作系统的区域设置。
   
好了，这个默认的介绍到这里，因为这个我们无法改变，所以这种默认的设置在实际中使用的比较少。所以你如果看到当前还无法满足的需求的话，那么接着往下看，博主已经帮你都想到了。

## 会话区域解析器之SessionLocaleResolver；
会话区域解析器也就是说，你设置完只针对当前的会话有效，session失效，还原为默认状态。那么这个具体怎么操作呢？
具体操作起来也是很简单的，我们需要在我们的启动类App.java（按你的实际情况进行修改）配置区域解析器为SessionLocaleResolver，具体代码如下：
```
      @Bean
       public LocaleResolver localeResolver() {
           SessionLocaleResolver slr = new SessionLocaleResolver();
           //设置默认区域,
           slr.setDefaultLocale(Locale.CHINA);
           return slr;
       }
```
其实到这里我们就完事了，你可以通过setDefaultLocale()设置默认的语言，启动就访问页面进行查看，是不是已经实现国际化了。
   
到这里当然还不是很完美，还需要在进一步的优化了，那么怎么在页面中进行切换呢？那么假设页面上有两个按钮【切换为中文】、【切换为英文】就可以进行切换了。接下来一起来实现下：在hello.html添加如下代码：
```
   <form action="/changeSessionLanauage" method="get">
          <input name="lang" type="hidden" value="zh"  />
          <button>切换为中文</button>
       </form>
      
       <form action="/changeSessionLanauage" method="get">
          <input name="lang" type="hidden" value="en" />
          <button>切换为英文</button>
       </form>
```
这里就是两个表单，切换语言，那么/changeSessionLanauage怎么编写呢，看如下代码：
```
   @RequestMapping("/changeSessionLanauage")
       public String changeSessionLanauage(HttpServletRequest request,String lang){
          System.out.println(lang);
          if("zh".equals(lang)){
              //代码中即可通过以下方法进行语言设置
              request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN")); 
          }elseif("en".equals(lang)){
              //代码中即可通过以下方法进行语言设置
              request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US")); 
          }
          return "redirect:/hello";
       }
```
这部分代码最核心的部分就是如何设置会话的区域，也就是如下代码：
```
   request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
```
这个代码就可以把当前会话的区域进行切换了，是不是很简单。以上代码你会发现只针对会话的设置，我们在这里在优化下，针对下面讲的Cookie也会作用到，这样这个代码就很智能了，代码修改为如下：
```
   @RequestMapping("/changeSessionLanauage")
       public String changeSessionLanauage(HttpServletRequest request,HttpServletResponse response,String lang){
                 System.out.println(lang);
          LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
          if("zh".equals(lang)){
              localeResolver.setLocale(request, response, new Locale("zh", "CN"));
          }elseif("en".equals(lang)){
              localeResolver.setLocale(request, response, new Locale("en", "US"));
          }
          return "redirect:/hello";
       }
```
在这里使用:
```
   LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
```
获取当前使用的区域解析器LocaleResolver调用里面的方法setLocale设置即可，这样的代码就是不管是会话还是cookie区域解析器都是一样的代码了。


## Cookie区域解析器之CookieLocaleResolver；
Cookie区域解析器也就是说，你设置完针对cookie生效，session失效。那么这个具体怎么操作呢？我们需要在我们的启动类App.java（按你的实际情况进行修改）配置区域解析器为CookieLocaleResolver（SessionLocaleResolver部分请注释掉），具体代码如下：
```
   @Bean
       public LocaleResolver localeResolver() {
          CookieLocaleResolver slr = new CookieLocaleResolver();
           //设置默认区域,
           slr.setDefaultLocale(Locale.CHINA);
           slr.setCookieMaxAge(3600);//设置cookie有效期.
           return slr;
       }
```
其实到这里我们就完事了，你可以通过setDefaultLocale()设置默认的语言，启动就访问http://127.0.0.1:8080/hello 进行查看，是不是已经实现国际化了。


## 固定的区域解析器之FixedLocaleResolver ；
一直使用固定的Local, 改变Local 是不支持的 。既然无法改变，那么不…好了，还是简单介绍下如何使用吧，还是在App.java进行编码：
```
   /**
        * cookie区域解析器;
        * @return
        */
       @Bean
       public LocaleResolver localeResolver() {
          FixedLocaleResolver slr = new FixedLocaleResolver ();
           //设置默认区域,
           slr.setDefaultLocale(Locale.US);
           returnslr;
       }
```


## 使用参数修改用户的区域；
除了显式调用LocaleResolver.setLocale()来修改用户的区域之外，还可以将LocaleChangeInterceptor拦截器应用到处理程序映射中，它会发现当前HTTP请求中出现的特殊参数。其中的参数名称可以通过拦截器的paramName属性进行自定义。如果这种参数出现在当前请求中，拦截器就会根据参数值来改变用户的区域。
   
只需要在App.java中加入：
```
       @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
               LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
               // 设置请求地址的参数,默认为：locale
   //          lci.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
               returnlci;
       }
      
       @Override
      public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(localeChangeInterceptor());
      }
```
注意这个是可以和会话区域解析器以及Cookie区域解析器一起使用的，但是不能和FixedLocaleResolver一起使用，否则会抛出异常信息。




# 参考
[刘冬的博客-玩转spring boot——国际化](https://www.cnblogs.com/GoodHelper/p/6824492.html)<br/>
[林祥纤-58. Spring Boot国际化（i18n）【从零开始学Spring Boot】](https://www.cnblogs.com/GoodHelper/p/6824492.html)
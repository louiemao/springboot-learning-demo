# SpringBoot + UEditor
springboot与ueditor集成，上传路径可指定任意位置，不需要一定是网站路径之下。<br/>
需要改动ueditor.jar的ConfigManager文件，因此需要导入源码文件。<br/>
项目访问地址：http://localhost:8080/test/ueditor-1.4.3.3/index.html

### UEditor相关文件
下载开发版中的jsp版，解压放到src/main/resources/static目录下，移除jsp文件夹中的文件，只保留config.json文件
下载完整源码，找到jsp下的java源码，加入到src/main/java目录下

### 关键代码
pom.xml
```
		<!--ueditor需要的依赖项-->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.3.1</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.9</version>
		</dependency>
		<dependency>
			<groupId>org.json</groupId>
			<artifactId>json</artifactId>
		</dependency>
```

application.properties
```
#自定义文件上传路径，注意要以/结尾
web.upload-path=D:/temp/ueditor

#表示所有的访问都经过静态资源路径
spring.mvc.static-path-pattern=/**
#静态资源路径，系统可以直接访问的路径，且路径下的所有文件均可被用户直接读取。
spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:${web.upload-path}
```
这里通过配置的方式添加外部的静态资源路径，也可以通过继承WebMvcConfigurerAdapter然后重写addResourceHandlers方法的方式来添加外部的静态资源路径，这个路径就是ueditor上传文件后保存文件的根目录。

ConfigManager.java
```
	private String getConfigPath () {
		//return this.parentPath + File.separator + ConfigManager.configFileName;
		return "static/ueditor-1.4.3.3/jsp"+ File.separator + ConfigManager.configFileName;
	}
	...
	private String readFile ( String path ) throws IOException {
		StringBuilder builder = new StringBuilder();
		try {
			//InputStreamReader reader = new InputStreamReader( new FileInputStream( path ), "UTF-8" );
			Resource resource=new ClassPathResource(path);
			InputStreamReader reader = new InputStreamReader( resource.getInputStream(), "UTF-8" );
			BufferedReader bfReader = new BufferedReader( reader );
			String tmpContent = null;
			while ( ( tmpContent = bfReader.readLine() ) != null ) {
				builder.append( tmpContent );
			}
			bfReader.close();
		} catch ( UnsupportedEncodingException e ) {
			// 忽略
		}
		return this.filter( builder.toString() );
	}
```
修改了ConfigManager.java的两处代码，通过ClassPathResource类来读取静态资源，使其符合springboot

UEditorServlet
```
@WebServlet(name = "UEditorServlet",urlPatterns = "/ueditor")
   public class UEditorServlet extends HttpServlet {
       @Value("${web.upload-path}")
       private String rootPath;
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doPost(request,response);
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.setCharacterEncoding( "utf-8" );
           response.setHeader("Content-Type" , "text/html");
   
           try {
               String exec = new ActionEnter(request, rootPath).exec();
               PrintWriter writer = response.getWriter();
               writer.write(exec);
               writer.flush();
               writer.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
```
这里使用了servlet的方式提供了后台服务，不推荐使用controller的方式，原因如下：
* 如果使用controller的方式，则还需要修改BinaryUploader文件
* 如果贸然尝试使用Controller会导致上传文件失败，原因是因为Spring Boot的Request在集成了某些技术后不能强转成为MultipartRequest

SpringbootUeditorApplication
```
@ServletComponentScan
```
这里servlet是使用@WebServlet方式，因此需要在启动类上增加@ServletComponentScan，也可以通过Bean注入的方式来实现。

ueditor.config.js
```
 // 服务器统一请求接口路径
, serverUrl: URL + "../ueditor"
```
serverUrl指定servlet的访问地址，这里的URL是ueditor.config.js文件所在路径

至此可以访问地址：http://localhost:8080/ueditor-1.4.3.3/index.html，来查看效果

### 路径配置的一些说明
* application.properties中的web.upload-path：用来指定ueditor上传到服务器上的文件存放的根目录，上传的文件全部都在此目录之下
* config.json的imagePathFormat，这个有两个作用（scrawlPathFormat、snapscreenPathFormat等作用类似）
    * 指定在根目录之后的文件路径，最终的文件存放地址是：web.upload-path配置的根目录+imagePathFormat生成的路径+后缀名
    * 生成文件保存成功后返回给前端访问文件的url地址：imageUrlPrefix+imagePathFormat生成的路径+后缀名

最后我在application.properties文件中增加了上下文
```
server.context-path=/test
```
这时候就需要配置config.json中的imageUrlPrefix等各种UrlPrefix
```
 "imageUrlPrefix": "/test"
```
最终的访问地址为http://localhost:8080/ueditor-1.4.3.3/index.html

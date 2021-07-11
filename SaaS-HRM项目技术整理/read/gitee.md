### day4 to day5
基于jwt的用户鉴权、api权限校验
shiro初步

### new
~~ihrm_common/src/main/java/com/ihrm/common/interceptor/JwtInterceptor.java~~

> fix: 
>
> jwt请求拦截
>
> d:
>
> ihrm_common/src/main/java/com/ihrm/common/interceptor/JwtInterceptor.java

~~ihrm_system/src/main/java/com/ihrm/system/SystemConfig.java~~

> fix:
>
> 添加拦截器配置
>
> d:
>
> iHRM_system/src/main/java/com/ihrm/system/SystemConfig.java




### recode

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/User.java~~

> fix: 
>
> 增加用户属性 level (权限等级)
>
> d:
>
> level	(String)
>
> ​		saasAdmin：saas管理员具备所有权限
>
> ​		coAdmin：企业管理（创建租户企业的时候添加）
>
> ​		user：普通用户（需要分配角色）

~~ihrm_common/src/main/java/com/ihrm/common/controller/BaseController.java~~

> fix: 
>
> 动态获取companyId和companyName

ihrm_system/src/main/java/com/ihrm/system/SystemApplication.java

> todo
>
> 解决no session
>
> ![image-20210511095121818](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210511095121818.png)

~~ihrm_system/src/main/java/com/ihrm/system/controller/RoleController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

> fix: 
>
> 方法重命名 sava->assignPrem/assignRoles
>
> d:
>
> ihrm_system/src/main/java/com/ihrm/system/controller/RoleController.java
>
> > save->assignPrem
>
> ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java
>
> > save->assignRoles
>
> scope: controller

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

> fix: 
>
> 1. 重写 delete方法、login方法和profile方法
> 2. 新增 permissionService 属性
> 3. 新增ProfileResult类构造方法 ProfileResult(User user, List<Permission> list)
>
> d:
>
> ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java
>
> > delete
>
> > login
>
> > profile
>
> > permissionService
>
> ihrm_common_model/src/main/java/com/ihrm/domain/system/response/ProfileResult.java
>
> > ProfileResult(User user, List<Permission> list)

### day5 to day6

### new

~~ihrm_common/src/main/java/com/ihrm/common/controller/ErrorController.java~~

> 公共错误跳转

~~ihrm_common/src/main/java/com/ihrm/common/shiro/realm/IhrmRealm.java~~

> 公共的realm：获取安全数据，构造权限信息

~~ihrm_common/src/main/java/com/ihrm/common/shiro/session/CustomSessionManager.java~~

> 返回sessionId

~~ihrm_company/src/main/java/com/ihrm/company/ShiroConfiguration.java~~

> shiro配置

~~ihrm_system/src/main/java/com/ihrm/system/ShiroConfiguration.java~~

> shiro配置

~~ihrm_system/src/main/java/com/ihrm/system/shiro/realm/UserRealm.java~~

> UserRealm

### recode

~~pom.xml~~

> spring和shiro的整合依赖
>
> shiro与redis整合

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

> delete增加权限过滤；ScurityUtils重写session数据获取方法
>
> ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java

~~ihrm_common/pom.xml~~

~~ihrm_common/src/main/java/com/ihrm/common/controller/BaseController.java~~

> 使用SecurityUtils重写session数据获取方法

~~ihrm_common/src/main/java/com/ihrm/common/handler/BaseExceptionHandler.java~~

> 增加权限不足异常处理

~~ihrm_common_model/pom.xml~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/ProfileResult.java~~

> 序列化，AuthCachePrincipal
>
> 增加companyId属性

~~ihrm_company/src/main/java/com/ihrm/company/CompanyApplication.java~~

> 注册JwtUtils Bean
>
> ihrm_company/src/main/java/com/ihrm/company/CompanyApplication.java

~~ihrm_company/src/main/resources/application.yml~~

~~ihrm_system/src/main/java/com/ihrm/system/SystemConfig.java~~

~~ihrm_system/src/main/java/com/ihrm/system/service/UserService.java~~

~~ihrm_system/src/main/resources/application.yml~~



### todo

ihrm_common\src\main\java\com\ihrm\common\interceptor\JwtInterceptor.java



### day6 to day7

### new

~~ihrm_common/src/main/java/com/ihrm/common/feign/FeignConfiguration.java~~

> ```java
> @Configuration
> public class FeignConfiguration {
>     
>     /**
>      * 配置feign拦截器，解决请求头问题
>      */
>     @Bean
>     public RequestInterceptor requestInterceptor(){
>         return new RequestInterceptor() {
>     
>             /**
>              * 获取所有浏览器发送的请求属性，请求头赋值到feign
>              * @param requestTemplate
>              */
>             @Override
>             public void apply(RequestTemplate requestTemplate) {
>                 /**
>                  * 请求属性
>                  */
>                 ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
>                 if(attributes!=null){
>                     HttpServletRequest request = attributes.getRequest();
>                     /**
>                      * 获取浏览器发起的请求头
>                      */
>                     Enumeration<String> headerNames = request.getHeaderNames();
>                     if(headerNames!=null){
>                         while (headerNames.hasMoreElements()){
>                             /**
>                              * 请求头名称
>                              */
>                             String name = headerNames.nextElement();
>                             /**
>                              * 请求头数据
>                              */
>                             String value = request.getHeader(name);
>                             requestTemplate.header(name,value);
>                             
>                         }
>                     }
>                 }
>             }
>         };
>     }
> }
> ```

~~ihrm_common/src/main/java/com/ihrm/common/utils/DownloadUtils.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/EmployeeArchive.java~~

> 员工档案实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/EmployeePositive.java~~

> 员工转正申请实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/EmployeeResignation.java~~

> 员工离职申请实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/EmployeeTransferPosition.java~~

> 调岗申请实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/UserCompanyJobs.java~~

> 员工岗位信息实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/UserCompanyPersonal.java~~

> 员工详情实体类

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/response/EmployeeReportResult.java~~

> 员工离职报告

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/UserSimpleResult.java~~

> 员工简单信息

~~ihrm_employee/pom.xml~~

~~ihrm_employee/src/main/java/com/ihrm/employee/EmployeeApplication.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/ShiroConfiguration.java~~

> shiro配置

~~ihrm_employee/src/main/java/com/ihrm/employee/controller/EmployeeController.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/ArchiveDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/EmployeeResignationDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/PositiveDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/TransferPositionDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/UserCompanyJobsDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/UserCompanyPersonalDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/ArchiveService.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/PositiveService.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/ResignationService.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/TransferPositionService.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/UserCompanyJobsService.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/UserCompanyPersonalService.java~~

~~ihrm_employee/src/main/resources/application.yml~~

~~ihrm_eureka/pom.xml~~

~~ihrm_eureka/src/main/java/com/ihrm/eureka/EurekaServer.java~~

~~ihrm_eureka/src/main/resources/application.yml~~

~~ihrm_system/src/main/java/com/ihrm/system/client/DepartmentFeignClient.java~~





### recode

~~ihrm_common/pom.xml~~

~~ihrm_common/src/main/java/com/ihrm/common/controller/BaseController.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/User.java~~

> ```java
> this.workNumber=new DecimalFormat("#").format(values[3]).toString();
> ```

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/ProfileResult.java~~

~~ihrm_company/pom.xml~~

~~ihrm_company/src/main/java/com/ihrm/company/CompanyApplication.java~~

~~ihrm_company/src/main/java/com/ihrm/company/controller/DepartmentController.java~~

~~ihrm_company/src/main/java/com/ihrm/company/dao/DepartmentDao.java~~

~~ihrm_company/src/main/java/com/ihrm/company/service/DepartmentService.java~~

~~ihrm_company/src/main/resources/application.yml~~

> 注册eureka服务地址

~~ihrm_system/pom.xml~~

~~ihrm_system/src/main/java/com/ihrm/system/SystemApplication.java~~

> ```java
> @EnableEurekaClient
> @EnableDiscoveryClient
> @EnableFeignClients
> ```

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

> ```java
> /**
>  * 导入Excel,添加用户
>  * 文件上传:springboot
>  */
> @RequestMapping(value = "/user/import",method = RequestMethod.POST)
> public Result importUser(@RequestParam(name = "file")MultipartFile file) throws Exception{
>     /**
>      * 解析Excel
>      * 1.1.根据Excel文件创建工作簿
>      */
>     Workbook wb = new XSSFWorkbook(file.getInputStream());
>     /**
>      * 1.2.获取Sheet
>      */
>     Sheet sheet = wb.getSheetAt(0);
>     /**
>      * 1.3.获取Sheet中的每一行，和每一个单元格
>      * 2.获取用户数据
>      */
>     List<User> list=new ArrayList<>();
>     for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){
>         Row row = sheet.getRow(rowNum);
>         Object[] values = new Object[row.getLastCellNum()];
>         for(int cellNum=1;cellNum<row.getLastCellNum();cellNum++){
>             Cell cell = row.getCell(cellNum);
>             Object value=getCellValue(cell);
>             values[cellNum]=value;
>         }
>         User user=new User(values);
>         list.add(user);
>     }
>     /**
>      * 批量保存用户
>      */
>     userService.saveAll(list,companyId,companyName);
>     
>     return new Result(ResultCode.SUCCESS);
> }
>     public static Object getCellValue(Cell cell){
>         /**
>          * 获取到单元格的属性类型
>          */
>         CellType cellType = cell.getCellType();
>         /**
>          * 根据单元格数据类型获取数据
>          */
>         Object value=null;
>         switch (cellType){
>             case STRING:
>                 value=cell.getStringCellValue();
>                 break;
>             case BOOLEAN:
>                 value=cell.getBooleanCellValue();
>                 break;
>             case NUMERIC:
>                 if(DateUtil.isCellDateFormatted(cell)){
>                     /**
>                      * 日期格式
>                      */
>                     value=cell.getDateCellValue();
>                 }else {
>                     /**
>                      * 数字
>                      */
>                     value=cell.getNumericCellValue();
>                 }
>                 break;
>             case FORMULA:
>                 /**
>                  * 公式
>                  */
>                 value=cell.getCellFormula();
>                 break;
>             default:
>                 break;
>         }
>         return value;
>     }
> ```
>
> 

~~ihrm_system/src/main/java/com/ihrm/system/service/UserService.java~~

~~ihrm_system/src/main/resources/application.yml~~

~~pom.xml~~







### day7 to day8

### new

~~ihrm_common/src/main/java/com/ihrm/common/poi/ExcelExportUtil.java~~

> ```java
> public class ExcelExportUtil <T>{
>     /**
>      * 写入数据的起始行
>      */
>     private int rowIndex;
>     
>     /**
>      * 需要提取的样式所在的行号
>      */
>     private int styleIndex;
>     
>     /**
>      * 对象的字节码
>      */
>     private Class clazz;
>     
>     /**
>      * 对象中的所有属性
>      */
>     private Field fields[];
>     
>     
>     
>     public ExcelExportUtil(Class clazz,int rowIndex,int styleIndex){
>         this.clazz=clazz;
>         this.rowIndex=rowIndex;
>         this.styleIndex=styleIndex;
>         fields=clazz.getDeclaredFields();
>     }
>     
>     /**
>      * 基于注解导出
>      *      参数：
>      *         response
>      *         InputStream:模板的输入流
>      *         objs:数据
>      *         fileName:生成的文件名
>      */
>     public void export(HttpServletResponse response, InputStream is, List<T> objs,String fileName) throws Exception{
>     
>         /**
>          * 1.根据模板创建工作簿
>          */
>         XSSFWorkbook workbook = new XSSFWorkbook(is);
>     
>         /**
>          * 2.读取工作表
>          */
>         XSSFSheet sheet = workbook.getSheetAt(0);
>     
>         /**
>          * 3.提取公共的的样式
>          */
>         CellStyle[] styles = getTemplateStyles(sheet.getRow(styleIndex));
>     
>         /**
>          * 4.根据数据创建每一行和每一个单元格的数据
>          */
>         AtomicInteger datasAi = new AtomicInteger(rowIndex);
>         
>         for(T t:objs){
>             Row row = sheet.createRow(datasAi.getAndIncrement());
>             for(int i=0;i< styles.length;i++){
>                 Cell cell = row.createCell(i);
>                 cell.setCellStyle(styles[i]);
>                 for(Field field:fields){
>                     if(field.isAnnotationPresent(ExcelAttribute.class)){
>                         field.setAccessible(true);
>                         ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
>                         if(i==ea.sort()){
>                             if(field.get(t)!=null){
>                                 cell.setCellValue(field.get(t).toString());
>                             }
>                         }
>                     }
>                 }
>             }
>         }
>         fileName=URLEncoder.encode(fileName,"UTF-8");
>         response.setContentType("application/octet-stream");
>         response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes("ISO8859-1")));
>         response.setHeader("filename",fileName);
>         workbook.write(response.getOutputStream());
>     }
>     
>     public CellStyle[] getTemplateStyles(Row row){
>         CellStyle[] styles=new CellStyle[row.getLastCellNum()];
>         for(int i=0;i<row.getLastCellNum();i++){
>             styles[i]=row.getCell(i).getCellStyle();
>         }
>         return styles;
>     }
> }
> ```

~~ihrm_common/src/main/java/com/ihrm/common/poi/ExcelImportUtil.java~~

> XSSFWorkbook 
>
> SXSSFWorkbook

> ```java
> public class ExcelImportUtil <T> {
> 
>     private Class clazz;
>     private Field fields[];
>     
>     public ExcelImportUtil(Class clazz){
>         this.clazz=clazz;
>         fields=clazz.getDeclaredFields();
>     }
>     
>     /**
>      * 基于注解读取excel
>      */
>     public List<T> readExcel(InputStream is,int rowIndex,int cellIndex){
>         List<T> list = new ArrayList<>();
>         T entity=null;
>         try{
>             XSSFWorkbook workbook = new XSSFWorkbook(is);
>             Sheet sheet = workbook.getSheetAt(0);
>             /**
>              * 不准确
>              */
>             int rowLength = sheet.getLastRowNum();
>     
>             System.out.println(sheet.getLastRowNum());
>             
>             for(int rowNum=rowIndex;rowNum<=sheet.getLastRowNum();rowNum++){
>                 Row row = sheet.getRow(rowNum);
>                 entity = (T) clazz.getInterfaces();
>                 System.out.println(row.getLastCellNum());
>                 for(int j=cellIndex;j<row.getLastCellNum();j++){
>                     Cell cell = row.getCell(j);
>                     for (Field field: fields){
>                         if(field.isAnnotationPresent(ExcelAttribute.class)){
>                             field.setAccessible(true);
>                             ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
>                             if(j== ea.sort()){
>                                 field.set(entity,coverAttrType(field,cell));
>                             }
>                         }
>                     }
>                 }
>                 list.add(entity);
>             }
>             
>         } catch (IOException e) {
>             e.printStackTrace();
>         } catch (IllegalAccessException e) {
>             e.printStackTrace();
>         } catch (Exception e) {
>             e.printStackTrace();
>         }
>         return list;
>     }
>     
>     /**
>      * 类型转换 将cell单元格格式转为 字段类型
>      */
>     private Object coverAttrType(Field field,Cell cell) throws Exception{
>         String fieldType = field.getType().getSimpleName();
>         if("String".equals(fieldType)){
>             return getValue(cell);
>         }else if("Date".equals(fieldType)){
>             return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(getValue(cell));
>         }else if("int".equals(fieldType)||"Integer".equals(fieldType)){
>             return Integer.parseInt(getValue(cell));
>         }else if("double".equals(fieldType)||"Double".equals(fieldType)){
>             return Double.parseDouble(getValue(cell));
>         }else {
>             return null;
>         }
>     }
>     
>     /**
>      * 格式转为String
>      */
>     public String getValue(Cell cell){
>         if(cell==null){
>             return "";
>         }
>         
>         switch (cell.getCellType()){
>             case STRING:
>                 return cell.getRichStringCellValue().getString().trim();
>             case NUMERIC:
>                 if(DateUtil.isCellDateFormatted(cell)){
>                     Date dt = DateUtil.getJavaDate(cell.getNumericCellValue());
>                     return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dt);
>                 }else {
>                     /**
>                      * 防止数值变成科学记数法
>                      */
>                     String strCell = "";
>                     Double num = cell.getNumericCellValue();
>                     BigDecimal bd = new BigDecimal(num.toString());
>                     if (bd != null) {
>                         strCell = bd.toPlainString();
>                     }
>                     /**
>                      * 去除浮点型自动加的.0
>                      */
>                     if (strCell.endsWith(".0")) {
>                         strCell = strCell.substring(0, strCell.indexOf("."));
>                     }
>                     return strCell;
>                 }
>             case BOOLEAN:
>                 return String.valueOf(cell.getBooleanCellValue());
>             default:
>                 return "";
>         }
>     }
> 
> 
> }
> ```

~~ihrm_common_model/src/main/java/com/ihrm/domain/poi/ExcelAttribute.java~~



### recode

~~ihrm_common/src/main/java/com/ihrm/common/controller/BaseController.java~~

~~ihrm_common/src/main/java/com/ihrm/common/utils/DownloadUtils.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/response/EmployeeReportResult.java~~

~~ihrm_company/src/main/java/com/ihrm/company/ShiroConfiguration.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/controller/EmployeeController.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/dao/UserCompanyPersonalDao.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/service/UserCompanyPersonalService.java~~

~~ihrm_system/src/main/java/com/ihrm/system/ShiroConfiguration.java~~





### day8 to day 9

### new 

~~ihrm_common/src/main/java/com/ihrm/common/utils/QiniuUploadUtil.java~~



### recode

~~ihrm_common/src/main/java/com/ihrm/common/poi/ExcelImportUtil.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/employee/response/EmployeeReportResult.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/User.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/UserResult.java~~

~~ihrm_employee/src/main/java/com/ihrm/employee/controller/EmployeeController.java~~

~~ihrm_system/pom.xml~~

~~ihrm_system/src/main/java/com/ihrm/system/ShiroConfiguration.java~~

~~ihrm_system/src/main/java/com/ihrm/system/SystemApplication.java~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/service/UserService.java~~

~~ihrm_system/src/main/resources/application.yml~~

~~ihrm_system/target/classes/application.yml~~

> ```yml
> ai:
>   appId: 15191935
>   apiKey: cyWSHgas93Vtdmt42OwbW8pu
>   secretKey: yf1GusMvvLBdOnyubfLubNyod9iEDEZW
>   imageType: BASE64
>   groupId: itcast
> ```

~~pom.xml~~



### day9 to day11

### new

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/FaceLoginResult.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/response/QRCode.java~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/FaceLoginController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/service/FaceLoginService.java~~

~~ihrm_system/src/main/java/com/ihrm/system/utils/BaiduAiUtil.java~~

~~ihrm_system/src/main/java/com/ihrm/system/utils/QRCodeUtil.java~~

### recode

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

ihrm_system/src/main/java/com/ihrm/system/service/UserService.java

~~ihrm_system/src/main/resources/application.yml~~





### day11 to day14 

### new 

~~ihrm_attendance/pom.xml~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/AttandanceApplication.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/ShiroConfiguration.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/controller/AttendanceController.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/controller/ConfigController.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/ArchiveMonthlyDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/ArchiveMonthlyInfoDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/AttendanceConfigDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/AttendanceDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/CompanySettingsDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/DayOffConfigDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/DeductionDictDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/DeductionTypeDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/ExtraDutyConfigDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/ExtraDutyRuleDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/LeaveConfigDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/UserDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/ArchiveService.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/AtteService.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/ConfigurationService.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/ExcelImportService.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/ReprortService.java~~

~~ihrm_attendance/src/main/resources/application.yml~~

~~ihrm_attendance/src/test/java/cn/itcast/PoiTest.java~~

~~ihrm_attendance/target/classes/application.yml~~

~~ihrm_gate/pom.xml~~

~~ihrm_gate/src/main/java/com/ihrm/gate/GateApplication.java~~

~~ihrm_gate/src/main/java/com/ihrm/gate/ShiroConfiguration.java~~

~~ihrm_gate/src/main/java/com/ihrm/gate/filter/LoginFilter.java~~

~~ihrm_gate/src/main/resources/application.yml~~

~~ihrm_social_securitys/pom.xml~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/ShiroConfiguration.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/SocialSecuritysApplication.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/client/EmployeeFeignClient.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/client/SystemFeignClient.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/controller/SocialSecurityController.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/ArchiveDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/ArchiveDetailDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/CityPaymentItemDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/CompanySettingsDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/PaymentItemDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/dao/UserSocialSecurityDao.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/service/ArchiveService.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/service/CompanySettingsService.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/service/PaymentItemService.java~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/service/UserSocialService.java~~

~~ihrm_social_securitys/src/main/resources/application.yml~~





### new complete

~~ihrm_common/src/main/java/com/ihrm/common/utils/DateUtil.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/base/BaseEntity.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/ArchiveListBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/AtteItemBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/AtteReportMonthlyBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/AtteSalaryStatisticsBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/AtteStatisBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/AtteTotalsBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/bo/DaysMonthlyBO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/ArchiveMonthly.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/ArchiveMonthlyInfo.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/Attendance.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/AttendanceConfig.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/CompanySettings.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/DayOffConfig.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/DeductionDict.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/DeductionType.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/ExtraDutyConfig.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/LeaveConfig.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/ReportItemVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/enums/DeductionEnum.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/enums/LeaveTypeEnum.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/resp/PageResult.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ArchiveInfoVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ArchiveItemVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ArchiveMonthlyVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ArchiveVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/AttePageVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/AtteSalaryStatisticsVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/AtteUploadVo.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/AtteVo.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ConfigVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ExtDutyVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ExtWorkVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/vo/ReportVO.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/Archive.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/ArchiveDetail.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/CityPaymentItem.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/CompanySettings.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/PaymentItem.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/UserSocialSecurity.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/UserSocialSecurityItem.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/City.java~~

~~ihrm_employee/src/main/resources/jasperreports_extension.properties~~

~~ihrm_employee/src/main/resources/stsong/fonts.xml~~

~~ihrm_employee/src/main/resources/stsong/stsong.TTF~~

~~ihrm_employee/src/main/resources/templates/profile.jasper~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/CityController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/dao/CityDao.java~~

~~ihrm_system/src/main/java/com/ihrm/system/service/CityService.java~~



### recode

~~pom.xml~~

~~ihrm_common/src/main/java/com/ihrm/common/exception/CommonException.java~~

~~ihrm_common/src/main/java/com/ihrm/common/interceptor/JwtInterceptor.java~~

~~ihrm_common/src/main/java/com/ihrm/common/poi/ExcelImportUtil.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/company/Department.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/system/User.java~~

~~ihrm_company/pom.xml~~

~~ihrm_company/src/main/java/com/ihrm/company/ShiroConfiguration.java~~

~~ihrm_company/src/main/java/com/ihrm/company/controller/CompanyController.java~~

~~ihrm_company/src/main/java/com/ihrm/company/controller/DepartmentController.java~~

~~ihrm_company/src/main/java/com/ihrm/company/service/CompanyService.java~~

~~ihrm_company/src/main/resources/application.yml~~

~~ihrm_employee/pom.xml~~

~~ihrm_employee/src/main/java/com/ihrm/employee/controller/EmployeeController.java~~

~~ihrm_employee/src/main/resources/application.yml~~

~~ihrm_eureka/pom.xml~~

~~ihrm_system/pom.xml~~

~~ihrm_system/src/main/java/com/ihrm/system/ShiroConfiguration.java~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/service/UserService.java~~

~~ihrm_system/src/main/resources/application.yml~~





### day14 to day17

### new complete



~~ihrm_audit/application.yml~~

~~ihrm_audit/pom.xml~~

~~ihrm_audit/src/main/java/com/ihrm/audit/AuditApplication.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/AuditDatasourceConfig.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/JpaRepositoriesConfig.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/ShiroConfiguration.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/client/FeignClientService.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/client/SystemFeignClient.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/controller/ProcessController.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/dao/ProcInstanceDao.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/dao/ProcTaskInstanceDao.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/dao/ProcUserGroupDao.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/entity/ProcInstance.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/entity/ProcTaskInstance.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/entity/ProcUserGroup.java~~

~~ihrm_audit/src/main/java/com/ihrm/audit/service/AuditService.java~~

~~irm_audit/src/main/java/com/ihrm/audit/service/ProcessService.java~~

~~ihrm_audit/src/main/resources/application.yml~~

~~ihrm_audit/src/test/java/com/ihrm/audit/AuditTest.java~~

~~ihrm_audit/src/test/java/com/ihrm/audit/ProcessTest.java~~

~~ihrm_salarys/application.yml~~

~~ihrm_salarys/pom.xml~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/SalarysApplication.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/ShiroConfiguration.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/controller/ArchiveController.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/controller/CompanySettingsController.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/controller/SalaryController.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/controller/SettingsController.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/ArchiveDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/ArchiveDetailDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/CompanySettingsDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/SettingsDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/UserSalaryChangeDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/dao/UserSalaryDao.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/feign/AttendanceFeignClient.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/feign/FeignClientService.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/feign/SocialSecurityFeignClient.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/service/ArchiveService.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/service/CompanySettingsService.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/service/SalaryService.java~~

~~ihrm_salarys/src/main/java/com/ihrm/salarys/service/SettingsService.java~~

~~ihrm_salarys/src/main/resources/application.yml~~







### new half

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/CompanySettings.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/SalaryArchive.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/SalaryArchiveDetail.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/Settings.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/UserSalary.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/salarys/UserSalaryChange.java~~



### recode

~~ihrm_attendance/src/main/java/com/ihrm/atte/controller/AttendanceController.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/dao/ArchiveMonthlyInfoDao.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/ArchiveService.java~~

~~ihrm_attendance/src/main/java/com/ihrm/atte/service/AtteService.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/atte/entity/ArchiveMonthlyInfo.java~~

~~ihrm_common_model/src/main/java/com/ihrm/domain/social_security/ArchiveDetail.java~~

~~ihrm_gate/src/main/resources/application.yml~~

~~ihrm_social_securitys/src/main/java/com/ihrm/social/controller/SocialSecurityController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/controller/UserController.java~~

~~ihrm_system/src/main/java/com/ihrm/system/shiro/realm/UserRealm.java~~

~~pom.xml~~





### toFix

ihrm_system/src/main/java/com/ihrm/system/SystemConfig.java

JwtInterceptor
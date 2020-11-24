# Crowdfunding_java_web_project

This project is online project that is initiated by shangguigu (http://www.atguigu.com/). All html files are provided by shangguigu. What I did is to finish all related back-end java coding and a part of html coding.

This project mainly has four things which are different with the original version from shangguigu:
1. using spring cloud gatewy instead of zuul (nitce that spring cloud gateway based on webflux).
2. using AWS SNS to send SMS message.
3. using AWS S3 as user image storage instead of Aliyun.
4. using paypal as a way to pay instead of Alipay.

Please refer to each pom in modules for dependcies version.

The project consists of 2 parts：
1. admin management system which is a all-in-one project(gtwcrowdfunding02-admin-parent)
 mainly functions: login and registration of administrators, authority management of administrators, creating, retrieving, updating and deleting of administrators, roles and authorities

 mainly frameworks:
   spring
   springmvc
   mybatis
   springsecurity
   mysqld
   
2. member management system which has a distributed architecture(gtwcrowdfunding05-member-parent) 
mainly functions: The microservice of authentication is responsible for login and registration of administrators. The microservice of project is responsible for crowd funding projects publish for memebers.  The microservice of order and pay are to help member to support a project that he or she likes. The microservice of mysql and redis are consumed by other microservices.

 mainly frameworks:
   springboot
   springcloud(eruka, gateway,openfeign,spring session)
   mybatis+mysql
   redis
   AWS SNS+ AWS S3+ PAYPAL

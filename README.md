# Cola 
## 简介
Cola是一个互联网应用基础组件，基于SpringBoot2/SpringSecurity/SpringSession/SpringSocial/SpringSecurityOAuth2构建，着重于解决互联网应用分布式集群场景下身份验证，并提供丰富的认证方式和社会化认证集成。也包含其他支撑系统，例如：用户中心、安全中心、通知中心。能够满足所有互联网应用的所有认证需求，并通过基础组件来支撑互联网应用快速开发。

## 组件列表
| 组件代码 | 组件名称 | 说明 |
| ------ | ------ | ------ |
| cola-auth | 认证组件 | 提供认证服务 |
| cola-user | 用户中心 | 提供用户服务 |
| cola-security | 安全中心 | 提供凭证验证等安全服务 |
| cola-notify | 通知中心 | 提供消息通知服务 |

## 认证模式
- Web Session认证
- Jwt Token认证
- OAuth2 Token认证
## 登录方式
- 账号密码登录
- 短信验证码登录
- 第三方集成登录(支付宝、微信、QQ)

通过Cola可快速的多种构建认证服务
## 认证服务
- SSO 基于OAuth2构建SSO平台
- Spring Cloud微服务集群认证服务
- OpenAPI 基于OAuth2构建开放平台

通过对SpringSecurityOAuth2进行增强，支持短信验证码、第三方登录等授权模式
## OAuth2扩展授权模式
- 通过短信验证码获取Token
- 通过第三方OpenId获取Token
- 通过第三方AuthorizationCode获取Token

## 实例项目
| 项目 | 认证模式 | 适用场景 |
| ------ | ------ | ------ |
| cola-sample-jwt | JWT |APP/小程序 |
| cola-sample-oauth2 | OAuth2 |  APP/小程序/开放平台和微服务平台的认证服务 |
| cola-sample-sso | Session&OAuth2 | Web应用/APP/小程序集成认证 |
| cola-sample-web | Session | Web应用 |
| cola-sample-social | Session | Web应用/SSO服务 |

# redisAdmin
open source redis tool to see and modify simple redis keys and data

to compile:
$git clone https://github.com/BitcoinJake09/redisAdmin
$cd to directory
$mvn clean compile assembly:single
$appletviewer run.html

if you have fail or permission issue, add
grant {
 permission java.security.AllPermission;
 };

to your <jre location>\lib\security\java.policy

![redisAdmin](https://media.discordapp.net/attachments/419294985419096064/890102225878274078/unknown.png?width=800&height=600)

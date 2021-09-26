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

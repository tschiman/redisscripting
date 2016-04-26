# Step-by-step guide #

##Executing Lua Scripts Via Java##

1. Open the project in your favorite IDE. 
1. Navigate to RedisScript.java
1. Change the variables at the top of the main method to alter execution:

+ host - the host of the redis server
+ relativeFilePath - the relative file path of the lua script you want to run
+ keyMatch - the pattern to match keys against for the scan 
+ fieldMatch - the pattern to match fields inside hashes against during script execution
+ count - the number of keys to try and obtain on each iteration

##Writing lua scripts##

Inside the scripts folder are the lua scripts that have been written. The can be ran by specifying the relative file 
path in the RedisScript.java class. The keys and argvs arguments are space separated lists of 
strings that are aggregated into arrays inside of the lua script. They are accessible via the 'KEYS' and 'ARGV' 
variable names. Lua scripts run against a remote server should always provide a list of keys to operate over to 
prevent the script from blocking while scanning for keys in the script. Attempting to embed a SCAN call inside a 
script will fail, so do not try that.

!!Do not do wild card key lookups inside a lua script!!

Scripts that are intended for local usage only should be stored in the /scripts/local folder.




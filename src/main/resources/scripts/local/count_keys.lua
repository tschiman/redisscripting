--ARGV[1] - the keystring to match against
return #redis.pcall('keys', ARGV[1])
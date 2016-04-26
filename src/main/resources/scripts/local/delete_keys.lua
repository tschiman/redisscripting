--ARGV[1] - the key pattern to match on for delete
local delKeys = redis.call('keys', ARGV[1])

for _,key in ipairs(delKeys) do
	redis.pcall('del', key)
end
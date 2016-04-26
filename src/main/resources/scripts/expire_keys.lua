--KEYS - the keys that were passed in as arguments

--For each key that in KEYS
local expireCount = 0
for _,key in ipairs(KEYS) do
	--find the ttl for that key
	local ttl = redis.call('TTL', key)
	if (ttl == -1) then
		--set the ttl to one year
		redis.call('EXPIRE', key, 31540000)
		expireCount = expireCount + 1
	end
end

return 'ExpirationCount: ' .. expireCount .. ' KeyCount: ' .. #KEYS
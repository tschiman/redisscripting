--KEYS - the keys that were passed in as arguments
--ARGV[1] - the field to remove inside the key

--track number of deletes

local countDeletes = 0
local returnValueIterator = 0

local returnValue = {}

for _,key in ipairs(KEYS) do
	--go get the actual value of the hash (HGET)
	local val = redis.call('HGET', key, ARGV[1])
	if (type(val) == 'string') then
		local jsonVal = cjson.decode(val)

		--If the uuid is empty and the count of entries is 0, delete this key
		if not (#jsonVal['e'] == 0) then
			jsonVal['redisKey'] = key
			returnValueIterator = returnValueIterator + 1
			returnValue[returnValueIterator] = cjson.encode(jsonVal)
		end
	end
end

return returnValue
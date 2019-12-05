-- 保存正在准备注册的用户的信息和验证码
local keyPrefix1 = "readyRegisterUsername:"
local keyPrefix2 = "readyRegisterUser:"
local keyPrefix3 = "readyVerifyCode:"
local username = KEYS[1]
local user = ARGV[1]
local code = ARGV[2]
-- 过期时间 30分钟
local expireSeconds = 1800
-- 逻辑
local existed = redis.call("keys", keyPrefix1 .. username)
if (existed)
then
    -- 用户名已存在
    return 10006
else
    -- setex key seconds value
    redis.call("setex", keyPrefix1 .. username, expireSeconds, username)
    redis.call("setex", keyPrefix2 .. username, expireSeconds, user)
    redis.call("setex", keyPrefix3 .. username, expireSeconds, code)
    -- 成功
    return 1
end

-- 字符串
local string1 = "Lua"
print("\"字符串 1 是\"", string1)
local string2 = 'runoob.com'
print("字符串 2 是", string2)
local string3 = [["Lua 教程"]]
print("字符串 3 是", string3)

print("全部大写", string.upper(string1))
print("全部小写", string.lower(string1))
print("替换", string.gsub("aaaa", "a", "z", 3))
print("查找", string.find("Hello Lua user", "Lua", 1))
print("字符串反转", string.reverse("Lua"))
print("格式化字符串", string.format("the value is:%d", 4))
print("整型数字转成字符并连接", string.char(97,98,99,100))
print("byte转换字符为整数值（可以指定某个字符，默认第一个字符）", string.byte("ABCD",4))
print("计算字符串的长度", string.len("abc"))
for word in string.gmatch("Hello Lua user", "%a+") do print(word) end
print(string.match("I have 2 questions for you.", "%d+ %a+"))
print(string.format("%d, %q", string.match("I have 2 questions for you.", "(%d+) (%a+)")))
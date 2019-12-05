-- 定义一个名为module的模块
MODULE = {}

-- 定义一个常量
MODULE.constant = "这是一个常量"

-- 定义一个函数
function MODULE.func1()
    io.write("这是一个公有函数！\n")
end

local function func2()
    print("这是一个私有函数！")
end

function MODULE.func3()
    func2()
end

return MODULE

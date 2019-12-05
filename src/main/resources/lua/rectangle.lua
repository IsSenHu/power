-- 元类
Rectangle = { area = 0, length = 0, breadth = 0 }

-- 派生类的方法
function Rectangle:new (o, length, breadth)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    self.length = length or 0
    self.breadth = breadth or 0
    self.area = length * breadth
    return o
end

-- 派生类的方法 printArea
function Rectangle:printArea()
    print("矩形面积为 ", self.area)
end

-- 我们可以使用点号(.)来访问类的属性：
r = Rectangle:new(nil, 10, 20)
print(r.length)

-- 我们可以使用冒号 : 来访问类的成员函数
r:printArea()



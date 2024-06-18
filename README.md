# Custom Minesweeper V1.2

> Just Copy Windows Minesweeper

## Links

> [GitHub](https://github.com/Kendieer/CustomMinesweeper)
> [Notion Page](https://boatneck-golf-2b4.notion.site/Minesweeper-6d0e47bc68254cc3900ba74a51b0aecf?pvs=4)

## Introduction

一个简单的扫雷游戏，其中包含基础的扫雷功能，以及附加的存档读档功能。


除此以外，还有可以读档、存档功能，以便中途退出游戏。

**随机种子**：当使用了固定种子并且首个点击节点相同时，那么生成的地图就相同，若不满足任意条件，则地图不同，随机种子在每局点击第一个节点后才生效，并非创建游戏时。

## Updates

### V1.2

+ 现在读取存档时也可以读取标记了(但是与之前的存档冲突)。
+ 新增警示窗口，用于在尝试读取无数据的情况下提示。

### V1.1

+ 增加了右键标记雷的功能。

### 1.0

+ Release

## 自定义API & 工具类 (Maybe it's useful?)

- **LinearRandom**

  线性随机数生成器，用于生成随机数，因使用线性随机数生成方式而取名。

  该类为API类，允许创建对象，其对象有着一个内置的随机种子。

  ```java
  //new instance
  LinearRandom linearRandom = new LinearRandom();
  // get value
  System.out.println(linearRandom.next()); // get [0,65536)
  System.out.println(linearRandom.next(20)); // get [0,20)
  System.out.println(linearRandom.next(20,100)); // get [20,100)
  ```


- **MapGenerator**

  地图生成器，用于随机分布地雷和生成对应节点类型。

  地图生成依赖于随机数生成器。

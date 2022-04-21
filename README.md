# AllenLucasBase

## 使用gradle composing builds 进行版本统一管理

> 源引自 [【奇技淫巧】子 module 的 build.gradle 中没有一行代码？多项目构建技巧](https://juejin.cn/post/6870082590397693965),项目地址：[地址](https://github.com/Flywith24/Android-Detail)

### Note 注意，如果只是单纯的使用gradle composing builds进行版本控制，使用groovy或者kotlin dsl都可以，但是如果使用扩展方法在插件内进行处理，目前项目内只能使用kotlin dsl,如果使用groovy也可实现相同功能吗，请反馈

### 使用composing builds
- 创建version的module,可以删除多余文件，保留当前项目内的即可
- version 的build.gradle文件修改名称为 build.gradle.kts 按照项目内填写即可，注意底部代码，id当前写的version的路径，后期导入插件用的，下面的implementationClass即实现了 Plugin<Project>的类
- 修改项目的build.gradle 为 build.gradle.kts 按照项目内填写即可，后续有修改
- 后续的module如果依赖baseLib并且没有单独的依赖，不需要build.gradle文件，直接删除掉
  
  
### todo
  - []添加分类，区分开哪些module依赖baseLib,哪些不需要依赖
  - []添加上传到maven仓库的配置
  - []现在在baseLib的build.gradle内还有依赖的配置，后续将依赖添加到插件中
  - []插件内部逻辑可以继续优化

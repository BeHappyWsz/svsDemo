<template>
  <div id="app">
      <el-container>
      <!-- 头部展示 -->
      <el-header>
        <el-col :span="10">
          <div class="tools" @click.prevent="collapse">
            <i class="fa fa-align-justify"></i>
          </div>
        </el-col>
      </el-header>
      <el-container>
        <!-- 侧边栏 -->
        <el-aside :width="asideWidth + 'px'">
          <el-button @click="slideOpen" v-show="!isCollapse" size="small" class="side-button" icon="el-icon-d-arrow-left" type="info"/>
          <el-button @click="slideOpen" v-show="isCollapse" size="small" class="side-button" icon="el-icon-d-arrow-right" type="info"/>
          <!-- 用户管理 -->
          <el-menu
            :collapse="isCollapse"
            :default-active="$route.path"
            :unique-opened=false
            :router=true
            text-color="#fff"
            background-color="#545c64"
            active-text-color="#ffd04b">
            <template v-for="(item, index) in $router.options.routes">
              <template v-if="item.menu">
                <el-submenu :key="item.name + '-' + index" :index="index + ''">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span slot="title">{{item.name}}</span>
                  </template>
                  <el-menu-item :index="sub.path" :key="sIndex" v-for="(sub, sIndex) in item.children">{{sub.name}}</el-menu-item>
                </el-submenu>
              </template>
            </template>
          </el-menu>
        </el-aside>
        <!-- 主体内容 -->
        <el-main style="height:100%;">
          <router-view name="indexRouter"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default{
  name: 'index',
  data () {
    return {
      asideWidth: 60,
      isCollapse: true
    }
  },
  methods: {
    slideOpen () {
      this.isCollapse = !this.isCollapse
    }
  },
  watch: {
    isCollapse: function (val, oldVal) {
      if (val) {
        this.asideWidth = 64
      } else {
        this.asideWidth = 200
      }
    }
  }
}
</script>
<style scoped>
.el-container{
  width: 100%;
  height: 100%;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-header {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.side-button{
  top: 15px;
  left: 50px;
  position: absolute;
}
</style>
